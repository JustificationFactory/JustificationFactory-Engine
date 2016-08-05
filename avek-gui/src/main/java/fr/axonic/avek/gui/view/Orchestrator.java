package fr.axonic.avek.gui.view;

import fr.axonic.avek.engine.ArgumentationDiagramAPIImpl;
import fr.axonic.avek.engine.Pattern;
import fr.axonic.avek.engine.WrongEvidenceException;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.validation.exception.VerificationException;
import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nathaël N on 04/08/16.
 */
public class Orchestrator {
    private final static Logger LOGGER = Logger.getLogger(Orchestrator.class);
    private final static Orchestrator INSTANCE = new Orchestrator();

    private MainFrame frame;
    private Pattern currentPattern;
    private List<Pattern> patternList;
    private List<EvidenceRole> evidences;

    private Orchestrator() {
    }

    private void orchestrate() throws VerificationException, WrongEvidenceException {
        frame.setView(new LoadingView());
        ArgumentationDiagramAPIImpl adAPI = ArgumentationDiagramAPIImpl.getInstance();
        evidences = null;
        patternList = null;
        currentPattern = null;

        new Thread(() -> {
            evidences = adAPI.getBaseEvidences();
            patternList = adAPI.getPossiblePatterns(evidences);

            if (patternList.size() == 1) {
                if (setViewFromPattern(patternList.get(0)))
                    return;
            }

            StrategySelectionView ssv = new StrategySelectionView();
            frame.setView(ssv);
            List<String> names = patternList.stream().map(Pattern::getName).collect(Collectors.toList());
            ssv.setAvailableChoices(names);
        }).start();
    }

    private boolean setViewFromPattern(Pattern p) {
        switch (p.getName()) {
            case "Treat":
                Platform.runLater(() -> frame.setView(new TreatView()));
                break;
            case "Establish Effect":
                Platform.runLater(() -> frame.setView(new EstablishEffectView()));
                break;
            case "Generalize":
                Platform.runLater(() -> frame.setView(new GeneralizeView()));
                break;
            default:
                LOGGER.warn("Pattern is unknown for View conversion: " + p);
                return false;
        }

        currentPattern = p;
        return true;
    }


    public static void setFrame(MainFrame frame) {
        INSTANCE.frame = frame;
        try {
            INSTANCE.orchestrate();
        } catch (VerificationException | WrongEvidenceException e) {
            LOGGER.error("Impossible to get ArgumentationDiagramAPIImpl", e);
        }
    }

    public static void submitChoice(String value) {
        Pattern selectedPattern = null;

        for (Pattern p : INSTANCE.patternList) {
            if (p.getName().equals(value)) {
                selectedPattern = p;
                break;
            }
        }

        if(selectedPattern == null) {
            LOGGER.warn("No pattern found with name: "+value);
        }
        else {
            INSTANCE.setViewFromPattern(selectedPattern);
        }
    }

    public static void onValidate() {
        try {
            INSTANCE.orchestrate();
        } catch (VerificationException | WrongEvidenceException e) {
            LOGGER.error("Impossible to get ArgumentationDiagramAPIImpl", e);
        }
    }
}
