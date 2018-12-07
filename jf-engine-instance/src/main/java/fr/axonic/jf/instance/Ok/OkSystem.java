package fr.axonic.jf.instance.Ok;


import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.diagram.JustificationPatternDiagram;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.pattern.DiagramPatternsBase;
import fr.axonic.jf.engine.pattern.ListPatternsBase;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.type.InputType;
import fr.axonic.jf.engine.pattern.type.OutputType;
import fr.axonic.jf.engine.pattern.type.Type;
import fr.axonic.jf.engine.strategy.Strategy;
import fr.axonic.jf.engine.support.instance.DocumentEvidence;
import fr.axonic.jf.instance.Ok.conclusion.OkConclusion;
import fr.axonic.jf.instance.Ok.conclusion.OkFinalConclusion;
import fr.axonic.jf.instance.Ok.evidence.OkEvidence;
import fr.axonic.jf.instance.Ok.strategie.OkStrategies;
import fr.axonic.jf.instance.Ok.strategie.OkStrategiesFC;
import fr.axonic.validation.exception.VerificationException;

import java.util.*;

public class OkSystem extends JustificationSystem<ListPatternsBase> {

    public OkSystem() throws VerificationException, WrongEvidenceException {
        //super(createPatternsBase());

        ListPatternsBase patternsBase=new ListPatternsBase();
        InputType<OkEvidence> Ok_e =new InputType<OkEvidence>("OK_E",OkEvidence.class);
        OutputType<OkConclusion> Ok_c = new OutputType<>(OkConclusion.class);
        Strategy Ok_s = new OkStrategies("OK_S");
        Pattern Ok_p = new Pattern("OK_P", "OK_P", Ok_s, Arrays.asList(Ok_e), Ok_c);

        OutputType<OkFinalConclusion> Ok_f_c = new OutputType<>(OkFinalConclusion.class);
        Strategy Ok_f_s = new OkStrategiesFC("OK_F_S");
        Pattern Ok_f_p = new Pattern("OK_F_P", "OK_F_P", Ok_f_s, Arrays.asList(Ok_p.getOutputType().transformToInput()), Ok_f_c);

        patternsBase.addPattern(Ok_p);
        patternsBase.addPattern(Ok_f_p);

        this.patternsBase=patternsBase;
        this.autoSupportFillEnable =true;
        this.versioningEnable=true;

    }

    /*private static DiagramPatternsBase createPatternsBase() {
        return new DiagramPatternsBase(createJPD());
    }

    private static JustificationPatternDiagram createJPD() {
        JustificationPatternDiagram jpd = new JustificationPatternDiagram();

        InputType<OkEvidence> Ok_e = new InputType<>(OkEvidence.EVIDENCE_NAME, new Type<>(OkEvidence.class, "OK_E"));
        OutputType<OkConclusion> Ok_c = new OutputType<>("OK_C", new Type<>(OkConclusion.class, "OK_C"));
        Strategy Ok_s = new OkStrategies("OK_S");
        Pattern Ok_p = new Pattern("OK_P", "OK_P", Ok_s, Arrays.asList(Ok_e), Ok_c);



        OutputType<OkFinalConclusion> Ok_f_c = new OutputType<>("OK_F_C", new Type<>(OkFinalConclusion.class, "OK_F_C"));
        Strategy Ok_f_s = new OkStrategiesFC("OK_F_S");
        Pattern Ok_f_p = new Pattern("OK_F_P", "OK_F_P", Ok_f_s, Arrays.asList(Ok_p.getOutputType().transformToInput()), Ok_f_c);


        jpd.addStep(Ok_p);
        jpd.addStep(Ok_f_p);

        return jpd;
    }
    */

}
