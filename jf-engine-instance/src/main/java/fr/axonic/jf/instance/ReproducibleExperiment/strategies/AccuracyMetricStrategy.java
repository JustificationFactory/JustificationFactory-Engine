package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.evidences.AccuracyMetricEvidence;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;

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
        XpDocument doc = null;
        for(int i = 0; i < supportList.size(); i++){
            Support s = supportList.get(i);
            if(s instanceof AccuracyMetricEvidence){
                AccuracyMetricEvidence accuracyMetricEvidence = (AccuracyMetricEvidence) s;
                doc = new XpDocument(accuracyMetricEvidence.getElement().getJobId());
            }
        }
        AccuracyMetricConclusion conclusion = new AccuracyMetricConclusion("ACCURACY_METRIC_CONCLUSION", doc);
        return conclusion;
    }
}
