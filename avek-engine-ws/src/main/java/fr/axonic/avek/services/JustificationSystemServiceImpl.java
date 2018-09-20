package fr.axonic.avek.services;

import fr.axonic.avek.dao.JustificationSystemsDAO;
import fr.axonic.avek.databases.JustificationSystemsBD;
import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.JustificationSystemAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

/**
 * Created by cduffau on 16/01/17.
 */
@Path("/justification")
public class JustificationSystemServiceImpl implements JustificationSystemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JustificationSystemServiceImpl.class);

    @Inject
    private JustificationSystemsBD justificationSystemsBD;

    @Inject
    private JustificationSystemsDAO justificationSystemsDAO;

    @Override
    public Response registerJustificationSystem(String name, JustificationSystem justificationSystem) {
        justificationSystemsBD.getJustificationSystems().put(name, justificationSystem);
        try {
            justificationSystemsDAO.saveJustificationSystem(name, justificationSystem);
        } catch (IOException e) {
            LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(name).build();
        }

        LOGGER.info("{} Justification System added", name);

        return Response.status(Response.Status.ACCEPTED).entity(name).build();
    }

    @Override
    public Response registerJustificationSystem(JustificationSystem justificationSystem) {
        String id = UUID.randomUUID().toString();
        return registerJustificationSystem(id, justificationSystem);
    }

    @Override
    public Response removeJustificationSystem(String argumentationSystemId) {
        if (justificationSystemsBD.getJustificationSystems().remove(argumentationSystemId) == null) {
            LOGGER.warn("Unknown {}, impossible to remove", argumentationSystemId);
            return Response.status(Response.Status.NOT_FOUND).entity("No justification system with id " + argumentationSystemId).build();
        }
        try {
            justificationSystemsDAO.removeJustificationSystem(argumentationSystemId);
        } catch (IOException e) {
            LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(argumentationSystemId).build();
        }
        LOGGER.info("{} Justification System removed", argumentationSystemId);
        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response getJustificationSystems() {
        Set<String> argumentationSystemsID = justificationSystemsBD.getJustificationSystems().keySet();
        if (argumentationSystemsID.isEmpty()) {
            LOGGER.warn("No argumentation systems");
            return Response.status(Response.Status.NOT_FOUND).entity("No justification systems").build();
        }
        LOGGER.info("Justification systems provided");
        return Response.status(Response.Status.OK).entity(argumentationSystemsID).build();
    }

    @Override
    public Response getJustificationSystem(String argumentationSystemId) {
        JustificationSystemAPI argumentationSystem = justificationSystemsBD.getJustificationSystems().get(argumentationSystemId);
        if (argumentationSystem == null) {
            LOGGER.warn("Unknown {}, impossible to provide", argumentationSystemId);
            return Response.status(Response.Status.NOT_FOUND).entity("No justification system with id " + argumentationSystemId).build();
        }
        LOGGER.info("{} Justification System provided", argumentationSystemId);
        return Response.status(Response.Status.OK).entity(argumentationSystem).build();
    }
}
