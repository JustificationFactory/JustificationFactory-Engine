package fr.axonic.jf.bus.configuration;

import fr.axonic.jf.bus.business.StepBuilder;
import fr.axonic.jf.bus.business.supports.KnownSupportsDAO;
import fr.axonic.jf.bus.business.supports.ListKnownSupportsDAO;
import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.dao.implementations.MongoJustificationSystemsDAO;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class JustificationBusBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(MongoJustificationSystemsDAO.class).to(JustificationSystemsDAO.class);
        bind(ListKnownSupportsDAO.class).to(KnownSupportsDAO.class);
        bind(StepBuilder.class).to(StepBuilder.class);
    }
}
