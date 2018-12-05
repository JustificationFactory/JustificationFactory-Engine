package fr.axonic.jf.instance.reproducibleXP.evidences;

import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.engine.support.evidence.Evidence;

public class TotalTimeMetric extends Evidence<Document> {
    public static final String EVIDENCE_NAME = "TOTALTIME_METRIC";

    public TotalTimeMetric(Document element) {
        super(EVIDENCE_NAME, element);
    }

}
