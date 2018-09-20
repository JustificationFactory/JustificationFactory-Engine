package fr.axonic.jf.engine.constraint.graph;

import fr.axonic.jf.engine.pattern.JustificationStep;
import fr.axonic.jf.engine.constraint.JustificationSystemConstraint;
import fr.axonic.jf.engine.support.evidence.Hypothesis;
import fr.axonic.jf.engine.constraint.JustificationSystemConstraint;

import java.util.List;

/**
 * Created by cduffau on 10/03/17.
 */
public class NoHypothesisConstraint implements JustificationSystemConstraint {

    @Override
    public boolean verify(List<JustificationStep> steps) {
        return steps.stream().noneMatch(step -> step.getSupports().stream().anyMatch(evidenceRole -> evidenceRole instanceof Hypothesis));
    }
}
