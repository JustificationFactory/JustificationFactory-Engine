package fr.axonic.jf.instance.jenkins;

import fr.axonic.jf.engine.strategy.ComputedStrategy;
import fr.axonic.jf.engine.strategy.Rationale;
import fr.axonic.jf.engine.strategy.UsageDomain;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;

import java.util.List;

/**
 * Created by cduffau on 25/08/17.
 */
public class JenkinsStrategy extends ComputedStrategy{

    public JenkinsStrategy() {
        super();
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        return null;
    }

    public JenkinsStrategy(String name) {
        super(name, null,null);
    }
}
