package fr.axonic.jf.engine.constraint.pattern.intra;

import fr.axonic.jf.engine.constraint.InvalidPatternConstraint;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.type.SupportType;
import fr.axonic.jf.engine.strategy.Actor;
import fr.axonic.jf.engine.strategy.HumanStrategy;
import fr.axonic.jf.engine.strategy.Role;
import fr.axonic.jf.engine.constraint.InvalidPatternConstraint;
import fr.axonic.jf.engine.strategy.HumanStrategy;
import fr.axonic.jf.engine.strategy.Role;

/**
 * Created by cduffau on 22/03/17.
 */
public class ActorTypePatternConstraint extends SupportTypePatternConstraint<Actor> {


    public ActorTypePatternConstraint(Pattern pattern, SupportType<Actor> supportType, Role roleMinimum) throws InvalidPatternConstraint {
        super(pattern, supportType, step -> step.getStrategy() instanceof HumanStrategy && step.getSupports().stream().allMatch(supportRole -> !supportType.check(supportRole) || ((Actor)supportRole).getRole().ordinal()<=roleMinimum.ordinal()));
    }
}
