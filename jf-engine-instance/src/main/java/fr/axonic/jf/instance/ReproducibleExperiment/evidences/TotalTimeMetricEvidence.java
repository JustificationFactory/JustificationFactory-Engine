package fr.axonic.jf.instance.ReproducibleExperiment.evidences;

import fr.axonic.jf.engine.support.evidence.Evidence;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.MetricDocument;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TotalTimeMetricEvidence extends Evidence<MetricDocument> {
    public static final String EVIDENCE_NAME = "TOTAL_TIME_METRIC_EVIDENCE";

    public TotalTimeMetricEvidence(MetricDocument element) {
        super(EVIDENCE_NAME, element);
    }

    public TotalTimeMetricEvidence() {
    }
}
