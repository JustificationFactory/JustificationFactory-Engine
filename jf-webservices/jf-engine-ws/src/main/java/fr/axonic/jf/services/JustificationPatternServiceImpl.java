package fr.axonic.jf.services;

import fr.axonic.jf.ArtifactType;
import fr.axonic.jf.databases.JustificationSystemsBD;
import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.JustificationSystemAPI;
import fr.axonic.jf.engine.pattern.ListPatternsBase;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.type.SupportType;
import fr.axonic.jf.engine.pattern.type.Type;
import fr.axonic.jf.engine.strategy.HumanStrategy;
import fr.axonic.jf.engine.support.evidence.Evidence;
import fr.axonic.jf.ArtifactType;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

@Path("/justification")
@RequestScoped
public class JustificationPatternServiceImpl implements JustificationPatternService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JustificationPatternServiceImpl.class);

    @Inject
    private JustificationSystemsBD justificationSystemsBD;

    @Override
    public Response registerPattern(String argumentationSystemId, Pattern pattern) {
        JustificationSystem<ListPatternsBase> argumentationSystem = justificationSystemsBD.getJustificationSystems().get(argumentationSystemId);
        argumentationSystem.getPatternsBase().addPattern(pattern);
        return Response.status(Response.Status.ACCEPTED).entity(pattern.getId()).build();
    }

    @Override
    public Response getJustificationSystemPatterns(String argumentationSystemId) {
        JustificationSystemAPI argumentationSystem = justificationSystemsBD.getJustificationSystems().get(argumentationSystemId);
        if (argumentationSystem == null) {
            LOGGER.warn("Unknown {}, impossible to provide", argumentationSystemId);
            return Response.status(Response.Status.NOT_FOUND).entity("No justification system with id " + argumentationSystemId).build();
        }
        LOGGER.info("{} Justification System patterns provided", argumentationSystemId);
        return Response.status(Response.Status.OK).entity(argumentationSystem.getPatternsBase().getPatterns().stream().map(Pattern::getId).collect(Collectors.toList())).build();
    }

    @Override
    public Response getJustificationSystemPattern(String argumentationSystemId, String pattern) {
        JustificationSystemAPI argumentationSystem = justificationSystemsBD.getJustificationSystems().get(argumentationSystemId);
        if (argumentationSystem == null) {
            LOGGER.warn("Unknown {}, impossible to provide", argumentationSystemId);
            return Response.status(Response.Status.NOT_FOUND).entity("No justification system with id " + argumentationSystemId).build();
        }
        LOGGER.info("Pattern {} for {} Justification System provided", pattern, argumentationSystemId);
        return Response.status(Response.Status.OK).entity(argumentationSystem.getPatternsBase().getPattern(pattern)).build();

    }

    @Override
    public Response getArtifactTypes(String artifact) {
        ArtifactType artifactType = ArtifactType.valueOf(artifact.toUpperCase());
        Reflections reflections = new Reflections("fr.axonic.jf");
        List<Class> classes = new ArrayList<>();
        for (Class clas : artifactType.getClasses()) {
            Set<Class<? extends Evidence>> classs =
                    reflections.getSubTypesOf(clas);
            for (Class c : classs) {
                if (!Modifier.isAbstract(c.getModifiers())) {
                    classes.add(c);
                }
            }
        }

        return Response.status(Response.Status.OK).entity(classes).build();
    }

    @Override
    public Response getArtifactTypesUsable(String argumentationSystem) {
        List<Pattern> patterns = justificationSystemsBD.getJustificationSystems().get(argumentationSystem).getPatternsBase().getPatterns();
        Set<Type> humanClasses = new HashSet<>();
        Set<Type> computedClasses = new HashSet<>();
        for (Pattern pattern : patterns) {
            if (pattern.getStrategy() instanceof HumanStrategy) {
                humanClasses.addAll(pattern.getInputTypes().stream().map(SupportType::getType).collect(Collectors.toList()));
                humanClasses.add(pattern.getOutputType().getType());
            } else {
                computedClasses.addAll(pattern.getInputTypes().stream().map(SupportType::getType).collect(Collectors.toList()));
                computedClasses.add(pattern.getOutputType().getType());
            }
        }

        Map<ArtifactType, Set<Type>> res = new EnumMap<>(ArtifactType.class);
        res.put(ArtifactType.HUMAN_STRATEGY, humanClasses);
        res.put(ArtifactType.COMPUTED_STRATEGY, computedClasses);
        return Response.status(Response.Status.OK).entity(res).build();
    }
}
