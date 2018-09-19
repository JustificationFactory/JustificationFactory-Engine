package fr.axonic.avek.bus;

import fr.axonic.avek.bus.services.JustificationBusServiceImplementation;
import fr.axonic.avek.bus.services.TransmittedSupports;
import fr.axonic.avek.dao.JustificationSystemsDAO;
import fr.axonic.avek.dao.implementations.SimpleJustificationSystemsDAO;
import fr.axonic.avek.engine.support.evidence.Document;
import fr.axonic.avek.instance.redmine.RedmineDocument;
import fr.axonic.avek.instance.redmine.RedmineDocumentApproval;
import fr.axonic.avek.instance.redmine.RedmineDocumentEvidence;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Ignore;
import org.junit.Test;

import javax.inject.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JustificationBusServiceImplementationTest extends JerseyTest {

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
}