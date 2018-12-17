package fr.axonic.jf.instance.ReproducibleExperiment.conclusion.TotalTimeMetricConclusions;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.ReproducibleDocument;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;
import fr.axonic.jf.instance.clinical.conclusion.EstablishedEffect;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
public abstract class TotalTimeMetricConclusion <T extends ReproducibleDocument> extends Conclusion<ReproducibleDocument> {
    public TotalTimeMetricConclusion(String name, ReproducibleDocument element) {
        super(name, element);
    }

    public TotalTimeMetricConclusion() {
        super();
    }
}
