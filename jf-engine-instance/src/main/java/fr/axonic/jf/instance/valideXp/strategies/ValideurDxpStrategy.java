package fr.axonic.jf.instance.valideXp.strategies;

import fr.axonic.jf.engine.support.evidence.Document;
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

    @Override
    public Conclusion createConclusion(List<Support> supportList) {

        ValideXpConclusion conclusion = new ValideXpConclusion();
        conclusion.setName("XP_VALIDE");
        return conclusion;
    }
}
