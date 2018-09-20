package fr.axonic.jf.engine.pattern;


import fr.axonic.jf.engine.exception.StepBuildingException;
import fr.axonic.jf.engine.exception.StrategyException;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.instance.conclusion.ExperimentationConclusion;
import fr.axonic.jf.instance.evidence.*;
import fr.axonic.jf.instance.strategy.TreatStrategy;
import fr.axonic.jf.engine.support.evidence.Evidence;
import fr.axonic.jf.engine.strategy.*;
import fr.axonic.jf.engine.pattern.type.OutputType;
import fr.axonic.jf.engine.pattern.type.InputType;
import fr.axonic.base.engine.AList;
import fr.axonic.jf.engine.exception.StrategyException;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.strategy.Rationale;
import fr.axonic.jf.engine.strategy.Strategy;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 23/06/16.
 */
public class PatternTest {

    @Test
    public void testApplicableWithDifferentOrderEvidenceType() throws WrongEvidenceException, VerificationException {
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);
        class TestProject implements Project {

            @Override
            public String name() {
                return "test";
            }
        }
        Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject}), conclusionExperimentationType);


        StimulationEvidence stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        Evidence<Subject> subject0 = new SubjectEvidence("Subject 0",new Subject());
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0", subject0.getElement(),stimulation0.getElement());

        Support evStimulation0 = rtStimulation.create(stimulation0 );
        Support evSubject0 = rtSubject.create(subject0);
        assertTrue(treat.applicable(Arrays.asList(evSubject0,evStimulation0)));
    }

    @Test
    @Ignore
    public void testApplicableWithOptionalEvidenceType() throws WrongEvidenceException, VerificationException {
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);

        class TestProject implements Project {

            @Override
            public String name() {
                return "test";
            }
        }
        Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject}), conclusionExperimentationType);


        Evidence<Stimulation> stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        Evidence<Subject> subject0 = new SubjectEvidence("Subject 0",new Subject());
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0", subject0.getElement(),stimulation0.getElement());

        Support evStimulation0 = rtStimulation.create(stimulation0 );
        Support evSubject0 = rtSubject.create(subject0);
        assertTrue(treat.applicable(Arrays.asList(new Support[] {evSubject0,evStimulation0})));
    }

    @Test
    @Ignore
    public void testApplicableWithOptionalNotGiveEvidenceType() throws WrongEvidenceException, VerificationException {
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        //rtSubject.setOptional(true);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);
        class TestProject implements Project {

            @Override
            public String name() {
                return "test";
            }
        }
        Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject}), conclusionExperimentationType);


        StimulationEvidence stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());

        Support evStimulation0 = rtStimulation.create(stimulation0 );
        assertTrue(treat.applicable(Arrays.asList(new Support[] {evStimulation0})));
    }

    @Test
    public void testApplicableWithMultipleSameEvidences() throws VerificationException, WrongEvidenceException, StrategyException, StepBuildingException {
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        InputType<SubjectEvidence> rtSubject2 = new InputType<>("subject2", SubjectEvidence.class);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);

        //Revoir car ici on a un singleton...
        class TestProject implements Project {

            @Override
            public String name() {
                return "test";
            }
        }
        Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject, rtSubject2}), conclusionExperimentationType);


        Evidence<Stimulation> stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        Evidence<Subject> subject0 = new SubjectEvidence("Subject 0",new Subject());
        Evidence<Subject> subject1 = new SubjectEvidence("Subject 1",new Subject());
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0", subject0.getElement(),stimulation0.getElement());

        Support evStimulation0 = rtStimulation.create(stimulation0 );
        Support evSubject0 = rtSubject.create(subject0);
        Support evSubject1=rtSubject2.create(subject1);
        JustificationStep step0 = treat.createStep(Arrays.asList(evStimulation0,evSubject0,evSubject1), experimentation0);
        assertNotNull(step0);
    }

    @Test(expected = StepBuildingException.class)
    public void testApplicableWithNotEnoughSameEvidences() throws VerificationException, WrongEvidenceException, StrategyException, StepBuildingException {
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        InputType<SubjectEvidence> rtSubject2 = new InputType<>("subject2", SubjectEvidence.class);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);

        //Revoir car ici on a un singleton...
        class TestProject implements Project {

            @Override
            public String name() {
                return "test";
            }
        }
        Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject, rtSubject2}), conclusionExperimentationType);


        Evidence<Stimulation> stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        Evidence<Subject> subject0 = new SubjectEvidence("Subject 0",new Subject());
        Evidence<Subject> subject1 = new SubjectEvidence("Subject 1",new Subject());
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0", subject0.getElement(),stimulation0.getElement());

        Support evStimulation0 = rtStimulation.create(stimulation0 );
        Support evSubject0 = rtSubject.create(subject0);

        JustificationStep step0 = treat.createStep(Arrays.asList(evStimulation0,evSubject0), experimentation0);
    }


    @Test(expected = WrongEvidenceException.class)
    public void testApplicableWithNotGoodEvidenceType() throws WrongEvidenceException, VerificationException {
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        InputType<SubjectEvidence> rtResult = new InputType<>("subject", SubjectEvidence.class);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);

        class TestProject implements Project {

            @Override
            public String name() {
                return "test";
            }
        }
        Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject}), conclusionExperimentationType);
        Evidence<Result> result = new ResultsEvidence("Result 0", new Result(new AList<>()));
        Evidence<Subject> subject0 = new SubjectEvidence("Subject 0",new Subject());
        Support evResult0 = rtResult.create(result);
        Support evSubject0 = rtSubject.create(subject0);
        assertFalse(treat.applicable(Arrays.asList(new Support[] {evSubject0,evResult0})));
    }

    @Ignore
    @Test(expected = StepBuildingException.class)
    public void testStepWithNotGoodOrderEvidenceType() throws WrongEvidenceException, StepBuildingException, VerificationException, StrategyException {
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);

        class TestProject implements Project {

            @Override
            public String name() {
                return "test";
            }
        }
        Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject}), conclusionExperimentationType);


        Evidence<Stimulation> stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        Evidence<Subject> subject0 = new SubjectEvidence("Subject 0",new Subject());
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0", subject0.getElement(),stimulation0.getElement());

        Support evStimulation0 = rtStimulation.create(stimulation0 );
        Support evSubject0 = rtSubject.create(subject0);
        treat.createStep(Arrays.asList(evSubject0,evStimulation0),experimentation0);
    }

    @Test
    public void testGoodStep() throws WrongEvidenceException, StepBuildingException, VerificationException, StrategyException {
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);

        //Revoir car ici on a un singleton...
        class TestProject implements Project {

            @Override
            public String name() {
                return "test";
            }
        }
        Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject}), conclusionExperimentationType);


        Evidence<Stimulation> stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        Evidence<Subject> subject0 = new SubjectEvidence("Subject 0",new Subject());
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0", subject0.getElement(),stimulation0.getElement());

        Support evStimulation0 = rtStimulation.create(stimulation0 );
        Support evSubject0 = rtSubject.create(subject0);
        JustificationStep step0 = treat.createStep(Arrays.asList(evStimulation0,evSubject0), experimentation0);
        assertNotNull(step0);
        assertNotNull(step0.getConclusion());
        assertNotNull(step0.getSupports());
        assertNotNull(step0.getStrategy());
        assertEquals(step0.getSupports().size(),2);
    }

}