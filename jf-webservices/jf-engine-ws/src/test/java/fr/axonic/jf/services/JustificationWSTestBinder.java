package fr.axonic.jf.services;

import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.dao.implementations.FileJustificationSystemsDAO;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class JustificationWSTestBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(FileJustificationSystemsDAO.class).to(JustificationSystemsDAO.class);
    }
}
