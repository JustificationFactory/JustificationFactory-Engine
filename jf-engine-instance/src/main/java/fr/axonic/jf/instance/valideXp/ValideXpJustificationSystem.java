package fr.axonic.jf.instance.valideXp;

import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.diagram.JustificationPatternDiagram;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.pattern.DiagramPatternsBase;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.type.InputType;
import fr.axonic.jf.engine.pattern.type.OutputType;
import fr.axonic.jf.engine.pattern.type.Type;
import fr.axonic.jf.engine.strategy.Strategy;


import fr.axonic.jf.instance.valideXp.conclusion.ValideXpConclusion;

import fr.axonic.jf.instance.valideXp.evidence.LogEvidence;
import fr.axonic.jf.instance.valideXp.evidence.ResultAnalyseEvidence;
import fr.axonic.jf.instance.valideXp.evidence.XpEvidence;

import fr.axonic.jf.instance.valideXp.strategies.ValideurDxpStrategy;


import fr.axonic.validation.exception.VerificationException;

import java.util.Arrays;

public class ValideXpJustificationSystem extends JustificationSystem<DiagramPatternsBase> {

    public ValideXpJustificationSystem() throws VerificationException, WrongEvidenceException {
        super(createPatternsBase());
    }

    private static DiagramPatternsBase createPatternsBase() {
        return new DiagramPatternsBase(createJPD());
    }


    private static JustificationPatternDiagram createJPD() {
        JustificationPatternDiagram jpd = new JustificationPatternDiagram();

        // Les preuves

        // Preuve de Log
        InputType<LogEvidence> LogE =
                new InputType<>(LogEvidence.EVIDENCE_NAME, new Type<>(LogEvidence.class, "LOG_FILE"));

        // Preuve de Log
        InputType<ResultAnalyseEvidence> ResultAnalyseE =
                new InputType<>(ResultAnalyseEvidence.EVIDENCE_NAME, new Type<>(ResultAnalyseEvidence.class, "RESULT_ANALYSE"));

        //Preuve Xp
        InputType<XpEvidence> XpE =
                new InputType<>(XpEvidence.EVIDENCE_NAME, new Type<>(XpEvidence.class, "XP_DESCRIPTION"));


        // Conclusion
        OutputType<ValideXpConclusion> ValideXpC =
                new OutputType<>("XP ACCEPTED", new Type<>(ValideXpConclusion.class, "XP_VALIDE"));

        // Stratégy

        Strategy ValideurDxp = new ValideurDxpStrategy("IS_IT_VALIDE");







        //Chemin pour arriver a la conclusion ValideXpC
        Pattern ValidationExperience = new Pattern("XP_IS_VALIDATE_PATTERN", "pattern de validation", ValideurDxp,
                Arrays.asList(LogE, ResultAnalyseE,XpE), ValideXpC);



        jpd.addStep(ValidationExperience);

        return jpd;
    }
}
