package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.ReproducibleMetricsConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.TotalTimeMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.ReproducibleDocument;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;

import java.util.List;

public class ReproducibleMetricsStrategy extends ReproducibleExperimentStrategy {
    public ReproducibleMetricsStrategy() {
    }

    public ReproducibleMetricsStrategy(String name) {
        super(name);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        ReproducibleMetricsConclusion conclusion = new ReproducibleMetricsConclusion("REPRODUCIBLE_METRICS_CONCLUSION", null);
        for(int i = 0; i < supportList.size(); i++){
            Support s = supportList.get(i);
            if(s instanceof AccuracyMetricConclusion){
                for (int y = 0; y < supportList.size(); y++){
                    Support tmp = supportList.get(y);
                    if(tmp instanceof TotalTimeMetricConclusion){
                        ReproducibleDocument totalTimeMetricDocument = (ReproducibleDocument) tmp.getElement();
                        ReproducibleDocument accuracyMetricDocument = (ReproducibleDocument) s.getElement();
                        String ttmc = totalTimeMetricDocument.getJobId();
                        String amc = accuracyMetricDocument.getJobId();
                        if(ttmc.equals(amc)){
                            boolean reproducible = totalTimeMetricDocument.isReproducible() && accuracyMetricDocument.isReproducible();
                            ReproducibleDocument doc = new ReproducibleDocument(totalTimeMetricDocument.getJobId(),reproducible);
                            conclusion = new ReproducibleMetricsConclusion("REPRODUCIBLE_METRICS_CONCLUSION", doc);
                        }
                    }
                }
            }
        }
        return conclusion;
    }
}
