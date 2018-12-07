package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.strategy.ComputedStrategy;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;

import java.util.List;

public abstract class ReproducibleExperimentStrategy extends ComputedStrategy {
    public ReproducibleExperimentStrategy() {
    }

    public ReproducibleExperimentStrategy(String name) {
        super(name, null, null);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        return null;
    }
}
