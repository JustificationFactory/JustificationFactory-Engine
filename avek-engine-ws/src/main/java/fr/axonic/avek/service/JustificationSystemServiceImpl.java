package fr.axonic.avek.service;

import fr.axonic.avek.dao.SimpleJustificationSystemsDAO;
import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.JustificationSystemAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;

/**
 * Created by cduffau on 16/01/17.
 */
@Path("/justification")
public class JustificationSystemServiceImpl implements JustificationSystemService {

    private static final Logger LOGGER= LoggerFactory.getLogger(JustificationSystemServiceImpl.class);

    private Map<String, JustificationSystem> justificationSystems=JustificationSystemsBD.getInstance().getJustificationSystems();

    @Override
    public Response registerJustificationSystem(String name, JustificationSystem justificationSystem) {
        justificationSystems.put(name, justificationSystem);
        try {
            SimpleJustificationSystemsDAO.getInstance().saveJustificationSystem(name, justificationSystem);
        } catch (IOException e) {
            LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(name).build();
        }
        LOGGER.info(name+" Justification System added");
        return Response.status(Response.Status.ACCEPTED).entity(name).build();
    }

    @Override
    public Response registerJustificationSystem(JustificationSystem justificationSystem) {
        String id =UUID.randomUUID().toString();
        return registerJustificationSystem(id, justificationSystem);
    }

    @Override
    public Response removeJustificationSystem(String argumentationSystemId) {
        if(justificationSystems.remove(argumentationSystemId)==null){
            LOGGER.warn("Unknown "+argumentationSystemId+", impossible to remove");
            return Response.status(Response.Status.NOT_FOUND).entity("No justification system with id "+argumentationSystemId).build();
        }
        try {
            SimpleJustificationSystemsDAO.getInstance().removeJustificationSystem(argumentationSystemId);
        } catch (IOException e) {
            LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(argumentationSystemId).build();
        }
        LOGGER.info(argumentationSystemId+" Justification System removed");
        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response getJustificationSystems() {
        Set<String> argumentationSystemsID= justificationSystems.keySet();
        if(argumentationSystemsID.isEmpty()){
            LOGGER.warn("No argumentation systems");
            return Response.status(Response.Status.NOT_FOUND).entity("No justification systems").build();
        }
        LOGGER.info("Justification systems provided");
        return Response.status(Response.Status.OK).entity(argumentationSystemsID).build();

    }

    @Override
    public Response getJustificationSystem(String argumentationSystemId) {
        JustificationSystemAPI argumentationSystem= justificationSystems.get(argumentationSystemId);
        if(argumentationSystem==null){
            LOGGER.warn("Unknown "+argumentationSystemId+", impossible to provide");
            return Response.status(Response.Status.NOT_FOUND).entity("No justification system with id "+argumentationSystemId).build();
        }
        LOGGER.info(argumentationSystemId+" Justification System provided");
        return Response.status(Response.Status.OK).entity(argumentationSystem).build();
    }


}
