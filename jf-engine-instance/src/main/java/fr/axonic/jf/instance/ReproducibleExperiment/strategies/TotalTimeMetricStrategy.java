package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.TotalTimeMetricConclusion;

import java.util.List;

public class TotalTimeMetricStrategy extends ReproducibleExperimentStrategy {
    public TotalTimeMetricStrategy() {
    }

    public TotalTimeMetricStrategy(String name) {
        super(name);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        Document doc = new Document("http://link-to-our-student-ci.io");
        TotalTimeMetricConclusion conclusion = new TotalTimeMetricConclusion("TOTAL_TIME_METRIC_CONCLUSION",doc);
        return conclusion;
    }
}
