package fr.axonic.avek.instance.redmine;

import fr.axonic.avek.engine.support.evidence.Document;
import fr.axonic.avek.engine.support.evidence.Evidence;

public class RedmineDocumentApproval extends Evidence<Document> {

    public RedmineDocumentApproval(String name, Document element) {
        super(name, element);
    }

    public RedmineDocumentApproval() {
        super();
    }
}
