package fr.axonic.jf.instance.ValidXp.conclusion;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;

public class ValidXpConclusion extends Conclusion<Document> {

    public ValidXpConclusion(String name, Document element) {
        super(name, element);
    }

    public ValidXpConclusion() {
        super();
    }
}