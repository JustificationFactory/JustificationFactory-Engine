package fr.axonic.avek.configuration;

import fr.axonic.avek.dao.JustificationSystemsDAO;
import fr.axonic.avek.dao.implementations.MongoJustificationSystemsDAO;
import fr.axonic.avek.databases.JustificationSystemsBD;
import fr.axonic.avek.databases.SimpleJustificationSystemsBD;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class JustificationWSBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(MongoJustificationSystemsDAO.class).to(JustificationSystemsDAO.class);
        bind(SimpleJustificationSystemsBD.class).to(JustificationSystemsBD.class);
    }
}
