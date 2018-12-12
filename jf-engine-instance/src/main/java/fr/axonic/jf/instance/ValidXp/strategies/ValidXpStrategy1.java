package fr.axonic.jf.instance.ValidXp.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.ValidXp.conclusion.ValidXpConclusion;

import java.util.List;






public class ValidXpStrategy1 extends ValidXpStrategy {

    public ValidXpStrategy1(String name) {
        super(name);
    }
    public ValidXpStrategy1() {
        super(null);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        Document doc = new Document("http://link-to-our-student-ci.io");
        ValidXpConclusion conclusion = new ValidXpConclusion("VALID_XP_C",doc);
        return conclusion;
    }

}