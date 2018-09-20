package fr.axonic.jf.instance.redmine;

import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.instance.JustificationSystemEnum;
import fr.axonic.jf.instance.JustificationSystemFactory;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RedmineJustificationSystemTest {

    @Test
    public void shouldReturnST0001Pattern() throws VerificationException, WrongEvidenceException {
        List<Support> givenSupports = Arrays.asList(
                new RedmineDocumentEvidence("SWAM_ST_0001", new RedmineDocument("http://oneurl.com/SWAM_ST_0001")),
                new RedmineDocumentApproval("SWAM_ST_0001_APPROVAL", new RedmineDocument("http://oneurl.com/SWAM_ST_0001_APPROVAL"))
        );

        JustificationSystem jpd = JustificationSystemFactory.create(JustificationSystemEnum.REDMINE);

        assertNotNull(jpd);

        List<String> possiblePatterns = jpd.getPatternsBase().getPossiblePatterns(givenSupports);

        assertEquals(1, possiblePatterns.size());
        assertEquals("SWAM_ST_0001_VALIDATION", possiblePatterns.get(0));
    }
}