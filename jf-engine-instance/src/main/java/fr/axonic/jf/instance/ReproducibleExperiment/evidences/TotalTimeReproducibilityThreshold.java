package fr.axonic.jf.instance.ReproducibleExperiment.evidences;

import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TotalTimeReproducibilityThreshold extends Evidence<Document> {
    public static final String EVIDENCE_NAME = "TOTALTIME_THRESHOLD_EVIDENCE";
    public static final int VALUE = 0;

    public TotalTimeReproducibilityThreshold(Document element) {
        super(EVIDENCE_NAME, element);
    }

    public TotalTimeReproducibilityThreshold() {

    }

}
