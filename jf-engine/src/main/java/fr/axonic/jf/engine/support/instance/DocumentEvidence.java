package fr.axonic.jf.engine.support.instance;

import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cduffau on 24/08/17.
 */
@XmlRootElement
public class DocumentEvidence extends Evidence<Document> {
    public DocumentEvidence(String name, Document element) {
        super(name, element);
    }

    public DocumentEvidence() {
        super();
    }
}
