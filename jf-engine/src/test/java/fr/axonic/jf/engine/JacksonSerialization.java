package fr.axonic.jf.engine;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import fr.axonic.jf.engine.exception.StepBuildingException;
import fr.axonic.jf.engine.exception.StrategyException;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.JustificationStep;
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
import fr.axonic.jf.engine.exception.StrategyException;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.strategy.Role;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by cduffau on 11/08/17.
 */

public class JacksonSerialization {
    private ObjectMapper mapper;
    @Before
    public void setup(){
        mapper = new ObjectMapper();
        AnnotationIntrospector aiJaxb = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        AnnotationIntrospector aiJackson = new JacksonAnnotationIntrospector();
        // first Jaxb, second Jackson annotations
        mapper.setAnnotationIntrospector(AnnotationIntrospector.pair(aiJaxb, aiJackson));

    }

    @Test
    public void testPattern() throws IOException, VerificationException, WrongEvidenceException {
        Pattern pattern= new MockedJustificationSystem().getPatternsBase().getPattern("1");
        String json = mapper.writeValueAsString(pattern);
        System.out.println(json);
        Pattern p=mapper.readValue(json, Pattern.class);
        assertEquals(pattern,p);
    }

    @Test
    public void testStep() throws IOException, WrongEvidenceException, VerificationException, StrategyException, StepBuildingException {
        JustificationSystemAPI argumentationSystem=new MockedJustificationSystem();
        Pattern pattern= argumentationSystem.getPatternsBase().getPattern("1");
        StimulationEvidence stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        SubjectEvidence subject0 = new SubjectEvidence("Subject 0",new Subject());
        Actor actor0=new Actor("Chloé", Role.SENIOR_EXPERT);
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0",subject0.getElement(),stimulation0.getElement());
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        InputType<Actor> rtActor=new InputType<>("actor", Actor.class);
        Support evStimulation0 = rtStimulation.create(stimulation0 );
        Support evSubject0 = rtSubject.create(subject0);
        Support evActor0=rtActor.create(actor0);
        JustificationStep stepToCreate = argumentationSystem.constructStep(pattern, Arrays.asList(evStimulation0,evSubject0,evActor0), experimentation0);

        String json = mapper.writeValueAsString(stepToCreate);
        JustificationStep step=mapper.readValue(json, JustificationStep.class);
    }

    @Test
    public void testSupports() throws IOException, VerificationException, WrongEvidenceException {
        List<Support> supports=new MockedJustificationSystem().getRegisteredEvidences();
        String json = mapper.writeValueAsString(supports);
        System.out.println(json);
        /**List<SupportRole> roles=mapper.readValue(json, new TypeReference<Set<SupportRole>>() {});
        System.out.println(roles);
        assertEquals(supports,roles);*/
    }
}
