package fr.axonic.jf.instance.ReproducibleExperiment.evidences;

import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TotalTimeMetricEvidence extends Evidence<Document> {
    public static final String EVIDENCE_NAME = "TOTAL_TIME_METRIC_EVIDENCE";

    public TotalTimeMetricEvidence(Document element) {
        super(EVIDENCE_NAME, element);
    }

    public TotalTimeMetricEvidence() {
    }
}
