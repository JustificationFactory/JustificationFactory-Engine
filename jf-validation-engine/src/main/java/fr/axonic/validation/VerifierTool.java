package fr.axonic.validation;


import fr.axonic.validation.Verifiable;
import fr.axonic.validation.Verifier;
import fr.axonic.validation.exception.VerifiableError;
import fr.axonic.validation.exception.VerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lbrouchet on 24/07/2015.
 */
public class VerifierTool {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerifierTool.class);

    /**
     * This method is used to do the fr.axonic.validation of a Verifiable Object with a specific Verifier. The fr.axonic.validation
     * can be made only on the current Verifiable or deeply, validating the Verifiable child.
     *
     * @param verifiable The Object which will be verified
     * @param verifier The Verifier which will be used to verify the Verifiable
     * @param verifyConsistency true to verify deeply, false to only validate the Object given as parameter
     * @return VerifiableError which may contain the errors which occurred during the validation.
     */
    public static VerifiableError doVerifyWithSpecifiedVerifier(Verifiable verifiable, Verifier verifier,
                                                                boolean verifyConsistency) {
        LOGGER.debug("start doVerifyWithSpecifiedVerifier(..)");
        VerifiableError verifError = new VerifiableError();
        try {
            if (verifyConsistency) {
                verifier.verify(verifiable);
            } else {
                verifier.verifyUnit(verifiable);
            }
        } catch (VerificationException e) {
            verifError.addErrors(e.getErrors());
        }
        LOGGER.debug("end doVerifyWithSpecifiedVerifier(..)");
        return verifError;
    }

}
