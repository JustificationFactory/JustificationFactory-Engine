package fr.axonic.jf.instance.Ok.conclusion;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;

public class OkFinalConclusion extends Conclusion<Document> {
    public OkFinalConclusion(String name, Document element) {
        super(name, element);
    }

    public OkFinalConclusion() {
        super("OK_F_C",null);
    }
}
