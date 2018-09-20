package fr.axonic.jf.services;

import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.dao.implementations.SimpleJustificationSystemsDAO;
import fr.axonic.jf.databases.AxonicJustificationSystemsBD;
import fr.axonic.jf.databases.JustificationSystemsBD;
import fr.axonic.jf.databases.AxonicJustificationSystemsBD;
import fr.axonic.jf.databases.JustificationSystemsBD;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class JustificationWSTestBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(SimpleJustificationSystemsDAO.class).to(JustificationSystemsDAO.class);
        bind(AxonicJustificationSystemsBD.class).to(JustificationSystemsBD.class);
    }
}
