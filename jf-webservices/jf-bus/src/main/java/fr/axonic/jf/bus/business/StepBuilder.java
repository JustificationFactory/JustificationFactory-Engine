package fr.axonic.jf.bus.business;

import fr.axonic.jf.bus.business.supports.KnownSupportsDAO;
import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.exception.AlreadyBuildingException;
import fr.axonic.jf.engine.exception.StepBuildingException;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.pattern.JustificationStep;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.PatternsBase;
import fr.axonic.jf.engine.support.Support;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestScoped
public class StepBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepBuilder.class);

    @Inject
    private JustificationSystemsDAO justificationSystemsDAO;

    @Inject
    private KnownSupportsDAO knownSupportsDAO;

    private final List<JustificationStep> builtSteps;

    public StepBuilder(JustificationSystemsDAO justificationSystemsDAO, KnownSupportsDAO knownSupportsDAO) {
        this();
        this.justificationSystemsDAO = justificationSystemsDAO;
        this.knownSupportsDAO = knownSupportsDAO;
    }

    public StepBuilder() {
        builtSteps = new ArrayList<>();
    }

    public List<JustificationStep> getBuiltSteps() {
        return builtSteps;
    }

    public void acknowledgeSupport(Support addedSupport) throws StepBuildingException {
        LOGGER.info("Acknowledge support \"{}\", version \"{}\"", addedSupport.getName(), addedSupport.getElement().getVersion());

        try {
            knownSupportsDAO.saveSupport(addedSupport);
            triggerStepsBuilding();
        } catch (IOException e) {
            LOGGER.error("Error while building steps", e);
        }
    }

    private void triggerStepsBuilding() throws StepBuildingException, IOException {
        for (Map.Entry<String, JustificationSystem> pair : justificationSystemsDAO.loadJustificationSystems().entrySet()) {
            String name = pair.getKey();
            JustificationSystem justificationSystem = pair.getValue();

            if (justificationSystem == null || justificationSystem.getPatternsBase() == null || justificationSystem.getJustificationDiagram() == null) {
                continue;
            }

            triggerOneSystemStepsBuilding(name, (JustificationSystem<? extends PatternsBase>) justificationSystem);
        }
    }

    private void triggerOneSystemStepsBuilding(String justificationSystemName, JustificationSystem<? extends PatternsBase> justificationSystem) throws StepBuildingException, IOException {
        List<JustificationStep> steps = justificationSystem.getJustificationDiagram().getSteps();
        LOGGER.info("Current system ({}): {}", steps.size(), steps);

        List<Support> knownSupports = knownSupportsDAO.loadSupports();

        List<Pattern> patterns = justificationSystem.getApplicablePatterns(knownSupports);

        if (patterns.isEmpty()) {
            LOGGER.info("No applicable pattern for {}. No action.", justificationSystemName);
            return;
        }

        LOGGER.info("{} patterns can be built with the {} known supports ({})", patterns.size(), knownSupports.size(),
                patterns.stream().map(Pattern::getName).collect(Collectors.toList()));

        for (Pattern pattern : patterns) {
            List<Support> usefulSupports = pattern.filterUsefulEvidences(knownSupports);

            LOGGER.info("Build pattern {} with supports: {}", pattern.getId(), usefulSupports);

            try {
                JustificationStep res = justificationSystem.constructStep(pattern, usefulSupports, null);
                JustificationStep step = res.clone();
                LOGGER.info("Step {} has been built", step.getPatternId());

                Optional<JustificationStep> justificationStep = builtSteps.stream().filter(s -> s.getId().equals(step.getId())).findFirst();

                justificationStep.ifPresent(existingStep -> {
                    List<Support> supports = new ArrayList<>(existingStep.getSupports());
                    supports.removeAll(step.getSupports());

                    try {
                        knownSupportsDAO.removeAllSupports(supports);
                    } catch (IOException e) {
                        LOGGER.error("Failed to remove supports", e);
                    }

                    builtSteps.remove(existingStep);
                });

                builtSteps.add(step);
            } catch (WrongEvidenceException e) {
                LOGGER.error("Unexpected wrong support", e);
            } catch (AlreadyBuildingException e) {
                LOGGER.warn("Already built", e);
            } catch (CloneNotSupportedException e) {
                LOGGER.error("Failed to clone", e);
            }
        }

        justificationSystemsDAO.saveJustificationSystem(justificationSystemName, justificationSystem);
        LOGGER.info("Saved justification system {}", justificationSystemName);

        if (!patterns.isEmpty()) {
            LOGGER.info("Going to trigger the step building one more time for {}", justificationSystemName);
            triggerOneSystemStepsBuilding(justificationSystemName, justificationSystem);
        }
    }
}
