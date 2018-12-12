package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.ReproducibleExperimentConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.documents.ReproducibleDocument;
import fr.axonic.jf.instance.ValidXp.documents.XpDocument;

import java.util.List;

public class ReproducibleExperimentFinalStrategy extends ReproducibleExperimentStrategy {
    public ReproducibleExperimentFinalStrategy() {
    }

    public ReproducibleExperimentFinalStrategy(String name) {
        super(name);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        ReproducibleDocument doc = new ReproducibleDocument("testReproducible Final",true);
        ReproducibleExperimentConclusion conclusion = new ReproducibleExperimentConclusion("REPRODUCIBLE_EXPERIMENT_CONCLUSION",doc);
        return conclusion;
    }
}
