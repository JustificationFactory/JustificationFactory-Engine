package fr.axonic.avek.service;

import fr.axonic.avek.engine.pattern.Pattern;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.*;

public class JustificationPatternServiceImplTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(JustificationPatternServiceImpl.class);
    }

    @Test
    public void testGetArgumentationSystemPattern() {
        Response argumentationSystem = target("/justification/CLINICAL_STUDIES/patterns/1").request().get();
        assertNotNull(argumentationSystem);
        assertEquals(argumentationSystem.getStatusInfo(), Response.Status.OK);
        Pattern pattern = argumentationSystem.readEntity(Pattern.class);
        assertNotNull(pattern);
        assertEquals(pattern.getId(), "1");
    }

    @Test
    public void testGetArgumentationSystemPatterns() {
        Response argumentationSystem = target("/justification/CLINICAL_STUDIES/patterns").request().get();
        assertNotNull(argumentationSystem);
        assertEquals(argumentationSystem.getStatusInfo(), Response.Status.OK);
        List patterns = argumentationSystem.readEntity(List.class);
        assertNotNull(patterns);
        assertFalse(patterns.isEmpty());
    }
}