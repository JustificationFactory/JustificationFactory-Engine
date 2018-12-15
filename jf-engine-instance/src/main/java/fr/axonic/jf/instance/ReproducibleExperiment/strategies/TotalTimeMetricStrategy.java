package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusions.AccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusions.NegativeAccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusions.PositiveAccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.TotalTimeMetricConclusions.NegativeTotalTimeMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.TotalTimeMetricConclusions.PositiveTotalTimeMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.TotalTimeMetricConclusions.TotalTimeMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.MetricDocument;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.ReproducibleDocument;
import fr.axonic.jf.instance.ReproducibleExperiment.evidences.ExperimentEvidence;
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

        ReproducibleDocument doc = null;
        for(int i = 0; i < supportList.size(); i++){
            Support s = supportList.get(i);
            if(s instanceof TotalTimeMetricEvidence) {
                for (int j = 0; j < supportList.size(); j++) {
                    Support s2 = supportList.get(j);
                    if (s2 instanceof ExperimentEvidence) {

                        TotalTimeMetricEvidence totalTimeMetricEvidence = (TotalTimeMetricEvidence) s;
                        boolean isReproducible = MetricAnalysis.isReproducible(totalTimeMetricEvidence.getElement().getValues(), 0.15);
                        MetricDocument totaltimeDocument = ((MetricDocument) s.getElement());
                        XpDocument experimentDocument = ((XpDocument) s2.getElement());
                        String totaltimeId = totaltimeDocument.getJobId();
                        String experimentId = experimentDocument.getJobId();
                        boolean sameId = totaltimeId.equals(experimentId);
                        boolean sameIdAndReproducible = sameId && isReproducible;
                        if (sameIdAndReproducible) {
                            doc = new ReproducibleDocument(totaltimeId, true);
                            PositiveTotalTimeMetricConclusion conclusion = new PositiveTotalTimeMetricConclusion("TOTAL_TIME_METRIC_CONCLUSION", doc);
                            return conclusion;
                        } else {
                            if (sameId) {
                                doc = new ReproducibleDocument(totaltimeId, false);
                                NegativeTotalTimeMetricConclusion conclusion = new NegativeTotalTimeMetricConclusion("TOTAL_TIME_METRIC_CONCLUSION", doc);
                                return conclusion;
                            } else {
                                NegativeTotalTimeMetricConclusion conclusion = new NegativeTotalTimeMetricConclusion("TOTAL_TIME_METRIC_CONCLUSION", doc);
                                return conclusion;
                            }
                        }
                    }
                }
            }
        }
        TotalTimeMetricConclusion conclusion = new NegativeTotalTimeMetricConclusion("TOTAL_TIME_METRIC_CONCLUSION",doc);
        return conclusion;
    }
}
