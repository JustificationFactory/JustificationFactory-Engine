package fr.axonic.avek.instance.redmine;

import fr.axonic.avek.engine.support.evidence.Evidence;

public class RedmineDocumentEvidence extends Evidence<RedmineDocument> {

    public RedmineDocumentEvidence(String name, RedmineDocument element) {
        super(name, element);
    }

    public RedmineDocumentEvidence() {
        super();
    }
}
