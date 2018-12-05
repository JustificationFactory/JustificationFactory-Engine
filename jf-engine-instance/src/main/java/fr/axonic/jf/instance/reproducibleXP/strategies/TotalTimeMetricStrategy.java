package fr.axonic.jf.instance.reproducibleXP.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.reproducibleXP.conclusions.TotalTimeMetricsConclusion;

import java.util.List;

public class TotalTimeMetricStrategy extends ReproducibleXPStrategy {

    public TotalTimeMetricStrategy(String name) {
        super(name);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        //TODO: Analyse metric(s) in the supportList parameter in order to give a conclusion regarding reproducibility
        TotalTimeMetricsConclusion conclusion = new TotalTimeMetricsConclusion();
        conclusion.setName("TOTALTIME_CONCLUSION");
        return conclusion;
    }

}
