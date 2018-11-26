package fr.axonic.jf.instance.ValidXp.evidence;

import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LogEvidence extends Evidence<Document> {
    public static final String EVIDENCE_NAME = "LOG_E";

    public LogEvidence(Document element) {
        super(EVIDENCE_NAME, element);
    }

    public LogEvidence() {
    }
}
