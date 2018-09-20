package fr.axonic.avek.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import fr.axonic.avek.dao.JerseyMapperProvider;
import fr.axonic.avek.dao.JustificationSystemsDAO;
import fr.axonic.avek.databases.JustificationSystemsBD;
import fr.axonic.avek.engine.JustificationSystemAPI;
import fr.axonic.avek.engine.StepToCreate;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.JustificationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/justification")
public class JustificationDiagramServiceImpl implements JustificationDiagramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JustificationDiagramServiceImpl.class);

    @Inject
    private JustificationSystemsBD justificationSystemsBD;

    @Inject
    private JustificationSystemsDAO justificationSystemsDAO;

    @Override
    public Response constructStep(String argumentationSystem, String pattern, StepToCreate step) {

        try {
            JustificationStep res = justificationSystemsBD.getJustificationSystems().get(argumentationSystem).constructStep(justificationSystemsBD.getJustificationSystems().get(argumentationSystem).getPatternsBase().getPattern(pattern), step.getSupports(), step.getConclusion());
            LOGGER.info("Step created on {} with pattern {}", argumentationSystem, pattern);
            try {
                justificationSystemsDAO.saveJustificationSystem(argumentationSystem, justificationSystemsBD.getJustificationSystems().get(argumentationSystem));
            } catch (IOException e) {
                LOGGER.error(e.toString());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(argumentationSystem).build();
            }
            return Response.status(Response.Status.CREATED).entity(res).build();
        } catch (StepBuildingException | WrongEvidenceException e) {
            LOGGER.error("Error during Step creation on {} with pattern {}", argumentationSystem, pattern);
            LOGGER.error(e.toString());
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(e.getStackTrace()).build();
        }
    }

    @Override
    public Response clearSteps(String argumentationSystemId) {
        JustificationSystemAPI argumentationSystem = justificationSystemsBD.getJustificationSystems().get(argumentationSystemId);
        if (argumentationSystem == null) {
            LOGGER.warn("Unknown {}, impossible to remove", argumentationSystemId);
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id " + argumentationSystemId).build();
        } else {
            argumentationSystem.getJustificationDiagram().getSteps().clear();
            try {
                justificationSystemsDAO.saveJustificationSystem(argumentationSystemId, argumentationSystem);
            } catch (IOException e) {
                LOGGER.error(e.toString());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(argumentationSystemId).build();
            }
            LOGGER.info("{} Justification System Justification Diagram removed", argumentationSystemId);
            return Response.status(Response.Status.OK).build();
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
        JustificationSystemAPI argumentationSystem = justificationSystemsBD.getJustificationSystems().get(argumentationSystemId);
        if (argumentationSystem == null) {
            LOGGER.warn("Unknown {}, impossible to remove", argumentationSystemId);
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id " + argumentationSystemId).build();
        } else {
            return Response.status(Response.Status.FOUND).entity(argumentationSystem.matrix()).build();
        }
    }
}
