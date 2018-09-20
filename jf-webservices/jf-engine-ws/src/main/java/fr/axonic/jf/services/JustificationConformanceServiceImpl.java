package fr.axonic.jf.services;

import fr.axonic.jf.databases.JustificationSystemsBD;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/justification")
@RequestScoped
public class JustificationConformanceServiceImpl implements JustificationConformanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JustificationConformanceServiceImpl.class);

    @Inject
    private JustificationSystemsBD justificationSystemsBD;

    @Override
    public Response checkJustificationSystemCompleteness(String name) {
        boolean complete = justificationSystemsBD.getJustificationSystems().get(name).isComplete();
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
