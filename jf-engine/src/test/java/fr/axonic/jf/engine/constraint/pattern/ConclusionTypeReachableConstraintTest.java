package fr.axonic.jf.engine.constraint.pattern;

import fr.axonic.jf.engine.constraint.PatternConstraintTest;
import fr.axonic.jf.engine.constraint.pattern.inter.ConclusionTypeReachableConstraint;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.type.InputType;
import fr.axonic.jf.engine.pattern.type.OutputType;
import fr.axonic.jf.engine.strategy.Rationale;
import fr.axonic.jf.engine.strategy.Strategy;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.conclusion.EstablishEffectConclusion;
import fr.axonic.jf.instance.conclusion.GeneralizationConclusion;
import fr.axonic.jf.instance.evidence.ResultsEvidence;
import fr.axonic.jf.instance.evidence.Stimulation;
import fr.axonic.jf.instance.strategy.GeneralizeStrategy;
import fr.axonic.jf.engine.constraint.PatternConstraintTest;
import fr.axonic.jf.engine.constraint.pattern.inter.ConclusionTypeReachableConstraint;
import fr.axonic.jf.engine.strategy.Rationale;
import fr.axonic.jf.engine.strategy.Strategy;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 16/03/17.
 */
public class ConclusionTypeReachableConstraintTest extends PatternConstraintTest {


    @Test
    public void testConclusionTypeReachable(){
        ConclusionTypeReachableConstraint conclusionTypeReachableConstraint=new ConclusionTypeReachableConstraint(new OutputType<>(GeneralizationConclusion.class));
        assertTrue(conclusionTypeReachableConstraint.verify(argumentationSystem.getPatternsBase()));

    }

    @Test
    public void testConclusionTypeMultipleReachable(){
        InputType<EstablishEffectConclusion> rtEstablishedEffect= new InputType<>("effects", EstablishEffectConclusion.class);
        InputType<EstablishEffectConclusion> rtEstablishedEffect2= new InputType<>("effects", EstablishEffectConclusion.class);
        InputType<ResultsEvidence> rtResult = new InputType<>("experimentation", ResultsEvidence.class);
        OutputType<GeneralizationConclusion> conclusionGeneralizationType = new OutputType<>(GeneralizationConclusion.class);
        Strategy ts3=new GeneralizeStrategy(new Rationale<>(null),null);
        Pattern pattern=new Pattern("3", "Generalize", ts3, Arrays.asList(new InputType[]{rtEstablishedEffect,rtEstablishedEffect2,rtResult}),conclusionGeneralizationType);

        argumentationSystem.getPatternsBase().addPattern(pattern);
        ConclusionTypeReachableConstraint conclusionTypeReachableConstraint=new ConclusionTypeReachableConstraint(new OutputType<>(GeneralizationConclusion.class));
        assertTrue(conclusionTypeReachableConstraint.verify(argumentationSystem.getPatternsBase()));

    }

    @Test
    public void testConclusionTypeNotReachable(){
        class OtherConclusion extends Conclusion<Stimulation>{

        }
        ConclusionTypeReachableConstraint conclusionTypeReachableConstraint=new ConclusionTypeReachableConstraint(new OutputType<>(OtherConclusion.class));
        assertFalse(conclusionTypeReachableConstraint.verify(argumentationSystem.getPatternsBase()));

    }
}