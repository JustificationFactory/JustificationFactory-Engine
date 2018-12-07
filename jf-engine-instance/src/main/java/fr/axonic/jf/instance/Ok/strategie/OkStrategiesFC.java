package fr.axonic.jf.instance.Ok.strategie;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.Ok.conclusion.OkFinalConclusion;

import java.util.List;

public class OkStrategiesFC extends OkStrategy {

    public OkStrategiesFC(String name) {
        super(name);
    }
    public OkStrategiesFC() {
        super(null);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        //OkFinalConclusion conclusion = new OkFinalConclusion();
        //conclusion.setName("OK_F_C");
        //return conclusion;
        return new OkFinalConclusion();
    }
}
