package fr.axonic.avek.bus.services;

import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.support.Support;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Collections;

@Path("/bus")
@RequestScoped
public class JustificationBusServiceImplementation implements JustificationBusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JustificationBusServiceImplementation.class);

    @Inject
    private StepBuilder stepBuilder;

    @Override
    public Response transmitSupport(TransmittedSupports supports) {
        LOGGER.info("Received {} supports.", supports.getSupports().size());

        for (Support support : supports.getSupports()) {
            try {
                stepBuilder.acknowledgeSupport(support);
            } catch (StepBuildingException e) {
                LOGGER.error("Error while transmitting supports", e);
                return Response.serverError().build();
            }
        }

        return Response.ok().build();
    }

    @Override
    public Response transmitSupport(Support support) {
        LOGGER.info("Received one support.");

        return transmitSupport(new TransmittedSupports(Collections.singletonList(support)));
    }
}
