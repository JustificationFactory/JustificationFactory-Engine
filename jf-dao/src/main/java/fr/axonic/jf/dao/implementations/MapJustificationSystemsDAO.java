package fr.axonic.jf.dao.implementations;

import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.JustificationSystemAPI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapJustificationSystemsDAO implements JustificationSystemsDAO {

    private Map<String, JustificationSystem> justificationSystems;

    public MapJustificationSystemsDAO() {
        justificationSystems = new HashMap<>();
    }

    @Override
    public Map<String, JustificationSystem> getJustificationSystems() {
        return justificationSystems;
    }

    @Override
    public JustificationSystem getJustificationSystem(String name) throws IOException {
        return justificationSystems.get(name);
    }

    @Override
    public void saveJustificationSystem(String name, JustificationSystemAPI argumentationSystem) {
        justificationSystems.put(name, (JustificationSystem) argumentationSystem);
    }

    @Override
    public void removeJustificationSystem(String name) {
        justificationSystems.remove(name);
    }
}
