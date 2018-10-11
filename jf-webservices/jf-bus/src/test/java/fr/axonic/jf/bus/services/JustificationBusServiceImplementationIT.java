package fr.axonic.jf.bus.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.axonic.jf.bus.configuration.JustificationBusTestBinder;
import fr.axonic.jf.dao.JerseyMapperProvider;
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
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JustificationBusServiceImplementationIT extends JerseyTest {

    private JustificationBusTestBinder binder;

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
        TransmittedSupports supports = new TransmittedSupports();
        supports.setSupports(Arrays.asList(
                new ContinuousIntegrationSystemEvidence(new Document("http://ci.com")),
                new ProjectGradeEvidence(new ProjectGradeDocument("http://grade.edu", 18)),
                new ReadResearchArticlesEvidence(new Document("http://edu.gouv"))
        ));

        Response ok = target("/bus/supports").request().post(Entity.json(supports));

        assertNotNull(ok);
        assertEquals(200, ok.getStatus());
    }

    public static void main(String[] args) throws JsonProcessingException {
        TransmittedSupports supports = new TransmittedSupports();
        supports.setSupports(Arrays.asList(
                new ContinuousIntegrationSystemEvidence(new Document("http://link-to-our-student-ci.io"))/*,
                new ProjectGradeEvidence(new ProjectGradeDocument("http://grade.edu", 18)),
                new ReadResearchArticlesEvidence(new Document("http://edu.gouv"))*/
        ));

        System.out.println(new JerseyMapperProvider().getContext(null).writeValueAsString(supports));
    }

    @Test
    public void shouldBuildOnlyOneStep() {
        TransmittedSupports supports = new TransmittedSupports();
        supports.setSupports(Arrays.asList(
                evidence("SWAM_ST_0001", "A"),
                approval("SWAM_ST_0001", "A"),
                evidence("SWAM_ST_0003", "A"),
                approval("SWAM_ST_0003", "A")));

        Response ok = target("/bus/supports").request().post(Entity.json(supports));

        assertNotNull(ok);
        assertEquals(200, ok.getStatus());
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