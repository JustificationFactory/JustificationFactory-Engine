package fr.axonic.jf.engine.exception;

/**
 * Created by cduffau on 24/06/16.
 */
public class StepBuildingException extends Exception {
    public StepBuildingException(String s) {
        super(s);
    }

    public StepBuildingException(String message, Throwable cause) {
        super(message, cause);
    }
}
