package fr.axonic.jf.instance.ValidXp.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.ReproducibleDocument;
import fr.axonic.jf.instance.ReproducibleExperiment.evidences.TotalTimeMetricEvidence;
import fr.axonic.jf.instance.ReproducibleExperiment.strategies.MetricAnalysis;
import fr.axonic.jf.instance.ValidXp.conclusion.NegativeValidXpConclusion;
import fr.axonic.jf.instance.ValidXp.conclusion.PositiveValidXpConclusion;
import fr.axonic.jf.instance.ValidXp.conclusion.ValidXpConclusion;
import fr.axonic.jf.instance.ValidXp.documents.LogDocument;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;
import fr.axonic.jf.instance.ValidXp.evidence.LogEvidence;
import fr.axonic.jf.instance.ValidXp.evidence.XpEvidence;
import sun.rmi.runtime.Log;

import java.util.List;






public class ValidXpStrategy1 extends ValidXpStrategy {

    public ValidXpStrategy1(String name) {
        super(name);
    }
    public ValidXpStrategy1() {
        super(null);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        XpDocument doc = null;
        ValidXpConclusion conclusion = new NegativeValidXpConclusion("VALID_XP_C",doc);
        for (int i = 0; i < supportList.size(); i++) {
            Support s = supportList.get(i);
            if (s instanceof LogEvidence) {
                for (int j = 0; j < supportList.size(); j++) {
                    Support s2 = supportList.get(j);
                    if (s2 instanceof XpEvidence) {
                        LogDocument logDoc = ((LogDocument) s.getElement());
                        XpDocument xpDoc = ((XpDocument) s2.getElement());
                        String logId = logDoc.getJobId();
                        String xpId = xpDoc.getJobId();
                        boolean sameId = logId.equals(xpId);
                        boolean isValid = (logDoc.getExitCode() == 0);
                        if (sameId && isValid) {
                            doc = new XpDocument(logId, true);
                            conclusion = new PositiveValidXpConclusion("VALID_XP_C", doc);
                            return conclusion;
                        }
                        else {
                            if (sameId) {
                                doc = new XpDocument(logId, false);
                                conclusion = new NegativeValidXpConclusion("VALID_XP_C", doc);
                                return conclusion;
                            } else {
                                conclusion = new NegativeValidXpConclusion("VALID_XP_C", null);
                            }
                        }
                    }
                }
            }
        }


        return conclusion;
    }

}
