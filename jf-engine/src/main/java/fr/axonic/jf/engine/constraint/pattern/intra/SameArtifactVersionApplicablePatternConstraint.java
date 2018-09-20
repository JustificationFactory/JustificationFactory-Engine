package fr.axonic.jf.engine.constraint.pattern.intra;

import fr.axonic.jf.engine.constraint.PatternConstraint;
import fr.axonic.jf.engine.pattern.JustificationStep;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.support.Support;

import java.util.List;
import java.util.Optional;

public class SameArtifactVersionApplicablePatternConstraint extends ApplicablePatternConstraint {

    public SameArtifactVersionApplicablePatternConstraint(Pattern pattern) {
        super(pattern);
    }

    @Override
    public boolean verify(List<Support> supports){
        if(!super.verify(supports)){
            return false;
        }
        else{
            Optional<Support> supp=supports.stream().filter(support -> support.getElement()!=null && support.getElement().getVersion()!=null).findFirst();
            if(supp.isPresent()){
                String supportVersion=supp.get().getElement().getVersion();
                return supports.stream().allMatch(support -> support.getElement()==null || support.getElement().getVersion()==null || support.getElement().getVersion().equals(supportVersion));
            }

            return true;
        }
    }
}
