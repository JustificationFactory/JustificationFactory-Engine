package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.strategy.ComputedStrategy;
import fr.axonic.jf.engine.strategy.Rationale;
import fr.axonic.jf.engine.strategy.UsageDomain;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

public abstract class ReproducibleExperimentStrategy extends ComputedStrategy {
    public ReproducibleExperimentStrategy() {
    }

    public ReproducibleExperimentStrategy(String name, Rationale<JournalOfExperimentalSocialPsychology> rationale, UsageDomain usageDomain) {
        super(name, rationale, usageDomain);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        return null;
    }
}
