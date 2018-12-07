package fr.axonic.jf.instance.ReproducibleExperiment.conclusion;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;

public class AccuracyMetricConclusion extends Conclusion<Document> {
    public AccuracyMetricConclusion(String name, Document element) {
        super(name, element);
    }

    public AccuracyMetricConclusion() {
        super();
    }
}
