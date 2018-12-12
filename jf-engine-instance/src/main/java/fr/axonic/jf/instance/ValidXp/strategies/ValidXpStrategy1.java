package fr.axonic.jf.instance.ValidXp.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.ReproducibleDocument;
import fr.axonic.jf.instance.ReproducibleExperiment.evidences.TotalTimeMetricEvidence;
import fr.axonic.jf.instance.ReproducibleExperiment.strategies.MetricAnalysis;
import fr.axonic.jf.instance.ValidXp.conclusion.ValidXpConclusion;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;
import fr.axonic.jf.instance.ValidXp.evidence.LogEvidence;
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
        for (int i = 0; i < supportList.size(); i++){
            Support s = supportList.get(i);
            if (s instanceof LogEvidence){
                LogEvidence logEvidence = (LogEvidence) s;
                boolean isValid = (logEvidence.getElement().getExitCode() == 0);
                doc = new XpDocument(logEvidence.getElement().getJobId(),isValid);
            }
        }
        ValidXpConclusion conclusion = new ValidXpConclusion("VALID_XP_C",doc);
        return conclusion;
    }

}
