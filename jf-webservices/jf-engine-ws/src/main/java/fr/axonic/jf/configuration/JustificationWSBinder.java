package fr.axonic.jf.configuration;

import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.dao.implementations.MongoJustificationSystemsDAO;
import fr.axonic.jf.databases.JustificationSystemsBD;
import fr.axonic.jf.databases.SimpleJustificationSystemsBD;
import fr.axonic.jf.databases.JustificationSystemsBD;
import fr.axonic.jf.databases.SimpleJustificationSystemsBD;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class JustificationWSBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(MongoJustificationSystemsDAO.class).to(JustificationSystemsDAO.class);
        bind(SimpleJustificationSystemsBD.class).to(JustificationSystemsBD.class);
    }
}
