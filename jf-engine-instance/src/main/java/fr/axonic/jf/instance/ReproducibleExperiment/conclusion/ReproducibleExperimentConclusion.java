package fr.axonic.jf.instance.ReproducibleExperiment.conclusion;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.ReproducibleDocument;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;

public class ReproducibleExperimentConclusion extends Conclusion<ReproducibleDocument> {
    public ReproducibleExperimentConclusion(String name, ReproducibleDocument element) {
        super(name, element);
    }

    public ReproducibleExperimentConclusion() {
    }
}
