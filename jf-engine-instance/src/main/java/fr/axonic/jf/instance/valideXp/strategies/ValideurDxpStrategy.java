package fr.axonic.jf.instance.valideXp.strategies;

import fr.axonic.jf.instance.school.conclusions.TakeTheNecessaryToSucceedSubConclusion;
import fr.axonic.jf.instance.valideXp.ValideXpJustificationSystem;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.valideXp.conclusion.ValideXpConclusion;

import java.util.List;

public class ValideurDxpStrategy extends ValideXpStrategy{

    public ValideurDxpStrategy(String name) {
        super(name);
    }

    public ValideurDxpStrategy() {
        super(null);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        return new ValideXpConclusion();
    }
}
