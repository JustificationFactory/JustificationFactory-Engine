package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.*;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.ReproducibleMetricsConclusions.NegativeReproducibleMetricsConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.ReproducibleMetricsConclusions.PositiveReproducibleMetricsConclusion;
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
    public Conclusion createConclusion(List <Support> supportList) {
        ReproducibleDocument doc = null;
        for (int i = 0; i < supportList.size(); i++) {
            Support s = supportList.get(i);
            if (s instanceof PositiveReproducibleMetricsConclusion) {
                for (int j = 0; j < supportList.size(); j++) {
                    Support s2 = supportList.get(j);
                    if (s2 instanceof ValidXpConclusion) {
                        ReproducibleDocument ReproducibleConclusionDoc = (ReproducibleDocument) s.getElement();
                        XpDocument ValidXPConclusionDoc = (XpDocument) s2.getElement();
                        String ReproducibleConclusionJobId = ReproducibleConclusionDoc.getJobId();
                        String ValidXPConclusionJobId = ValidXPConclusionDoc.getJobId();
                        if (ReproducibleConclusionJobId.equals(ValidXPConclusionJobId)) {
                            boolean reproducibleXP = ReproducibleConclusionDoc.isReproducible() && ValidXPConclusionDoc.getValid();
                            doc = new ReproducibleDocument(ReproducibleConclusionJobId, reproducibleXP);
                            if (reproducibleXP) {
                                ReproducibleExperimentConclusion conclusion = new ReproducibleExperimentConclusion("REPRODUCIBLE_EXPERIMENT_CONCLUSION", doc);
                                return conclusion;
                            } else {
                                ReproducibleExperimentConclusion conclusion = new ReproducibleExperimentConclusion("REPRODUCIBLE_EXPERIMENT_CONCLUSION", doc);
                                return conclusion;
                            }
                        }
                    }
                }
            }
            if (s instanceof NegativeReproducibleMetricsConclusion) {
                if (s.getElement() != null) {
                    for (int j = 0; j < supportList.size(); j++) {
                        Support s2 = supportList.get(j);
                        if (s2 instanceof ValidXpConclusion) {
                            ReproducibleDocument ReproducibleConclusionDoc = (ReproducibleDocument) s.getElement();
                            XpDocument ValidXPConclusionDoc = (XpDocument) s2.getElement();
                            String ReproducibleConclusionJobId = ReproducibleConclusionDoc.getJobId();
                            String ValidXPConclusionJobId = ValidXPConclusionDoc.getJobId();
                            if (ReproducibleConclusionJobId.equals(ValidXPConclusionJobId)) {
                                doc = new ReproducibleDocument(ReproducibleConclusionJobId, false);
                                ReproducibleExperimentConclusion conclusion = new ReproducibleExperimentConclusion("REPRODUCIBLE_EXPERIMENT_CONCLUSION", doc);
                                return conclusion;
                            }
                        }
                    }
                    ReproducibleExperimentConclusion conclusion = new ReproducibleExperimentConclusion("REPRODUCIBLE_EXPERIMENT_CONCLUSION", doc);
                    return conclusion;
                }
            }
            ReproducibleExperimentConclusion conclusion = new ReproducibleExperimentConclusion("REPRODUCIBLE_EXPERIMENT_CONCLUSION", doc);
            return conclusion;
        }
        ReproducibleExperimentConclusion conclusion = new ReproducibleExperimentConclusion("REPRODUCIBLE_EXPERIMENT_CONCLUSION", doc);
        return conclusion;

    }

}
