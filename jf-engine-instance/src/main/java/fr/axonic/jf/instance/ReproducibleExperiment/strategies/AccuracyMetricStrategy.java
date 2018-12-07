package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusion;

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
        Document doc = new Document("http://link-to-our-student-ci.io");
        AccuracyMetricConclusion conclusion = new AccuracyMetricConclusion("ACCURACY_METRIC_CONCLUSION",doc);
        return conclusion;
    }
}
