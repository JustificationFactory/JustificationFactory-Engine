package fr.axonic.jf.dao.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.axonic.jf.dao.AxonicJustificationSystemsBD;
import fr.axonic.jf.dao.JerseyMapperProvider;
import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.JustificationSystemAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileJustificationSystemsDAO implements JustificationSystemsDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileJustificationSystemsDAO.class);
    private static final String DIR = "data";

    private String destinationFilePattern;

    public FileJustificationSystemsDAO() {
        this(DIR + "/%s.data");
    }

    public FileJustificationSystemsDAO(String destinationFilePattern) {
        this.destinationFilePattern = destinationFilePattern;
        AxonicJustificationSystemsBD.initializeJustificationsSystems(this);
    }

    public Map<String, JustificationSystem> getJustificationSystems() throws IOException {
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

    @Override
    public JustificationSystem getJustificationSystem(String name) throws IOException {
        ObjectMapper mapper = new JerseyMapperProvider().getContext(null);
        try{
            File file = new File(DIR+"/"+name+".data");
            return mapper.readValue(file, JustificationSystem.class);
        }
        catch (FileNotFoundException e){
            return null;
        }
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
