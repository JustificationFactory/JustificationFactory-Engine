package fr.axonic.jf.databases;

import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.engine.JustificationSystem;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleJustificationSystemsBD implements JustificationSystemsBD {

    @Inject
    private JustificationSystemsDAO dao;

    @Override
    public Map<String, JustificationSystem> getJustificationSystems() {
        try {
            return dao.loadJustificationSystems();
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}
