package fr.axonic.jf.instance.redmine;

import fr.axonic.jf.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RedmineDocumentEvidence extends Evidence<RedmineDocument> {

    public RedmineDocumentEvidence(String name, RedmineDocument element) {
        super(name, element);
    }

    public RedmineDocumentEvidence() {
        super();
    }
}
