package fr.axonic.jf.instance.ReproducibleExperiment.evidences;
import fr.axonic.jf.engine.support.evidence.Evidence;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.ThresholdDocument;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccuracyReproducibilityThreshold extends Evidence<ThresholdDocument> {
    public static final String EVIDENCE_NAME = "ACCURACY_THRESHOLD_EVIDENCE";
    public static final int VALUE = 0;

    public AccuracyReproducibilityThreshold(ThresholdDocument element) {
        super(EVIDENCE_NAME, element);
    }

    public AccuracyReproducibilityThreshold() {

    }

}
