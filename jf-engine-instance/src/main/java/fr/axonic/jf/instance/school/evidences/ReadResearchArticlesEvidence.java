package fr.axonic.jf.instance.school.evidences;

import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReadResearchArticlesEvidence extends Evidence<Document> {

    public static final String EVIDENCE_NAME = "HAVE READ RESEARCH ARTICLES";

    public ReadResearchArticlesEvidence(Document element) {
        super(EVIDENCE_NAME, element);
    }

    public ReadResearchArticlesEvidence() {
    }
}
