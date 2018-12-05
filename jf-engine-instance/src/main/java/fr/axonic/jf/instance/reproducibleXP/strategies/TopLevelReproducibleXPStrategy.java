package fr.axonic.jf.instance.reproducibleXP.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.reproducibleXP.conclusions.ReproducibleMetricsConclusion;
import fr.axonic.jf.instance.reproducibleXP.conclusions.ReproducibleXPConclusion;

import java.util.List;

public class TopLevelReproducibleXPStrategy extends ReproducibleXPStrategy {
    public TopLevelReproducibleXPStrategy(String name) {
        super(name);
    }

    public TopLevelReproducibleXPStrategy()  {
        super(null);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        //TODO: Analyse validXP conclusions and conclusions of metrics strategies & return conclusion
        ReproducibleXPConclusion conclusion = new ReproducibleXPConclusion();
        conclusion.setName("REPRODUCIBLE_XP_CONCLUSION");
        return conclusion;
    }

}
