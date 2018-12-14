package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusions.AccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusions.NegativeAccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusions.PositiveAccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.ReproducibleDocument;
import fr.axonic.jf.instance.ReproducibleExperiment.evidences.AccuracyMetricEvidence;

import java.util.List;

public class AccuracyMetricStrategy extends ReproducibleExperimentStrategy{
    public AccuracyMetricStrategy() {
        super(null);
    }

    public AccuracyMetricStrategy(String name) {
        super(name);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        //TODO : Strategy for accuracy metric
        ReproducibleDocument doc = null;
        for(int i = 0; i < supportList.size(); i++){
            Support s = supportList.get(i);
            if(s instanceof AccuracyMetricEvidence){
                AccuracyMetricEvidence accuracyMetricEvidence = (AccuracyMetricEvidence) s;
                boolean isReproducible = MetricAnalysis.isReproducible(accuracyMetricEvidence.getElement().getValues(),0.15);
                doc = new ReproducibleDocument(accuracyMetricEvidence.getElement().getJobId(),isReproducible);
                if (isReproducible){
                    AccuracyMetricConclusion conclusion = new PositiveAccuracyMetricConclusion("ACCURACY_METRIC_CONCLUSION", doc);
                    return conclusion;
                }
                else {
                    AccuracyMetricConclusion conclusion = new NegativeAccuracyMetricConclusion("ACCURACY_METRIC_CONCLUSION", doc);
                    return conclusion;
                }
            }
        }
        AccuracyMetricConclusion conclusion = new NegativeAccuracyMetricConclusion("ACCURACY_METRIC_CONCLUSION", doc);
        return conclusion;
    }
}
