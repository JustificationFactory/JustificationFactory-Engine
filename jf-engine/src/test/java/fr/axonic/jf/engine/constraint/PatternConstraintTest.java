package fr.axonic.jf.engine.constraint;

import fr.axonic.jf.engine.*;
import fr.axonic.jf.engine.exception.StepBuildingException;
import fr.axonic.jf.engine.exception.StrategyException;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.pattern.ListPatternsBase;
import fr.axonic.jf.engine.strategy.Actor;
import fr.axonic.jf.engine.strategy.Role;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.instance.MockedJustificationSystem;
import fr.axonic.jf.instance.conclusion.ExperimentationConclusion;
import fr.axonic.jf.instance.evidence.Stimulation;
import fr.axonic.jf.instance.evidence.StimulationEvidence;
import fr.axonic.jf.instance.evidence.Subject;
import fr.axonic.jf.instance.evidence.SubjectEvidence;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.type.InputType;
import fr.axonic.jf.engine.exception.StrategyException;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.strategy.Role;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cduffau on 08/03/17.
 */
public abstract class PatternConstraintTest {

    protected Pattern pattern;
    protected JustificationSystem<ListPatternsBase> argumentationSystem;
    protected ExperimentationConclusion experimentation0;
    protected Support evStimulation0;
    protected Support evSubject0;
    protected Support evActor0;

    @Before
    public void setUp() throws VerificationException, WrongEvidenceException, StrategyException, StepBuildingException {

        argumentationSystem=new MockedJustificationSystem();
        pattern= argumentationSystem.getPatternsBase().getPattern("1");
        argumentationSystem.getPatternsBase().setConstraints(new ArrayList<>());
        StimulationEvidence stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        SubjectEvidence subject0 = new SubjectEvidence("Subject 0",new Subject());
        Actor actor=new Actor("Chloé", Role.SENIOR_EXPERT);
        experimentation0 = new ExperimentationConclusion("Experimentation 0",subject0.getElement(),stimulation0.getElement());
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        InputType<Actor> rtActor = new InputType<>("actor", Actor.class);
        evStimulation0 = rtStimulation.create(stimulation0 );
        evSubject0 = rtSubject.create(subject0);
        evActor0=rtActor.create(actor);

        argumentationSystem.constructStep(pattern,Arrays.asList(evStimulation0,evSubject0,evActor0), experimentation0);
    }


}