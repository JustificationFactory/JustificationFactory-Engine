package fr.axonic.jf.instance.ReproducibleExperiment.conclusion;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;

public class AccuracyMetricConclusion extends Conclusion<XpDocument> {
    public AccuracyMetricConclusion(String name, XpDocument element) {
        super(name, element);
    }

    public AccuracyMetricConclusion() {
        super();
    }
}
