package fr.axonic.jf.instance.school.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.school.conclusions.TakeTheNecessaryToSucceedSubConclusion;

import java.util.List;

public class WorkHardStrategy extends SchoolStrategy {

    public WorkHardStrategy(String name) {
        super(name);
    }

    public WorkHardStrategy() {
        super(null);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        return new TakeTheNecessaryToSucceedSubConclusion();
    }
}
