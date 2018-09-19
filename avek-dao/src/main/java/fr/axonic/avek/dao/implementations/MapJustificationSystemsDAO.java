package fr.axonic.avek.dao.implementations;

import fr.axonic.avek.dao.JustificationSystemsDAO;
import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.JustificationSystemAPI;

import java.util.HashMap;
import java.util.Map;

public class MapJustificationSystemsDAO implements JustificationSystemsDAO {

    private Map<String, JustificationSystem> justificationSystems;

    public MapJustificationSystemsDAO() {
        justificationSystems = new HashMap<>();
    }

    @Override
    public Map<String, JustificationSystem> loadJustificationSystems() {
        return justificationSystems;
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
