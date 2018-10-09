package fr.axonic.jf.dao;

import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.JustificationSystemAPI;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Resource
public interface JustificationSystemsDAO {

    Map<String, JustificationSystem> getJustificationSystems() throws IOException;
    JustificationSystem getJustificationSystem(String name) throws IOException;
    void saveJustificationSystem(String name, JustificationSystemAPI argumentationSystem) throws IOException;
    void removeJustificationSystem(String name) throws IOException;
}
