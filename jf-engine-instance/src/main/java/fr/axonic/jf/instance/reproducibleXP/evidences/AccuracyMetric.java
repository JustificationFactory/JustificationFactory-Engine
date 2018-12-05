package fr.axonic.jf.instance.reproducibleXP.evidences;

import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccuracyMetric extends Evidence<Document> {
    public static final String EVIDENCE_NAME = "ACCURACY_METRIC";

    public AccuracyMetric(Document element) {
        super(EVIDENCE_NAME, element);
    }


    public AccuracyMetric() {
    }
}
