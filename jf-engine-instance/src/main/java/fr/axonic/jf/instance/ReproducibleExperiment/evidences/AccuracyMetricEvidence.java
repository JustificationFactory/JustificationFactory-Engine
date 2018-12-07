package fr.axonic.jf.instance.ReproducibleExperiment.evidences;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccuracyMetricEvidence extends Evidence<Document> {
    public static final String EVIDENCE_NAME = "ACCURACY_METRIC_EVIDENCE";

    public AccuracyMetricEvidence(Document element) {
        super(EVIDENCE_NAME, element);
    }

    public AccuracyMetricEvidence() {
    }
}
