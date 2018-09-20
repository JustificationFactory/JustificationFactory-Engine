package fr.axonic.jf.instance.strategy;

import fr.axonic.jf.engine.strategy.HumanStrategy;
import fr.axonic.jf.engine.strategy.Rationale;
import fr.axonic.jf.engine.strategy.UsageDomain;
import fr.axonic.jf.engine.strategy.HumanStrategy;
import fr.axonic.jf.engine.strategy.Rationale;
import fr.axonic.jf.engine.strategy.UsageDomain;


public class TreatStrategy extends HumanStrategy {

	public TreatStrategy(Rationale rationale, UsageDomain usageDomain) {
		super("Treat",rationale, usageDomain);
	}

	public TreatStrategy() {
		this(null,null);
	}
// createConclusion according to Evidences
	/**public Conclusion createConclusion(Map<String,Evidence> evidenceRoles){
		return new ExperimentationConclusion((Subject)evidenceRoles.get("subject").getElement(),(Stimulation)evidenceRoles.get("stimulation").getElement());
	}
	//	new Conclusion();
	//}*/

}
