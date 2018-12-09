package fr.axonic.jf.instance.ReproducibleExperiment.evidences;

import fr.axonic.jf.engine.support.evidence.Evidence;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.MetricDocument;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccuracyMetricEvidence extends Evidence<MetricDocument> {
    public static final String EVIDENCE_NAME = "ACCURACY_METRIC_EVIDENCE";

    public AccuracyMetricEvidence(MetricDocument element) {
        super(EVIDENCE_NAME, element);
    }

    public AccuracyMetricEvidence() {
    }
}
