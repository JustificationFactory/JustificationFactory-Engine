package fr.axonic.jf.databases;

import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.instance.JustificationSystemEnum;
import fr.axonic.jf.instance.JustificationSystemFactory;
import fr.axonic.validation.exception.VerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple DAO which load the justification systems of AXONIC.
 */
public class AxonicJustificationSystemsBD implements JustificationSystemsBD {

    private static final Logger LOGGER = LoggerFactory.getLogger(AxonicJustificationSystemsBD.class);

    @Inject
    private JustificationSystemsDAO dao;
    private boolean initialized;

    public AxonicJustificationSystemsBD() {
        initialized = false;
    }

    public Map<String, JustificationSystem> getJustificationSystems() {
        if (!initialized) {
            initializeJustificationsSystems();

            initialized = true;
        }

        try {
            return dao.loadJustificationSystems();
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    private void initializeJustificationsSystems() {
        if (dao == null) {
            return;
        }

        Map<String, JustificationSystem> knownSystems;
        try {
            knownSystems = dao.loadJustificationSystems();
        } catch (IOException e) {
            knownSystems = new HashMap<>();
        }

        for (JustificationSystemEnum jsType : JustificationSystemEnum.values()) {
            if (!knownSystems.containsKey(jsType.name())) {
                try {
                    dao.saveJustificationSystem(jsType.name(), JustificationSystemFactory.create(jsType));
                    LOGGER.info("{} Justification System added", jsType.name());
                } catch (WrongEvidenceException | VerificationException | IOException e) {
                    // TODO
                    e.printStackTrace();
                }
            }
        }
    }
}
