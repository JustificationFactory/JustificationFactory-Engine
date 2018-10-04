package fr.axonic.jf.bus.services;

import fr.axonic.jf.bus.configuration.JustificationBusTestBinder;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.redmine.RedmineDocument;
import fr.axonic.jf.instance.redmine.RedmineDocumentApproval;
import fr.axonic.jf.instance.redmine.RedmineDocumentEvidence;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JustificationBusServiceImplementationTestIT extends JerseyTest {

    @Override
    protected Application configure() {
        ResourceConfig config = new ResourceConfig(JustificationBusServiceImplementation.class);
        config.register(new JustificationBusTestBinder());

        return config;
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