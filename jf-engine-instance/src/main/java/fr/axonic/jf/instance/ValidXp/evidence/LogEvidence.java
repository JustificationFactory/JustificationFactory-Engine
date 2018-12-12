package fr.axonic.jf.instance.ValidXp.evidence;

import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.engine.support.evidence.Evidence;
import fr.axonic.jf.instance.ValidXp.documents.LogDocument;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LogEvidence extends Evidence<LogDocument> {
    public static final String EVIDENCE_NAME = "LOG_E";

    public LogEvidence(LogDocument element) {
        super(EVIDENCE_NAME, element);
    }

    public LogEvidence() {
    }
}
