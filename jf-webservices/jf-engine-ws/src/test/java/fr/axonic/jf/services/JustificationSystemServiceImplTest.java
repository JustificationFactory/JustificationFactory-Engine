package fr.axonic.jf.services;


import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.JustificationSystemAPI;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 17/03/17.
 */
@Ignore
public class JustificationSystemServiceImplTest extends JerseyTest {

    @Override
    protected Application configure() {
        ResourceConfig config = new ResourceConfig(JustificationSystemServiceImpl.class);
        config.register(new JustificationWSTestBinder());

        return config;
    }

    @Test
    public void testGetArgumentationSystems() {
        Response argumentationSystem = target("/justification/systems").request().get();
        assertNotNull(argumentationSystem);
        assertEquals(Response.Status.OK, argumentationSystem.getStatusInfo());

        List systems = argumentationSystem.readEntity(List.class);
        assertNotNull(systems);
        assertFalse(systems.isEmpty());
    }

    @Test
    public void testGetArgumentationSystem() {
        Response argumentationSystemResponse = target("/justification/CLINICAL_STUDIES").request().get();
        assertNotNull(argumentationSystemResponse);
        assertEquals(Response.Status.OK, argumentationSystemResponse.getStatusInfo());

        JustificationSystem justificationSystem = argumentationSystemResponse.readEntity(JustificationSystem.class);
        assertNotNull(justificationSystem);
    }

    @Test
    public void testRegisterArgumentationSystem() {
        JustificationSystemAPI argumentationSystem = new JustificationSystem();

        Response argSystem = target("/justification/system").request().post(Entity.json(argumentationSystem));
        assertNotNull(argSystem);
        assertEquals(Response.Status.ACCEPTED, argSystem.getStatusInfo());

        String system = argSystem.readEntity(String.class);
        assertNotNull(system);
    }
}