package fr.axonic.avek.bus.configuration;

import fr.axonic.avek.bus.services.StepBuilder;
import fr.axonic.avek.dao.JustificationSystemsDAO;
import fr.axonic.avek.dao.implementations.SimpleJustificationSystemsDAO;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class JustificationBusBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(SimpleJustificationSystemsDAO.class).to(JustificationSystemsDAO.class);
        bind(StepBuilder.class).to(StepBuilder.class);
    }
}
