package fr.axonic.jf.engine.pattern.type;

import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.evidence.Evidence;
import fr.axonic.jf.instance.conclusion.ExperimentationConclusion;
import fr.axonic.jf.instance.evidence.Stimulation;
import fr.axonic.jf.instance.evidence.StimulationEvidence;
import fr.axonic.jf.instance.evidence.Subject;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 17/03/17.
 */
public class InputTypeTest {
    @Test
    public void testGoodEvidenceCreation() throws WrongEvidenceException, VerificationException {
        InputType<Evidence> evidenceRoleType= new InputType<Evidence>("stimulation classType",Evidence.class);
        Evidence<Stimulation> evidence=new Evidence<Stimulation>("stimulation", new Stimulation());
        evidenceRoleType.create(evidence);
    }

    @Test(expected=WrongEvidenceException.class)
    public void testWrongEvidenceCreation() throws WrongEvidenceException, VerificationException {
        InputType<StimulationEvidence> evidenceRoleType= new InputType<>("stimulation classType",StimulationEvidence.class);

        Evidence<Subject> evidence=new Evidence<Subject>("subject", new Subject());
        evidenceRoleType.create(evidence);
    }

    @Test
    public void testGoodEvidenceRoleFromEvidenceRoleType() throws WrongEvidenceException, VerificationException {
        InputType<Evidence> evidenceRoleType= new InputType<Evidence>("stimulation classType",Evidence.class);

        Evidence<Stimulation> evidence=new Evidence<Stimulation>("stimulation", new Stimulation());
        Support role=evidenceRoleType.create(evidence);
        assertTrue(evidenceRoleType.check(role));
    }
    @Test
    public void testWrongEvidenceRoleFromEvidenceRoleType() throws WrongEvidenceException, VerificationException {
        InputType<StimulationEvidence> evidenceRoleType= new InputType<>("stimulation classType",StimulationEvidence.class);

        Support supportRole =new Evidence("test",new Subject());
        assertFalse(evidenceRoleType.check(supportRole));
    }

    @Test
    public void isPrimitiveInputType() throws Exception {
        InputType<StimulationEvidence> evidenceRoleType= new InputType<>("stimulation classType",StimulationEvidence.class);
        assertTrue(evidenceRoleType.isPrimitiveInputType());
    }

    @Test
    public void isNotPrimitiveInputType() throws Exception {
        InputType<ExperimentationConclusion> evidenceRoleType= new InputType<>("stimulation classType",ExperimentationConclusion.class);
        assertFalse(evidenceRoleType.isPrimitiveInputType());
    }

}