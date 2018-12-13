package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.ReproducibleExperimentConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.ReproducibleMetricsConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.TotalTimeMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.ReproducibleDocument;
import fr.axonic.jf.instance.ValidXp.conclusion.ValidXpConclusion;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;

import java.util.List;

public class ReproducibleExperimentFinalStrategy extends ReproducibleExperimentStrategy {
    public ReproducibleExperimentFinalStrategy() {
    }

    public ReproducibleExperimentFinalStrategy(String name) {
        super(name);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        ReproducibleDocument doc = null;
        for (int i = 0; i < supportList.size(); i++){
            Support s = supportList.get(i);
            if(s instanceof ReproducibleMetricsConclusion){
                for (int j = 0; j < supportList.size(); j++){
                    Support  s2 = supportList.get(j);
                    if(s2 instanceof ValidXpConclusion){
                        ReproducibleDocument ReproducibleConclusionDoc = (ReproducibleDocument)s.getElement();
                        XpDocument ValidXPConclusionDoc = (XpDocument) s2.getElement();
                        String ReproducibleConclusionJobId = ReproducibleConclusionDoc.getJobId();
                        String ValidXPConclusionJobId = ValidXPConclusionDoc.getJobId();
                        if (ReproducibleConclusionJobId.equals(ValidXPConclusionJobId)){
                            boolean reproducibleXP = ReproducibleConclusionDoc.isReproducible() && ValidXPConclusionDoc.getValid();
                            doc = new ReproducibleDocument(ReproducibleConclusionJobId,reproducibleXP);
                        }

                    }
                }
            }
        }
        ReproducibleExperimentConclusion conclusion = new ReproducibleExperimentConclusion("REPRODUCIBLE_EXPERIMENT_CONCLUSION",doc);
        return conclusion;
    }
}
