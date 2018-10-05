package fr.axonic.jf.instance.redmine;

import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RedmineDocumentApproval extends Evidence<Document> {

    public RedmineDocumentApproval(String name, Document element) {
        super(name, element);
    }

    public RedmineDocumentApproval() {
        super();
    }
}
