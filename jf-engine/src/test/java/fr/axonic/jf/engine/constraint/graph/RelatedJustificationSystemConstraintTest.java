package fr.axonic.jf.engine.constraint.graph;

import fr.axonic.jf.engine.constraint.JustificationSystemConstraint;
import fr.axonic.jf.engine.constraint.PatternConstraintTest;
import fr.axonic.jf.engine.pattern.JustificationStep;
import fr.axonic.jf.engine.pattern.type.InputType;
import fr.axonic.jf.engine.strategy.HumanStrategy;
import fr.axonic.jf.engine.strategy.Strategy;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Evidence;
import fr.axonic.jf.instance.conclusion.Effect;
import fr.axonic.jf.instance.evidence.Stimulation;
import fr.axonic.jf.engine.constraint.JustificationSystemConstraint;
import fr.axonic.jf.engine.constraint.PatternConstraintTest;
import fr.axonic.jf.engine.strategy.HumanStrategy;
import fr.axonic.jf.engine.strategy.Strategy;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 21/03/17.
 */
public class RelatedJustificationSystemConstraintTest extends PatternConstraintTest {

    @Test
    public void verifyConnexeWithOneStep() throws Exception {
        JustificationSystemConstraint justificationSystemConstraint =new RelatedJustificationSystemConstraint();
        assertTrue(justificationSystemConstraint.verify(argumentationSystem.getJustificationDiagram().getSteps()));
    }

    @Test
    public void verifyConnexeWithMoreThanOneStep() throws Exception {
        Strategy strategy=new HumanStrategy();
        InputType evidenceRoleType1=new InputType("Test1", Evidence.class);
        Evidence evidence1=new Evidence("evidence1", new Stimulation());
        Support supportRole1 =evidenceRoleType1.create(evidence1);

        InputType evidenceRoleType2=new InputType("Test2", Evidence.class);
        Evidence evidence2=new Evidence("evidence2", new Stimulation());
        Support supportRole2 =evidenceRoleType2.create(evidence2);
        Conclusion conclusion=new Conclusion("conclusion1",new Effect());
        Conclusion conclusion2=new Conclusion("conclusion2",new Effect());
        Conclusion conclusion3=new Conclusion("conclusion3",new Effect());

        InputType conclusionRoleType2=new InputType("Test3", Conclusion.class);
        Support conclusionRole1=conclusionRoleType2.create(conclusion);
        InputType conclusionRoleType3=new InputType("Test4", Conclusion.class);
        Support conclusionRole2=conclusionRoleType3.create(conclusion2);


        JustificationStep step1=new JustificationStep("1",strategy,Arrays.asList(supportRole1, supportRole2), conclusion);
        JustificationStep step2=new JustificationStep("1",strategy,Arrays.asList(supportRole1, supportRole2, conclusionRole1), conclusion3);
        JustificationStep step3=new JustificationStep("1",strategy,Arrays.asList(supportRole1, supportRole2, conclusionRole1), conclusion2);
        JustificationSystemConstraint justificationSystemConstraint =new RelatedJustificationSystemConstraint();
        assertTrue(justificationSystemConstraint.verify(Arrays.asList(step1,step2,step3)));
    }

    @Test
    public void verifyNotConnexe() throws Exception {
        Strategy strategy=new HumanStrategy();
        InputType evidenceRoleType1=new InputType("Test1", Evidence.class);
        Evidence evidence1=new Evidence("evidence1", new Stimulation());
        Support supportRole1 =evidenceRoleType1.create(evidence1);

        InputType evidenceRoleType2=new InputType("Test2", Evidence.class);
        Evidence evidence2=new Evidence("evidence2", new Stimulation());
        Support supportRole2 =evidenceRoleType2.create(evidence2);
        Conclusion conclusion=new Conclusion("conclusion1",new Effect());
        Conclusion conclusion2=new Conclusion("conclusion2",new Effect());
        Conclusion conclusion3=new Conclusion("conclusion3",new Effect());

        InputType conclusionRoleType2=new InputType("Test3", Conclusion.class);
        Support conclusionRole1=conclusionRoleType2.create(conclusion);
        InputType conclusionRoleType3=new InputType("Test4", Conclusion.class);
        Support conclusionRole2=conclusionRoleType3.create(conclusion2);


        JustificationStep step1=new JustificationStep("1",strategy,Arrays.asList(supportRole1, supportRole2), conclusion);
        JustificationStep step2=new JustificationStep("1",strategy,Arrays.asList(supportRole1, supportRole2, conclusionRole1), conclusion3);
        JustificationStep step3=new JustificationStep("1",strategy,Arrays.asList(supportRole1, supportRole2), conclusion2);
        JustificationSystemConstraint justificationSystemConstraint =new RelatedJustificationSystemConstraint();
        assertFalse(justificationSystemConstraint.verify(Arrays.asList(step1,step2,step3)));
    }
}