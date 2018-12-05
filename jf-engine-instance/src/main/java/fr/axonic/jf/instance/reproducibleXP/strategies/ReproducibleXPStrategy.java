package fr.axonic.jf.instance.reproducibleXP.strategies;

import fr.axonic.jf.engine.strategy.ComputedStrategy;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.reproducibleXP.conclusions.ReproducibleMetricsConclusion;
import fr.axonic.jf.instance.reproducibleXP.conclusions.ReproducibleXPConclusion;

import java.util.List;

public abstract class ReproducibleXPStrategy extends ComputedStrategy {
    public ReproducibleXPStrategy(String name) {
        super(name, null, null);
    }

}
