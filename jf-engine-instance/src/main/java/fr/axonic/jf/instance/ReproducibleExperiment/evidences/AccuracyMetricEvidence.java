package fr.axonic.jf.instance.ReproducibleExperiment.evidences;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccuracyMetricEvidence extends Conclusion<Document> {
    public static final String EVIDENCE_NAME = "ACCURACY_METRIC_EVIDENCE";

    public AccuracyMetricEvidence(String name, Document element) {
        super(name, element);
    }

    public AccuracyMetricEvidence() {
    }
}
