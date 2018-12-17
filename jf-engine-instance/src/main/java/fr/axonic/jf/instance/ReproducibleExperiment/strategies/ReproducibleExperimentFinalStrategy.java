package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.strategy.Rationale;
import fr.axonic.jf.engine.strategy.UsageDomain;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.*;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.ReproducibleMetricsConclusions.NegativeReproducibleMetricsConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.ReproducibleMetricsConclusions.PositiveReproducibleMetricsConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.ReproducibleDocument;
import fr.axonic.jf.instance.ValidXp.conclusion.NegativeValidXpConclusion;
import fr.axonic.jf.instance.ValidXp.conclusion.PositiveValidXpConclusion;
import fr.axonic.jf.instance.ValidXp.conclusion.ValidXpConclusion;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;

import java.util.List;

public class ReproducibleExperimentFinalStrategy extends ReproducibleExperimentStrategy {
    public ReproducibleExperimentFinalStrategy() {
    }

    public ReproducibleExperimentFinalStrategy(String name, Rationale rationale, UsageDomain usageDomain) {
        super(name,rationale,usageDomain);
    }

    @Override
    public Conclusion createConclusion(List <Support> supportList) {
        ReproducibleDocument doc = null;
        ReproducibleExperimentConclusion conclusion = new NegativeExperimentConclusion("REPRODUCIBLE_EXPERIMENT_CONCLUSION", doc);

        for (int i = 0; i < supportList.size(); i++) {
            Support s = supportList.get(i);
            if ((s instanceof PositiveReproducibleMetricsConclusion || s instanceof NegativeReproducibleMetricsConclusion) && s.getElement() != null) {
                for (int j = 0; j < supportList.size(); j++) {
                    Support s2 = supportList.get(j);
                    if ((s2 instanceof PositiveValidXpConclusion || s2 instanceof NegativeValidXpConclusion) && s2.getElement() != null) {
                        ReproducibleDocument ReproducibleConclusionDoc = (ReproducibleDocument) s.getElement();
                        XpDocument ValidXPConclusionDoc = (XpDocument) s2.getElement();
                        String ReproducibleConclusionJobId = ReproducibleConclusionDoc.getJobId();
                        String ValidXPConclusionJobId = ValidXPConclusionDoc.getJobId();
                        if (ReproducibleConclusionJobId.equals(ValidXPConclusionJobId)) {
                            boolean reproducibleXP = ReproducibleConclusionDoc.isReproducible() && ValidXPConclusionDoc.getValid();
                            doc = new ReproducibleDocument(ReproducibleConclusionJobId, reproducibleXP);
                            if (reproducibleXP) {
                                conclusion = new PositiveExperimentConclusion("REPRODUCIBLE_EXPERIMENT_CONCLUSION", doc);
                                return conclusion;
                            } else {
                                conclusion = new NegativeExperimentConclusion("REPRODUCIBLE_EXPERIMENT_CONCLUSION", doc);
                                return conclusion;
                            }
                        } else {
                            conclusion = new NegativeExperimentConclusion("REPRODUCIBLE_EXPERIMENT_CONCLUSION", null);
                        }
                    } else {
                        conclusion = new NegativeExperimentConclusion("REPRODUCIBLE_EXPERIMENT_CONCLUSION", null);
                    }
                }
            } else {
                conclusion = new NegativeExperimentConclusion("REPRODUCIBLE_EXPERIMENT_CONCLUSION", null);
            }
        }

            return conclusion;
        }





}
