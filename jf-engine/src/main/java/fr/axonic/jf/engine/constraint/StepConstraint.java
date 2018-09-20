package fr.axonic.jf.engine.constraint;

import fr.axonic.jf.engine.pattern.JustificationStep;

/**
 * Created by cduffau on 24/03/17.
 */
public interface StepConstraint {

    boolean verify(JustificationStep step);
}
