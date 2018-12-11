package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.TotalTimeMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.evidences.TotalTimeMetricEvidence;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;

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
        XpDocument doc = null;
        for(int i = 0; i < supportList.size(); i++){
            Support s = supportList.get(i);
            if(s instanceof TotalTimeMetricEvidence){
                TotalTimeMetricEvidence totalTimeMetricEvidence = (TotalTimeMetricEvidence) s;
                doc = new XpDocument(totalTimeMetricEvidence.getElement().getJobId());
            }
        }
        TotalTimeMetricConclusion conclusion = new TotalTimeMetricConclusion("TOTAL_TIME_METRIC_CONCLUSION",doc);
        return conclusion;
    }
}
