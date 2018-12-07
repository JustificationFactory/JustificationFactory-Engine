package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.ReproducibleMetricsConclusion;

import java.util.List;

public class ReproducibleMetricsStrategy extends ReproducibleExperimentStrategy {
    public ReproducibleMetricsStrategy() {
    }

    public ReproducibleMetricsStrategy(String name) {
        super(name);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        Document doc = new Document("http://link-to-our-student-ci.io");
        ReproducibleMetricsConclusion conclusion = new ReproducibleMetricsConclusion("REPRODUCIBLE_METRICS_CONCLUSION",doc);
        return conclusion;
    }
}
