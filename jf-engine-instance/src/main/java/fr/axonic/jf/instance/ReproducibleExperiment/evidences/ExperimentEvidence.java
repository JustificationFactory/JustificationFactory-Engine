package fr.axonic.jf.instance.ReproducibleExperiment.evidences;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExperimentEvidence extends Evidence<Document> {
    public static final String EVIDENCE_NAME = "EXPERIMENT_EVIDENCE";

    public ExperimentEvidence(Document element) {
        super(EVIDENCE_NAME, element);
    }

    public ExperimentEvidence() {
    }
}
