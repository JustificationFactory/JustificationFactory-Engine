package fr.axonic.jf.engine.constraint.pattern.intra;

import fr.axonic.jf.engine.pattern.JustificationStep;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.support.Support;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReApplicablePatternConstraint extends ApplicablePatternConstraint{


    private final List<JustificationStep> steps;

    public ReApplicablePatternConstraint(Pattern pattern, List<JustificationStep> steps) {
        super(pattern);
        this.steps=steps;
    }

    @Override
    public boolean verify(List<Support> supports) {
        if(!super.verify(supports)){
            return false;
        }
        else{
            Optional<JustificationStep> step=steps.stream().filter(justificationStep -> justificationStep.getPatternId().equals(pattern.getId())).findFirst();
            List<Support> usefulEvidences= new ArrayList<>(supports);
            usefulEvidences.removeAll(step.get().getSupports());
            usefulEvidences=pattern.filterUsefulEvidences(usefulEvidences);
            return super.verify(usefulEvidences);
        }
    }
}
