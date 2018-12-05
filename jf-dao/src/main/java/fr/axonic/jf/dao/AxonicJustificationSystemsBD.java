package fr.axonic.jf.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple DAO which load the justification systems of AXONIC.
 */
public class AxonicJustificationSystemsBD {

    private static final Logger LOGGER = LoggerFactory.getLogger(AxonicJustificationSystemsBD.class);
    private static boolean initialized = false;

    public static void initializeJustificationsSystems(JustificationSystemsDAO dao) {
        if (!initialized) {
            /*for (JustificationSystemEnum jsType : JustificationSystemEnum.values()) {
                try {
                    if(dao.getJustificationSystem(jsType.name())==null){
                        dao.saveJustificationSystem(jsType.name(),JustificationSystemFactory.create(jsType));
                    }
                } catch (IOException | VerificationException | WrongEvidenceException e) {
                    LOGGER.error(e.toString());
                }
            }*/
            initialized = true;
        }
    }


}
