package fr.axonic.jf.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import fr.axonic.jf.dao.JerseyMapperProvider;
import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.engine.JustificationSystemAPI;
import fr.axonic.jf.engine.StepToCreate;
import fr.axonic.jf.engine.exception.StepBuildingException;
import fr.axonic.jf.engine.exception.StrategyException;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.pattern.JustificationStep;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/justification")
@RequestScoped
public class JustificationDiagramServiceImpl implements JustificationDiagramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JustificationDiagramServiceImpl.class);


    @Inject
    private JustificationSystemsDAO justificationSystemsDAO;

    @Override
    public Response constructStep(String argumentationSystem, String pattern, StepToCreate step) {

        try {
            JustificationSystemAPI js = justificationSystemsDAO.getJustificationSystem(argumentationSystem);
            JustificationStep res = js.constructStep(js.getPatternsBase().getPattern(pattern), step.getSupports(), step.getConclusion());
            LOGGER.info("Step created on {} with pattern {}", argumentationSystem, pattern);
            justificationSystemsDAO.saveJustificationSystem(argumentationSystem, js);
            return Response.status(Response.Status.CREATED).entity(res).build();
        } catch (IOException e) {
            LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(argumentationSystem).build();
        } catch (StepBuildingException | WrongEvidenceException | StrategyException e) {
            LOGGER.error("Error during Step creation on {} with pattern {}", argumentationSystem, pattern);
            LOGGER.error(e.toString());
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(e.getStackTrace()).build();
        }

    }

    @Override
    public Response clearSteps(String argumentationSystemId) {

        try {
            JustificationSystemAPI argumentationSystem = justificationSystemsDAO.getJustificationSystem(argumentationSystemId);
            if (argumentationSystem == null) {
                LOGGER.warn("Unknown {}, impossible to remove", argumentationSystemId);
                return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id " + argumentationSystemId).build();
            } else {
                argumentationSystem.getJustificationDiagram().getSteps().clear();
                justificationSystemsDAO.saveJustificationSystem(argumentationSystemId, argumentationSystem);
                LOGGER.info("{} Justification System Justification Diagram removed", argumentationSystemId);
                return Response.status(Response.Status.OK).build();
            }

        } catch (IOException e) {
            LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(argumentationSystemId).build();
        }

    }

    @Override
    public Response getTypeContent(String type) {
        try {
            Class clas = Class.forName(type);
            JerseyMapperProvider jerseyMapperProvider = new JerseyMapperProvider();
            JsonSchema schema = jerseyMapperProvider.getContext(null).generateJsonSchema(clas);

            return Response.status(Response.Status.OK).entity(jerseyMapperProvider.getContext(null).writerWithDefaultPrettyPrinter().writeValueAsString(schema)).build();

        } catch (ClassNotFoundException | JsonProcessingException e) {
            LOGGER.error(e.toString());
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(e.getStackTrace()).build();
        }
    }

    @Override
    public Response getMatrixTransformation(String argumentationSystemId) {
        JustificationSystemAPI argumentationSystem = null;
        try {
            argumentationSystem = justificationSystemsDAO.getJustificationSystem(argumentationSystemId);
        } catch (IOException e) {
            LOGGER.warn("Unknown {}, impossible to remove", argumentationSystemId);
            LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(argumentationSystemId).build();
        }
        if (argumentationSystem == null) {
            LOGGER.warn("Unknown {}, impossible to remove", argumentationSystemId);
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id " + argumentationSystemId).build();
        } else {
            return Response.status(Response.Status.FOUND).entity(argumentationSystem.matrix()).build();
        }
    }
}
