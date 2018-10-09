package fr.axonic.jf.services;

import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.JustificationSystemAPI;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.pattern.ListPatternsBase;
import fr.axonic.validation.exception.VerificationException;
import org.glassfish.jersey.process.internal.RequestScoped;
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
@RequestScoped
public class JustificationSystemServiceImpl implements JustificationSystemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JustificationSystemServiceImpl.class);


    @Inject
    private JustificationSystemsDAO justificationSystemsDAO;

    @Override
    public Response registerJustificationSystem(String name, JustificationSystem justificationSystem) {
        try {
            if(justificationSystem==null){
                justificationSystem=new JustificationSystem<>(new ListPatternsBase());
            }
            justificationSystemsDAO.saveJustificationSystem(name, justificationSystem);
        } catch (IOException | WrongEvidenceException | VerificationException e) {
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
        try {
            justificationSystemsDAO.removeJustificationSystem(argumentationSystemId);

        } catch (IOException e) {
            LOGGER.error(e.toString());
            LOGGER.warn("Unknown {}, impossible to remove", argumentationSystemId);
            return Response.status(Response.Status.NOT_FOUND).entity("No justification system with id " + argumentationSystemId).build();
            //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(argumentationSystemId).build();
        }
        LOGGER.info("{} Justification System removed", argumentationSystemId);
        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response getJustificationSystems() {
        Set<String> argumentationSystemsID = null;
        try {
            argumentationSystemsID = justificationSystemsDAO.getJustificationSystems().keySet();
        } catch (IOException e) {
            LOGGER.warn("No argumentation systems");
            return Response.status(Response.Status.NOT_FOUND).entity("No justification systems").build();
        }
        if (argumentationSystemsID.isEmpty()) {
            LOGGER.warn("No argumentation systems");
            return Response.status(Response.Status.NOT_FOUND).entity("No justification systems").build();
        }
        LOGGER.info("Justification systems provided");
        return Response.status(Response.Status.OK).entity(argumentationSystemsID).build();
    }

    @Override
    public Response getJustificationSystem(String argumentationSystemId) {
        JustificationSystemAPI argumentationSystem = null;
        try {
            argumentationSystem = justificationSystemsDAO.getJustificationSystem(argumentationSystemId);
        } catch (IOException e) {
            LOGGER.warn("Unknown {}, impossible to provide", argumentationSystemId);
            return Response.status(Response.Status.NOT_FOUND).entity("No justification system with id " + argumentationSystemId).build();
        }
        if (argumentationSystem == null) {
            LOGGER.warn("Unknown {}, impossible to provide", argumentationSystemId);
            return Response.status(Response.Status.NOT_FOUND).entity("No justification system with id " + argumentationSystemId).build();
        }
        LOGGER.info("{} Justification System provided", argumentationSystemId);
        return Response.status(Response.Status.OK).entity(argumentationSystem).build();
    }
}
