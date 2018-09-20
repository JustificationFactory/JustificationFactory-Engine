package fr.axonic.jf.instance.clinical.strategy;

import fr.axonic.jf.engine.strategy.*;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;

import java.util.List;

/**
 * Created by cduffau on 09/08/16.
 */
public class GeneralizeStrategy extends ComputedStrategy {

    public GeneralizeStrategy(Rationale rationale, UsageDomain usageDomain) {
        super("Generalize", rationale, usageDomain);
    }

    public GeneralizeStrategy() {
        this(null, null);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        return null;
    }
}
