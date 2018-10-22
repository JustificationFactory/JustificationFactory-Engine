package fr.axonic.jf.bus.services;

import fr.axonic.jf.bus.configuration.JustificationBusTestBinder;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.JustificationSystemEnum;
import fr.axonic.jf.instance.JustificationSystemFactory;
import fr.axonic.jf.instance.redmine.RedmineDocument;
import fr.axonic.jf.instance.redmine.RedmineDocumentApproval;
import fr.axonic.jf.instance.redmine.RedmineDocumentEvidence;
import fr.axonic.jf.instance.school.documents.ProjectGradeDocument;
import fr.axonic.jf.instance.school.evidences.ContinuousIntegrationSystemEvidence;
import fr.axonic.jf.instance.school.evidences.ProjectGradeEvidence;
import fr.axonic.jf.instance.school.evidences.ReadResearchArticlesEvidence;
import fr.axonic.validation.exception.VerificationException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JustificationBusServiceImplementationIT extends JerseyTest {

    private JustificationBusTestBinder binder;
    private TransmittedSupports supports;

    @Override
    protected Application configure() {
        ResourceConfig config = new ResourceConfig(JustificationBusServiceImplementation.class);

        binder = new JustificationBusTestBinder();
        config.register(binder);

        return config;
    }

    @Before
    public void initialize() throws IOException, VerificationException, WrongEvidenceException {
        for (String name : binder.getDao().getJustificationSystems().keySet()) {
            binder.getDao().removeJustificationSystem(name);
        }

        binder.getDao().saveJustificationSystem("REDMINE", JustificationSystemFactory.create(JustificationSystemEnum.REDMINE));

        supports = new TransmittedSupports();
        supports.setSupports(new ArrayList<>());
    }

    @Test
    public void shouldAcceptRedmineEvidences() {
        TransmittedSupports supports = new TransmittedSupports();
        supports.setSupports(Arrays.asList(
                new RedmineDocumentEvidence("DOC", new RedmineDocument("DOC")),
                new RedmineDocumentApproval("DOC_APPROVAL", new Document("DOC_APPROVAL"))));

        Response ok = target("/bus/supports").request().post(Entity.json(supports));

        assertNotNull(ok);
        assertEquals(200, ok.getStatus());
    }

    @Test
    public void shouldAcceptSchoolEvidences() {
        supports.setSupports(Arrays.asList(
                new ContinuousIntegrationSystemEvidence(new Document("http://ci.com")),
                new ProjectGradeEvidence(new ProjectGradeDocument("http://grade.edu", 18)),
                new ReadResearchArticlesEvidence(new Document("http://edu.gouv"))
        ));

        Response ok = target("/bus/supports").request().post(Entity.json(supports));

        assertNotNull(ok);
        assertEquals(200, ok.getStatus());
    }

    @Test
    public void shouldBuildOnlyOneStep() {
        acknowledge("SWAM_ST_0001", "A");
        acknowledge("SWAM_ST_0003", "A");

        Response ok = target("/bus/supports").request().post(Entity.json(supports));

        assertNotNull(ok);
        assertEquals(200, ok.getStatus());
    }

    @Test
    public void shouldNotLoop() {
        acknowledge("SWAM_ST_0001", "A");
        acknowledge("SWAM_ST_0003", "A");
        acknowledge("SWAM_ST_0006", "A");
        acknowledge("SWAM_ST_0007", "A");
        acknowledge("SWAM_ST_0002", "A");
        acknowledge("SWAM_ST_0004", "A");
        acknowledge("SWAM_ST_0005", "A");
        acknowledge("SWAM_ST_0010", "A");
        acknowledge("SWAM_ST_0002", "B");
        acknowledge("SWAM_ST_0003", "B");
        acknowledge("SWAM_ST_0007", "B");
        acknowledge("SWAM_ST_0006", "B");
        acknowledge("SWAM_ST_0008", "A");
        acknowledge("SWAM_ST_0001", "B");
        acknowledge("SWAM_ST_0002", "C");
        acknowledge("SWAM_ST_0013", "A");
        acknowledge("SWAM_ST_0003", "C");
        acknowledge("SWAM_ST_0011", "A");
        acknowledge("SWAM_ST_0001", "C");
        acknowledge("SWAM_ST_0002", "D");
        acknowledge("SWAM_ST_0004", "B");
        acknowledge("SWAM_ST_0013", "B");
        acknowledge("SWAM_ST_0002", "E");
        acknowledge("SWAM_ST_0008", "B");
        acknowledge("SWAM_ST_0009", "A");
        acknowledge("SWAM_ST_0012", "A");

        // FIXME 22/10/2018 : At this point, everything is fine. Then the next document make the bus loop.

        acknowledge("SWAM_ST_0005", "B");

        Response ok = target("/bus/supports").request().post(Entity.json(supports));

        assertNotNull(ok);
        assertEquals(200, ok.getStatus());
    }

    private void acknowledge(String name, String version) {
        supports.getSupports().add(evidence(name, version));
        supports.getSupports().add(approval(name, version));
    }

    private static RedmineDocumentEvidence evidence(String name, String version) {
        RedmineDocument document = new RedmineDocument("http://aurl.com/" + name);
        document.setVersion(version);

        return new RedmineDocumentEvidence(name, document);
    }

    private static RedmineDocumentApproval approval(String name, String version) {
        String approvalName = name + "_APPROVAL";

        Document document = new Document("http://aurl.com/" + approvalName);
        document.setVersion(version);

        return new RedmineDocumentApproval(approvalName, document);
    }
}