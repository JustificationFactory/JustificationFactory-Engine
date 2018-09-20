package fr.axonic.jf.engine.constraint.pattern.intra;

import fr.axonic.jf.engine.constraint.JustificationSystemConstraint;
import fr.axonic.jf.engine.constraint.StepConstraint;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.JustificationStep;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.constraint.JustificationSystemConstraint;
import fr.axonic.jf.engine.constraint.StepConstraint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by cduffau on 21/03/17.
 */
public class StepConstructionConstraint implements StepConstraint, JustificationSystemConstraint {

    private Pattern pattern;

    public StepConstructionConstraint(Pattern pattern){
        this.pattern=pattern;
    }

    @Override
    public boolean verify(JustificationStep step){
        if(!pattern.getOutputType().check(step.getConclusion())){
            return false;
        }
        if(pattern.getInputTypes().size()!=step.getSupports().size()) {
         return false;
        }
        List<Support> supports=new ArrayList<>(step.getSupports());
        return pattern.getInputTypes().stream().allMatch(inputType -> {Optional<Support> s=supports.stream().filter(inputType::check).findAny(); supports.remove(s.get()); return s.isPresent();});
    }

    @Override
    public boolean verify(List<JustificationStep> steps) {
        List<JustificationStep> patternSteps=steps.stream().filter(step -> step.getPatternId().equals(pattern.getId())).distinct().collect(Collectors.toList());
        return patternSteps.stream().allMatch(this::verify);
    }
}
