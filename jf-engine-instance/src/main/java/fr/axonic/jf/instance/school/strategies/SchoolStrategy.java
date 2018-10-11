package fr.axonic.jf.instance.school.strategies;

import fr.axonic.jf.engine.strategy.ComputedStrategy;

public abstract class SchoolStrategy extends ComputedStrategy {

    public SchoolStrategy(String name) {
        super(name, null, null);
    }
}
