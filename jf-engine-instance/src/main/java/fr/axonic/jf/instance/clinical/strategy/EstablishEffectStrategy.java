package fr.axonic.jf.instance.clinical.strategy;

import fr.axonic.jf.engine.strategy.*;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;

import java.util.List;

/**
 * Created by cduffau on 04/08/16.
 */
public class EstablishEffectStrategy extends ComputedStrategy {

    public EstablishEffectStrategy(Rationale rationale, UsageDomain usageDomain) {
        super("Establish Effect",rationale, usageDomain);
    }

    public EstablishEffectStrategy() {
        this(null, null);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        return null;
    }
}
