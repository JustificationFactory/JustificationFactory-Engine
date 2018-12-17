package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.strategy.Rationale;
import fr.axonic.jf.engine.strategy.UsageDomain;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusions.AccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusions.NegativeAccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusions.PositiveAccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.ReproducibleMetricsConclusions.NegativeReproducibleMetricsConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.ReproducibleMetricsConclusions.PositiveReproducibleMetricsConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.ReproducibleMetricsConclusions.ReproducibleMetricsConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.TotalTimeMetricConclusions.NegativeTotalTimeMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.TotalTimeMetricConclusions.PositiveTotalTimeMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.TotalTimeMetricConclusions.TotalTimeMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.ReproducibleDocument;

import java.util.List;

public class ReproducibleMetricsStrategy extends ReproducibleExperimentStrategy {
    public ReproducibleMetricsStrategy() {
    }

    public ReproducibleMetricsStrategy(String name, Rationale rationale, UsageDomain usageDomain) {
        super(name,rationale,usageDomain);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        ReproducibleMetricsConclusion conclusion = new NegativeReproducibleMetricsConclusion("REPRODUCIBLE_METRICS_CONCLUSION", null);
        for(int i = 0; i < supportList.size(); i++){
            Support s = supportList.get(i);
            if( (s instanceof PositiveAccuracyMetricConclusion || s instanceof NegativeAccuracyMetricConclusion) &&  s.getElement() != null){
                for (int y = 0; y < supportList.size(); y++){
                    Support tmp = supportList.get(y);
                    if( (tmp instanceof PositiveTotalTimeMetricConclusion || tmp instanceof NegativeTotalTimeMetricConclusion) && tmp.getElement() != null){
                        ReproducibleDocument totalTimeMetricDocument = (ReproducibleDocument) tmp.getElement();
                        ReproducibleDocument accuracyMetricDocument = (ReproducibleDocument) s.getElement();
                        String ttmc = totalTimeMetricDocument.getJobId();
                        String amc = accuracyMetricDocument.getJobId();
                        if(ttmc.equals(amc)){
                            boolean reproducible = totalTimeMetricDocument.isReproducible() && accuracyMetricDocument.isReproducible();
                            ReproducibleDocument doc = new ReproducibleDocument(totalTimeMetricDocument.getJobId(),reproducible);
                            if (reproducible) { conclusion = new PositiveReproducibleMetricsConclusion("REPRODUCIBLE_METRICS_CONCLUSION", doc);}
                            else { conclusion = new NegativeReproducibleMetricsConclusion("REPRODUCIBLE_METRICS_CONCLUSION", doc); }
                            return conclusion;
                        }
                    }
                    else {
                        conclusion = new NegativeReproducibleMetricsConclusion("REPRODUCIBLE_METRICS_CONCLUSION", null);
                    }
                }
            }
            else{
                conclusion = new NegativeReproducibleMetricsConclusion("REPRODUCIBLE_METRICS_CONCLUSION", null);
            }
        }
        return conclusion;
    }
}
