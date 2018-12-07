package fr.axonic.jf.instance.Ok.strategie;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.Ok.conclusion.OkConclusion;

import java.util.List;

public class OkStrategies extends OkStrategy {

    public OkStrategies(String name) {
        super(name);
    }
    public OkStrategies() {
        super(null);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        Document doc = new Document("http://link-to-our-student-ci.io");
        OkConclusion conclusion = new OkConclusion("OK_C",doc);


        return conclusion;
    }

}
