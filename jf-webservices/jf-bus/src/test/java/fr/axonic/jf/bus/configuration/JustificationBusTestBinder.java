package fr.axonic.jf.bus.configuration;

import fr.axonic.jf.bus.services.StepBuilder;
import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.dao.implementations.MongoJustificationSystemsDAO;
import fr.axonic.jf.dao.implementations.SimpleJustificationSystemsDAO;
import fr.axonic.jf.bus.services.StepBuilder;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class JustificationBusTestBinder extends AbstractBinder {

    private JustificationSystemsDAO dao;

    public JustificationBusTestBinder() {
        dao = new MongoJustificationSystemsDAO();
    }

    @Override
    protected void configure() {
        bind(dao).to(JustificationSystemsDAO.class);
        bind(StepBuilder.class).to(StepBuilder.class);
    }

    public JustificationSystemsDAO getDao() {
        return dao;
    }
}
