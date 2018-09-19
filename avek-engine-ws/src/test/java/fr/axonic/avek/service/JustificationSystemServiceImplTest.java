package fr.axonic.avek.service;


import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.JustificationSystemAPI;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 17/03/17.
 */
public class JustificationSystemServiceImplTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(JustificationSystemServiceImpl.class);
    }

    @Test
    public void testGetArgumentationSystems() {
        Response argumentationSystem = target("/justification/systems").request().get();
        assertNotNull(argumentationSystem);
        assertEquals(argumentationSystem.getStatusInfo(), Response.Status.OK);
        List systems = argumentationSystem.readEntity(List.class);
        assertNotNull(systems);
        assertFalse(systems.isEmpty());
    }

    @Test
    public void testGetArgumentationSystem() {
        Response argumentationSystemResponse = target("/justification/CLINICAL_STUDIES").request().get();
        assertNotNull(argumentationSystemResponse);
        assertEquals(argumentationSystemResponse.getStatusInfo(), Response.Status.OK);
        JustificationSystem justificationSystem = argumentationSystemResponse.readEntity(JustificationSystem.class);
        assertNotNull(justificationSystem);
    }

    @Test
    public void testRegisterArgumentationSystem() {
        JustificationSystemAPI argumentationSystem = new JustificationSystem();
        Response argSystem = target("/justification/system").request().post(Entity.json(argumentationSystem));
        assertNotNull(argSystem);
        assertEquals(argSystem.getStatusInfo(), Response.Status.ACCEPTED);
        String system = argSystem.readEntity(String.class);
        assertNotNull(system);
    }
}