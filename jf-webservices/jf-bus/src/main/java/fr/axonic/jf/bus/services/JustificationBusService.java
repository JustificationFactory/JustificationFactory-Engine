package fr.axonic.jf.bus.services;

import fr.axonic.jf.engine.support.Support;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface JustificationBusService {

    @POST
    @Path("/supports")
    Response transmitSupport(TransmittedSupports supports);

    @POST
    @Path("/support")
    Response transmitSupport(Support support);
}
