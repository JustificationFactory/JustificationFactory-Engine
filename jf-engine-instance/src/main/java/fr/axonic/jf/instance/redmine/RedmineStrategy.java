package fr.axonic.jf.instance.redmine;

import fr.axonic.jf.engine.strategy.ComputedStrategy;

public abstract class RedmineStrategy extends ComputedStrategy {

    public RedmineStrategy(String name) {
        super(name, null, null);
    }

    public RedmineStrategy() {
    }
}
