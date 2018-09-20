package fr.axonic.jf.bus.configuration;

import fr.axonic.jf.bus.services.StepBuilder;
import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.dao.implementations.MongoJustificationSystemsDAO;
import fr.axonic.jf.bus.services.StepBuilder;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class JustificationBusBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(MongoJustificationSystemsDAO.class).to(JustificationSystemsDAO.class);
        bind(StepBuilder.class).to(StepBuilder.class);
    }
}
