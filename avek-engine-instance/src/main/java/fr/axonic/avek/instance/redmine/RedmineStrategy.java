package fr.axonic.avek.instance.redmine;

import fr.axonic.avek.engine.strategy.ComputedStrategy;

public abstract class RedmineStrategy extends ComputedStrategy {

    public RedmineStrategy(String name) {
        super(name, null, null);
    }

    public RedmineStrategy() {
    }
}
