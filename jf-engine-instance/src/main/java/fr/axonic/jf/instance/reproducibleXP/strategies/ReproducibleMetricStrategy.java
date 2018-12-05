package fr.axonic.jf.instance.reproducibleXP.strategies;

import fr.axonic.jf.engine.strategy.ComputedStrategy;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.reproducibleXP.conclusions.ReproducibleMetricsConclusion;

import java.util.List;

public class ReproducibleMetricStrategy extends ReproducibleXPStrategy  {

    public ReproducibleMetricStrategy(String name) {
        super(name);
    }

    public ReproducibleMetricStrategy()  {
        super(null);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        //TODO: Analyse conclusions of metrics strategies & return conclusion

        ReproducibleMetricsConclusion conclusion = new ReproducibleMetricsConclusion();
        conclusion.setName("REPRODUCIBLE_METRICS_CONCLUSION");
        return conclusion;
    }
}
