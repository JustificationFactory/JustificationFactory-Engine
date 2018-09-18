package fr.axonic.avek.bus;

import fr.axonic.avek.dao.JustificationSystemsDAO;
import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.exception.AlreadyBuildingException;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.JustificationStep;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.support.Support;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StepBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepBuilder.class);

    private final JustificationSystemsDAO justificationSystemsDAO;
    private final List<Support> knownSupports;
    private final List<JustificationStep> builtSteps;

    public StepBuilder(JustificationSystemsDAO justificationSystemsDAO) {
        this.justificationSystemsDAO = justificationSystemsDAO;
        knownSupports = new ArrayList<>();
        builtSteps = new ArrayList<>();
    }

    public List<JustificationStep> getBuiltSteps() {
        return builtSteps;
    }

    public void acknowledgeSupport(Support addedSupport) throws StepBuildingException, StrategyException {
        LOGGER.info("Acknowledge support \"{}\", version \"{}\"", addedSupport.getName(), addedSupport.getElement().getVersion());
        knownSupports.add(addedSupport);

        try {
            triggerStepsBuilding();
        } catch (IOException e) {
            e.printStackTrace();
            // TODO
        }
    }

    private void triggerStepsBuilding() throws StrategyException, StepBuildingException, IOException {
        for (Map.Entry<String, JustificationSystem> pair : justificationSystemsDAO.loadJustificationSystems().entrySet()) {
            triggerOneSystemStepsBuilding(pair.getKey(), pair.getValue());
        }
    }

    private void triggerOneSystemStepsBuilding(String justificationSystemName, JustificationSystem justificationSystem) throws StrategyException, StepBuildingException, IOException {
        List<Pattern> patterns = justificationSystem.getApplicablePatterns(knownSupports);

        LOGGER.info("{} patterns can be built with the {} known supports ({})", patterns.size(), knownSupports.size(),
                patterns.stream().map(Pattern::getName).collect(Collectors.toList()));

        for (Pattern pattern : patterns) {
            List<Support> usefulSupports = pattern.filterUsefulEvidences(knownSupports);

            try {
                JustificationStep res = justificationSystem.constructStep(pattern, usefulSupports, null);
                JustificationStep step = res.clone();
                LOGGER.info("Step {} has been built", step.getPatternId());

                // TODO What is next with this step?
                Optional<JustificationStep> justificationStep = builtSteps.stream().filter(s -> s.getId().equals(step.getId())).findFirst();

                justificationStep.ifPresent(existingStep -> {
                    List<Support> supports = new ArrayList<>(existingStep.getSupports());
                    supports.removeAll(step.getSupports());
                    knownSupports.removeAll(supports);
                    builtSteps.remove(existingStep);
                });

                builtSteps.add(step);
            } catch (WrongEvidenceException e) {
                LOGGER.error("Unexpected wrong support", e);
            } catch (AlreadyBuildingException e) {
                LOGGER.warn("Already built exception", e);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        if (!patterns.isEmpty()) {
            triggerOneSystemStepsBuilding(justificationSystemName, justificationSystem);
        } else {
            justificationSystemsDAO.saveJustificationSystem(justificationSystemName, justificationSystem);
        }
    }
}
