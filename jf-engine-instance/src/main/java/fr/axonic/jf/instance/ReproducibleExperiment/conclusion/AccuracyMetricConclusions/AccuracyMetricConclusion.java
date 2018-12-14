package fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusions;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.ReproducibleDocument;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;

public abstract class AccuracyMetricConclusion <T extends ReproducibleDocument> extends Conclusion<ReproducibleDocument> {
    public AccuracyMetricConclusion(String name, ReproducibleDocument element) {
        super(name, element);
    }

    public AccuracyMetricConclusion() {
        super();
    }
}
