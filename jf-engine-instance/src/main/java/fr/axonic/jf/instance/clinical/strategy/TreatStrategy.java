package fr.axonic.jf.instance.clinical.strategy;

import fr.axonic.jf.engine.strategy.*;


public class TreatStrategy extends HumanStrategy {

	public TreatStrategy(Rationale rationale, UsageDomain usageDomain) {
		super("Treat",rationale, usageDomain);
	}

	public TreatStrategy() {
		this(null,null);
	}
}
