package fr.axonic.avek.dao;

import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.JustificationSystemAPI;

import java.io.IOException;
import java.util.Map;

public class MongoJustificationSystemsDAO implements JustificationSystemsDAO {

    @Override
    public Map<String, JustificationSystem> loadJustificationSystems() throws IOException {
        return null; // TODO
    }

    @Override
    public void saveJustificationSystem(String name, JustificationSystemAPI argumentationSystem) throws IOException {
        // TODO
    }

    @Override
    public void removeJustificationSystem(String name) throws IOException {
        // TODO
    }
}
