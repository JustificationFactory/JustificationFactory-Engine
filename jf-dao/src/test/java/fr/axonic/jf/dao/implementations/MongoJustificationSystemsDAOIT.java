package fr.axonic.jf.dao.implementations;

import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.instance.JustificationSystemEnum;
import fr.axonic.jf.instance.JustificationSystemFactory;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MongoJustificationSystemsDAOIT {

    private MongoJustificationSystemsDAO dao;

    @Before
    public void initialize() throws IOException {
        dao = new MongoJustificationSystemsDAO("mongodb://localhost:27018","jf","justificationSystems");
        dao.getJustificationSystems().keySet().forEach(dao::removeJustificationSystem);
    }

    @Test
    public void shouldAddOneSystem() throws VerificationException, WrongEvidenceException, IOException {
        JustificationSystem js = JustificationSystemFactory.create(JustificationSystemEnum.REDMINE);

        assertEquals(0, dao.getJustificationSystems().size());

        dao.saveJustificationSystem("REDMINE", js);

        assertEquals(1, dao.getJustificationSystems().size());
    }

    @Test
    public void shouldAddSeveralSystems() throws VerificationException, WrongEvidenceException, IOException {
        JustificationSystem js1 = JustificationSystemFactory.create(JustificationSystemEnum.REDMINE);
        JustificationSystem js2 = JustificationSystemFactory.create(JustificationSystemEnum.JENKINS);

        assertEquals(0, dao.getJustificationSystems().size());

        dao.saveJustificationSystem("REDMINE", js1);
        dao.saveJustificationSystem("JENKINS", js2);

        assertEquals(2, dao.getJustificationSystems().size());
    }

    @Test
    public void shouldOverrideSystemWithSameName() throws VerificationException, WrongEvidenceException, IOException {
        JustificationSystem js1 = JustificationSystemFactory.create(JustificationSystemEnum.REDMINE);
        JustificationSystem js2 = JustificationSystemFactory.create(JustificationSystemEnum.JENKINS);

        assertEquals(0, dao.getJustificationSystems().size());

        dao.saveJustificationSystem("REDMINE", js1);

        assertEquals(1, dao.getJustificationSystems().size());

        dao.saveJustificationSystem("REDMINE", js2);

        assertEquals(1, dao.getJustificationSystems().size());
    }

    @Test
    public void shouldDeleteSystem() throws VerificationException, WrongEvidenceException, IOException {
        JustificationSystem js = JustificationSystemFactory.create(JustificationSystemEnum.REDMINE);

        assertEquals(0, dao.getJustificationSystems().size());

        dao.saveJustificationSystem("REDMINE", js);

        assertEquals(1, dao.getJustificationSystems().size());

        dao.removeJustificationSystem("REDMINE");

        assertEquals(0, dao.getJustificationSystems().size());
    }

    @Test
    public void shouldNotDeleteIfNotExist() throws IOException {
        assertEquals(0, dao.getJustificationSystems().size());

        dao.removeJustificationSystem("REDMINE");

        assertEquals(0, dao.getJustificationSystems().size());
    }
}