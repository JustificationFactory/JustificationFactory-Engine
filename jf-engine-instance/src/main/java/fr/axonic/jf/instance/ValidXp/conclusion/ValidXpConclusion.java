package fr.axonic.jf.instance.ValidXp.conclusion;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;

public class ValidXpConclusion extends Conclusion<XpDocument> {

    public ValidXpConclusion(String name, XpDocument element) {
        super(name, element);
    }

    public ValidXpConclusion() {
        super();
    }
}