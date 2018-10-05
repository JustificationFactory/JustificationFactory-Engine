package fr.axonic.jf.dao.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.axonic.jf.dao.JerseyMapperProvider;
import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.JustificationSystemAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleJustificationSystemsDAO implements JustificationSystemsDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleJustificationSystemsDAO.class);
    private static final String DIR = "data";

    private String destinationFilePattern;

    public SimpleJustificationSystemsDAO() {
        this(DIR + "/%s.data");
    }

    public SimpleJustificationSystemsDAO(String destinationFilePattern) {
        this.destinationFilePattern = destinationFilePattern;
    }

    public Map<String, JustificationSystem> loadJustificationSystems() throws IOException {
        Map<String, JustificationSystem> res = new HashMap<>();
        ObjectMapper mapper = new JerseyMapperProvider().getContext(null);

        File dir = new File(DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getName().split("\\.")[0];
                LOGGER.info("Found Justification System {}", name);
                res.put(name, mapper.readValue(file, JustificationSystem.class));
            }
        }

        return res;
    }

    public void saveJustificationSystem(String name, JustificationSystemAPI argumentationSystem) throws IOException {
        File file = new File(String.format(destinationFilePattern, name));
        if (file.createNewFile()) {
            ObjectMapper mapper = new JerseyMapperProvider().getContext(null);
            mapper.writeValue(file, argumentationSystem);
        }
    }

    public void removeJustificationSystem(String name) {
        File file = new File(String.format(destinationFilePattern, name));
        if (file.exists()) {
            file.delete();
        }
    }
}
