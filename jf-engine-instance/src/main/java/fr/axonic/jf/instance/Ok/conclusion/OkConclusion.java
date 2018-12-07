package fr.axonic.jf.instance.Ok.conclusion;

//import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
//import fr.axonic.jf.engine.support.instance.DocumentConclusion;


public class OkConclusion extends Conclusion<Document> {

    public OkConclusion(String name, Document element) {
        super(name, element);
    }

    public OkConclusion() {
        super();
    }
}
