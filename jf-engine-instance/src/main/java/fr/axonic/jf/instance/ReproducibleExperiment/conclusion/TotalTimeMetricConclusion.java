package fr.axonic.jf.instance.ReproducibleExperiment.conclusion;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;

public class TotalTimeMetricConclusion extends Conclusion<Document> {
    public TotalTimeMetricConclusion(String name, Document element) {
        super(name, element);
    }

    public TotalTimeMetricConclusion() {
        super();
    }
}
