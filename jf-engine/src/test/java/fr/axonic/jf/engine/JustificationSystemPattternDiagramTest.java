package fr.axonic.jf.engine;

import fr.axonic.jf.engine.exception.StepBuildingException;
import fr.axonic.jf.engine.exception.StrategyException;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.pattern.DiagramPatternsBase;
import fr.axonic.jf.engine.pattern.ListPatternsBase;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.type.InputType;
import fr.axonic.jf.engine.strategy.Actor;
import fr.axonic.jf.engine.strategy.Role;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.instance.MockedJustificationSystem;
import fr.axonic.jf.instance.conclusion.ExperimentationConclusion;
import fr.axonic.jf.instance.evidence.Stimulation;
import fr.axonic.jf.instance.evidence.StimulationEvidence;
import fr.axonic.jf.instance.evidence.Subject;
import fr.axonic.jf.instance.evidence.SubjectEvidence;
import fr.axonic.jf.instance.strategy.TreatStrategy;
import fr.axonic.jf.engine.exception.StrategyException;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.strategy.Role;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class JustificationSystemPattternDiagramTest {


    private JustificationSystem<DiagramPatternsBase> argumentationSystem;

    @Before
    public void setUp() throws VerificationException, WrongEvidenceException {
        argumentationSystem= new MockedJustificationSystem<>(MockedJustificationSystem.getAXONICDiagramPattern());
    }

    private void createAStep(Role role) throws VerificationException, WrongEvidenceException, StrategyException, StepBuildingException {
        Pattern pattern= argumentationSystem.getPatternsBase().getPattern("1");
        StimulationEvidence stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        SubjectEvidence subject0 = new SubjectEvidence("Subject 0",new Subject());
        Actor actor0=new Actor("Chloé", role);
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0",subject0.getElement(),stimulation0.getElement());
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        InputType<Actor> rtActor=new InputType<>("actor", Actor.class);
        Support evStimulation0 = rtStimulation.create(stimulation0 );
        Support evSubject0 = rtSubject.create(subject0);
        Support evActor0=rtActor.create(actor0);

        argumentationSystem.constructStep(pattern, Arrays.asList(evStimulation0,evSubject0,evActor0), experimentation0);

    }

    @Test
    public void getPattern() throws Exception {
        assertTrue(argumentationSystem.getPatternsBase().getPattern("1").getId().equals("1"));
    }

    @Test
    public void constructStep() throws Exception {
        createAStep(Role.SENIOR_EXPERT);
        assertTrue(argumentationSystem.getJustificationDiagram().getSteps().size()==1);
        assertTrue(argumentationSystem.getJustificationDiagram().getSteps().get(0).getPatternId().equals("1"));
        assertTrue(argumentationSystem.getJustificationDiagram().getSteps().get(0).getSupports().size()==3);
        assertTrue(argumentationSystem.getJustificationDiagram().getSteps().get(0).getStrategy() instanceof TreatStrategy);
    }

    @Test(expected = StepBuildingException.class)
    public void constructTwoStepFromOnePattern() throws Exception {
        createAStep(Role.SENIOR_EXPERT);
        assertTrue(argumentationSystem.getJustificationDiagram().getSteps().size()==1);
        assertTrue(argumentationSystem.getJustificationDiagram().getSteps().get(0).getPatternId().equals("1"));
        assertTrue(argumentationSystem.getJustificationDiagram().getSteps().get(0).getSupports().size()==3);
        assertTrue(argumentationSystem.getJustificationDiagram().getSteps().get(0).getStrategy() instanceof TreatStrategy);
        createAStep(Role.SENIOR_EXPERT);
    }


}
