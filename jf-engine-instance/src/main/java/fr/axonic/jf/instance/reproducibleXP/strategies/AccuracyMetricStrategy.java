package fr.axonic.jf.instance.reproducibleXP.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.reproducibleXP.conclusions.AccuracyMetricsConclusion;

import java.util.List;

public class AccuracyMetricStrategy extends ReproducibleXPStrategy {

    public AccuracyMetricStrategy(String name) {
        super(name);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        //TODO: Analyse metric(s) in the supportList parameter in order to give a conclusion regarding reproducibility
        AccuracyMetricsConclusion conclusion = new AccuracyMetricsConclusion();
        conclusion.setName("ACCURACY_CONCLUSION");
        return conclusion;
    }

}
