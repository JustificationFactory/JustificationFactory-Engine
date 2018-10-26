package fr.axonic.jf.bus.configuration;

import fr.axonic.jf.bus.business.StepBuilder;
import fr.axonic.jf.bus.business.supports.KnownSupportsDAO;
import fr.axonic.jf.bus.business.supports.ListKnownSupportsDAO;
import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.dao.implementations.MongoJustificationSystemsDAO;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class JustificationBusTestBinder extends AbstractBinder {

    private JustificationSystemsDAO dao;

    public JustificationBusTestBinder() {
        dao = new MongoJustificationSystemsDAO("mongodb://localhost:27018","jf","justificationSystems");
    }

    @Override
    protected void configure() {
        bind(dao).to(JustificationSystemsDAO.class);
        bind(StepBuilder.class).to(StepBuilder.class);
        bind(ListKnownSupportsDAO.class).to(KnownSupportsDAO.class);
    }

    public JustificationSystemsDAO getDao() {
        return dao;
    }
}
