package fr.axonic.avek.services;

import fr.axonic.avek.dao.JustificationSystemsDAO;
import fr.axonic.avek.dao.implementations.SimpleJustificationSystemsDAO;
import fr.axonic.avek.databases.AxonicJustificationSystemsBD;
import fr.axonic.avek.databases.JustificationSystemsBD;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class JustificationWSTestBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(SimpleJustificationSystemsDAO.class).to(JustificationSystemsDAO.class);
        bind(AxonicJustificationSystemsBD.class).to(JustificationSystemsBD.class);
    }
}
