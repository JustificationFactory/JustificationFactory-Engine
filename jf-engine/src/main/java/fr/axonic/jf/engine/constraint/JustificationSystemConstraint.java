package fr.axonic.jf.engine.constraint;

import fr.axonic.jf.engine.pattern.JustificationStep;

import java.util.List;

/**
 * Created by cduffau on 09/03/17.
 */
public interface JustificationSystemConstraint {

    boolean verify(List<JustificationStep> steps);

}
