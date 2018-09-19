package fr.axonic.avek.dao;

import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.JustificationSystemAPI;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Resource
public interface JustificationSystemsDAO {

    Map<String, JustificationSystem> loadJustificationSystems() throws IOException;
    void saveJustificationSystem(String name, JustificationSystemAPI argumentationSystem) throws IOException;
    void removeJustificationSystem(String name) throws IOException;
}
