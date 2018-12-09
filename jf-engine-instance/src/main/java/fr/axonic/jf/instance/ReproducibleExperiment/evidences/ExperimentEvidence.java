package fr.axonic.jf.instance.ReproducibleExperiment.evidences;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.engine.support.evidence.Evidence;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExperimentEvidence  extends Evidence<XpDocument> {
    public static final String EVIDENCE_NAME = "EXPERIMENT_EVIDENCE";

    public ExperimentEvidence(XpDocument element) {
        super(EVIDENCE_NAME, element);
    }

    public ExperimentEvidence() {
    }
}
