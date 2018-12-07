package fr.axonic.jf.instance.ReproducibleExperiment.evidences;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TotalTimeMetricEvidence extends Conclusion<Document> {
    public static final String EVIDENCE_NAME = "TOTAL_TIME_METRIC_EVIDENCE";

    public TotalTimeMetricEvidence(String name, Document element) {
        super(name, element);
    }

    public TotalTimeMetricEvidence() {
    }
}
