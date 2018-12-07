package fr.axonic.jf.instance.Ok.strategie;

import fr.axonic.jf.engine.strategy.ComputedStrategy;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;

import java.util.List;


public abstract class OkStrategy extends ComputedStrategy {

    public OkStrategy(String name) {
        super(name, null, null);
    }

    public OkStrategy() {
        super();
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        return null;
    }
}
