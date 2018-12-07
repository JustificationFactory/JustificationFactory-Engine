package fr.axonic.jf.instance.ReproducibleExperiment.conclusion;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;

public class ReproducibleMetricsConclusion extends Conclusion<Document> {
    public ReproducibleMetricsConclusion(String name, Document element) {
        super(name, element);
    }

    public ReproducibleMetricsConclusion() {
        super();
    }
}
