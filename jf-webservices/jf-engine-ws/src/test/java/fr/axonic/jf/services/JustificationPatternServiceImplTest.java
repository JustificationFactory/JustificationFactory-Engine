package fr.axonic.jf.services;

import fr.axonic.jf.engine.pattern.Pattern;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.*;

@Ignore
public class JustificationPatternServiceImplTest extends JerseyTest {

    @Override
    protected Application configure() {
        ResourceConfig config = new ResourceConfig(JustificationPatternServiceImpl.class);
        config.register(new JustificationWSTestBinder());

        return config;
    }

    @Test
    public void testGetArgumentationSystemPattern() {
        Response argumentationSystem = target("/justification/CLINICAL_STUDIES/patterns/1").request().get();
        assertNotNull(argumentationSystem);
        assertEquals(Response.Status.OK, argumentationSystem.getStatusInfo());

        Pattern pattern = argumentationSystem.readEntity(Pattern.class);
        assertNotNull(pattern);
        assertEquals("1", pattern.getId());
    }

    @Test
    public void testGetArgumentationSystemPatterns() {
        Response argumentationSystem = target("/justification/CLINICAL_STUDIES/patterns").request().get();
        assertNotNull(argumentationSystem);
        assertEquals(Response.Status.OK, argumentationSystem.getStatusInfo());

        List patterns = argumentationSystem.readEntity(List.class);
        assertNotNull(patterns);
        assertFalse(patterns.isEmpty());
    }
}