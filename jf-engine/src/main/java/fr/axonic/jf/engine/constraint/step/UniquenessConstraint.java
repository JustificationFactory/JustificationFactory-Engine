package fr.axonic.jf.engine.constraint.step;

import fr.axonic.jf.engine.constraint.PatternConstraint;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.JustificationStep;
import fr.axonic.jf.engine.constraint.PatternConstraint;

import java.util.List;

/**
 * Created by cduffau on 09/03/17.
 */
public class UniquenessConstraint extends PatternConstraint {

    public UniquenessConstraint(Pattern pattern) {
        super(pattern);
    }

    @Override
    public boolean verify(List<JustificationStep> steps) {
        return steps.stream().filter(step -> patterns.get(0).getId().equals(step.getPatternId())).count()<=1;
    }
}
