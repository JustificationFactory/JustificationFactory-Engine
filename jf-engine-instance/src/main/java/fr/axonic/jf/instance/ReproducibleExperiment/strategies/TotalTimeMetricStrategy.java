package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.TotalTimeMetricConclusions.NegativeTotalTimeMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.TotalTimeMetricConclusions.PositiveTotalTimeMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.TotalTimeMetricConclusions.TotalTimeMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.ReproducibleDocument;
import fr.axonic.jf.instance.ReproducibleExperiment.evidences.TotalTimeMetricEvidence;

import java.util.List;

public class TotalTimeMetricStrategy extends ReproducibleExperimentStrategy {
    public TotalTimeMetricStrategy() {
    }

    public TotalTimeMetricStrategy(String name) {
        super(name);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        //TODO : Strategy for total time metric

        ReproducibleDocument doc = null;
        for(int i = 0; i < supportList.size(); i++){
            Support s = supportList.get(i);
            if(s instanceof TotalTimeMetricEvidence){
                TotalTimeMetricEvidence totalTimeMetricEvidence = (TotalTimeMetricEvidence) s;
                boolean isReproducible = MetricAnalysis.isReproducible(totalTimeMetricEvidence.getElement().getValues(),0.15);
                doc = new ReproducibleDocument(totalTimeMetricEvidence.getElement().getJobId(), isReproducible);
                if (isReproducible){
                    TotalTimeMetricConclusion conclusion = new PositiveTotalTimeMetricConclusion("TOTAL_TIME_METRIC_CONCLUSION",doc);
                    return conclusion;
                }
                else {
                    TotalTimeMetricConclusion conclusion = new NegativeTotalTimeMetricConclusion("TOTAL_TIME_METRIC_CONCLUSION",doc);
                    return conclusion;
                }
            }
        }
        TotalTimeMetricConclusion conclusion = new NegativeTotalTimeMetricConclusion("TOTAL_TIME_METRIC_CONCLUSION",doc);
        return conclusion;
    }
}
