package fr.axonic.jf.instance.ValidXp;

import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.diagram.JustificationPatternDiagram;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.pattern.DiagramPatternsBase;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.type.InputType;
import fr.axonic.jf.engine.pattern.type.OutputType;
import fr.axonic.jf.engine.pattern.type.Type;
import fr.axonic.jf.engine.strategy.Strategy;
import fr.axonic.jf.instance.ValidXp.conclusion.ValidXpConclusion;
import fr.axonic.jf.instance.ValidXp.evidence.LogEvidence;
import fr.axonic.jf.instance.ValidXp.evidence.LogResultsEvidence;
import fr.axonic.jf.instance.ValidXp.evidence.XpEvidence;
import fr.axonic.jf.instance.ValidXp.strategies.ValidXpStrategy1;
import fr.axonic.validation.exception.VerificationException;

import java.util.Arrays;



public class ValidXpSystem extends JustificationSystem<DiagramPatternsBase> {

    public ValidXpSystem() throws VerificationException, WrongEvidenceException {
        super(createPatternsBase());
    }

    private static DiagramPatternsBase createPatternsBase() {
        return new DiagramPatternsBase(createJPD());
    }

    private static JustificationPatternDiagram createJPD() {
        JustificationPatternDiagram jpd = new JustificationPatternDiagram();

        // Evidences
        // Log evidence
        InputType<LogEvidence> Log_e = new InputType<>(LogEvidence.EVIDENCE_NAME, new Type<>(LogEvidence.class, "LOG_E"));
        // Log results evidence
        InputType<LogResultsEvidence> Log_results_e = new InputType<>(LogResultsEvidence.EVIDENCE_NAME, new Type<>(LogResultsEvidence.class, "LOG_RESULTS_E"));
        // Xp evidence
        InputType<XpEvidence> Xp_e = new InputType<>(XpEvidence.EVIDENCE_NAME, new Type<>(XpEvidence.class, "XP_E"));






        // conclusion
        OutputType<ValidXpConclusion> Valid_xp_c = new OutputType<>("VALID_XP_C", new Type<>(ValidXpConclusion.class, "VALID_XP_C"));





        // strategie
        Strategy Valid_xp_s = new ValidXpStrategy1("VALID_XP_S");

        //PATTERN
        Pattern Valid_xp_p = new Pattern("VALID_XP_P", "VALID_XP_P", Valid_xp_s, Arrays.asList(Log_e,Log_results_e,Xp_e), Valid_xp_c);

        jpd.addStep(Valid_xp_p);

        return jpd;
    }
}