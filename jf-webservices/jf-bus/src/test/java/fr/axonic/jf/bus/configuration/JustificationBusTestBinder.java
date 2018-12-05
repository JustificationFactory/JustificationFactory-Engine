package fr.axonic.jf.bus.configuration;

import fr.axonic.jf.bus.business.StepBuilder;
import fr.axonic.jf.bus.business.supports.KnownSupportsDAO;
import fr.axonic.jf.bus.business.supports.ListKnownSupportsDAO;
import fr.axonic.jf.bus.business.supports.MongoKnownSupportsDAO;
import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.dao.implementations.MongoJustificationSystemsDAO;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class JustificationBusTestBinder extends AbstractBinder {

    private JustificationSystemsDAO justificationSystemsDAO;
    private KnownSupportsDAO knownSupportsDAO;

    public JustificationBusTestBinder() {
        justificationSystemsDAO = new MongoJustificationSystemsDAO("mongodb://localhost:27018", "jf", "justificationSystems");
        knownSupportsDAO = new MongoKnownSupportsDAO("mongodb://localhost:27018", "jf", "knownSupports");
    }

    @Override
    protected void configure() {
        bind(justificationSystemsDAO).to(JustificationSystemsDAO.class);
        bind(StepBuilder.class).to(StepBuilder.class);
        bind(new ListKnownSupportsDAO()).to(KnownSupportsDAO.class);
    }

    public JustificationSystemsDAO getJustificationSystemsDAO() {
        return justificationSystemsDAO;
    }
}
