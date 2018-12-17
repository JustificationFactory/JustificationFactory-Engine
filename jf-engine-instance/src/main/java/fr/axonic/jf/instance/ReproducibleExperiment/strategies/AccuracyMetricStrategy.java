package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.strategy.Rationale;
import fr.axonic.jf.engine.strategy.UsageDomain;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusions.AccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusions.NegativeAccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusions.PositiveAccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.MetricDocument;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.ReproducibleDocument;
import fr.axonic.jf.instance.ReproducibleExperiment.evidences.AccuracyMetricEvidence;
import fr.axonic.jf.instance.ReproducibleExperiment.evidences.ExperimentEvidence;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;

import java.util.List;

public class AccuracyMetricStrategy extends ReproducibleExperimentStrategy{
    public AccuracyMetricStrategy() {
        super(null,null,null);
    }

    public AccuracyMetricStrategy(String name, Rationale rationale, UsageDomain usageDomain) {
        super(name, rationale, usageDomain);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        //TODO : Strategy for accuracy metric
        ReproducibleDocument doc = null;
        for(int i = 0; i < supportList.size(); i++){
            Support s = supportList.get(i);
            if(s instanceof AccuracyMetricEvidence){
                for (int j = 0; j < supportList.size(); j++){
                    Support s2 = supportList.get(j);
                    if (s2 instanceof ExperimentEvidence){
                        AccuracyMetricEvidence accuracyMetricEvidence = (AccuracyMetricEvidence) s;
                        boolean isReproducible = MetricAnalysis.isReproducible(accuracyMetricEvidence.getElement().getValues(),0.15);
                        MetricDocument accuracyDocument = ((MetricDocument) s.getElement());
                        XpDocument experimentDocument = ((XpDocument) s2.getElement());
                        String accuracyId = accuracyDocument.getJobId();
                        String experimentId = experimentDocument.getJobId();
                        boolean sameId = accuracyId.equals(experimentId);
                        boolean sameIdAndReproducible = sameId && isReproducible;
                        if (sameIdAndReproducible){
                            doc = new ReproducibleDocument(accuracyId,true);
                            AccuracyMetricConclusion conclusion = new PositiveAccuracyMetricConclusion("ACCURACY_METRIC_CONCLUSION", doc);
                            return conclusion;
                        }
                        else {
                            if (sameId) {
                                doc = new ReproducibleDocument(accuracyId,false);
                                AccuracyMetricConclusion conclusion = new NegativeAccuracyMetricConclusion("ACCURACY_METRIC_CONCLUSION", doc);
                                return conclusion;
                            }
                            else{
                                AccuracyMetricConclusion conclusion = new NegativeAccuracyMetricConclusion("ACCURACY_METRIC_CONCLUSION", doc);
                                return conclusion;
                            }
                        }
                    }
                }

            }
        }
        AccuracyMetricConclusion conclusion = new NegativeAccuracyMetricConclusion("ACCURACY_METRIC_CONCLUSION", doc);
        return conclusion;
    }
}
