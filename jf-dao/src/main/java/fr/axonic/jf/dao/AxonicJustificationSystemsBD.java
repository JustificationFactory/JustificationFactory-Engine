package fr.axonic.jf.dao;

import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.instance.JustificationSystemEnum;
import fr.axonic.jf.instance.JustificationSystemFactory;
import fr.axonic.validation.exception.VerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * A simple DAO which load the justification systems of AXONIC.
 */
public class AxonicJustificationSystemsBD {

    private static final Logger LOGGER = LoggerFactory.getLogger(AxonicJustificationSystemsBD.class);

    private static boolean initialized=false;



    public static void initializeJustificationsSystems(JustificationSystemsDAO dao) {
        if (!initialized) {
            for (JustificationSystemEnum jsType : JustificationSystemEnum.values()) {
                try {
                    if(dao.getJustificationSystem(jsType.name())==null){
                        dao.saveJustificationSystem(jsType.name(),JustificationSystemFactory.create(jsType));
                    }
                } catch (IOException | VerificationException | WrongEvidenceException e) {
                    LOGGER.error(e.toString());
                }
            }
            initialized = true;
        }
    }


}
