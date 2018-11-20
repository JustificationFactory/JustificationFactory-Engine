package fr.axonic.jf.instance.valideXp.evidence;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ResultAnalyseEvidence extends Evidence<Document> {

    public static final String EVIDENCE_NAME = "ResultAnalyse";

    public ResultAnalyseEvidence(Document element) {
        super(EVIDENCE_NAME, element);
    }

    public ResultAnalyseEvidence() {
    }
}

