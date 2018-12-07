package fr.axonic.jf.instance.ReproducibleExperiment.conclusion;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;

public class ReproducibleExperimentConclusion extends Conclusion<Document> {
    public ReproducibleExperimentConclusion(String name, Document element) {
        super(name, element);
    }

    public ReproducibleExperimentConclusion() {
    }
}
