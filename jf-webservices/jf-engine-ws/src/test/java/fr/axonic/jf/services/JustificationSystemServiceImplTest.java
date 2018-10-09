package fr.axonic.jf.services;

import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.JustificationSystemAPI;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.*;

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
        assertEquals(200, argumentationSystem.getStatus());

        List systems = argumentationSystem.readEntity(List.class);
        assertNotNull(systems);
        assertFalse(systems.isEmpty());
    }

    @Test
    public void testGetArgumentationSystem() {
        Response argumentationSystemResponse = target("/justification/CLINICAL_STUDIES").request().get();
        assertNotNull(argumentationSystemResponse);
        assertEquals(200, argumentationSystemResponse.getStatus());

        JustificationSystem justificationSystem = argumentationSystemResponse.readEntity(JustificationSystem.class);
        assertNotNull(justificationSystem);
    }

    @Test
    public void testRegisterArgumentationSystem() {
        JustificationSystemAPI argumentationSystem = new JustificationSystem();

        Response argSystem = target("/justification/system").request().post(Entity.json(argumentationSystem));
        assertNotNull(argSystem);
        assertEquals(202, argSystem.getStatus());

        String system = argSystem.readEntity(String.class);
        assertNotNull(system);
    }
}