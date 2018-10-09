package fr.axonic.jf.services;

import fr.axonic.jf.dao.JustificationSystemsDAO;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/justification")
@RequestScoped
public class JustificationConformanceServiceImpl implements JustificationConformanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JustificationConformanceServiceImpl.class);

    @Inject
    private JustificationSystemsDAO justificationSystemsDAO;

    @Override
    public Response checkJustificationSystemCompleteness(String name) {
        boolean complete = false;
        try {
            complete = justificationSystemsDAO.getJustificationSystem(name).isComplete();
        } catch (IOException e) {
            LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(name).build();
        }
        return Response.status(Response.Status.OK).entity(complete).build();
    }

    @Override
    public Response compareJustificationDiagrams(String justificationDiagram, String justificationDiagram2) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }

    @Override
    public Response deriveJustificationDiagrams(String justificationDiagram, String justificationDiagram2) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }

    @Override
    public Response achieveJustificationDiagram(String justificationDiagram) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }

    @Override
    public Response alignJustificationDiagrams(String justificationDiagram, String justificationDiagram2) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }
}
