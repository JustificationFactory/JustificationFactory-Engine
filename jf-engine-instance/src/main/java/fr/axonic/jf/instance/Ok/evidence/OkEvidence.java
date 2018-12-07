package fr.axonic.jf.instance.Ok.evidence;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.engine.support.evidence.Evidence;
import fr.axonic.jf.engine.support.instance.DocumentEvidence;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OkEvidence extends Conclusion<Document> {
    public static final String EVIDENCE_NAME = "OK_E";

    public OkEvidence(String name, Document element) {
        super(name, element);
    }

    public OkEvidence() {
    }
}
