package fr.axonic.jf.instance;

import fr.axonic.jf.engine.*;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.instance.ValidXp.ValidXpSystem;
import fr.axonic.jf.instance.clinical.AVEKJustificationSystem;
import fr.axonic.jf.instance.jenkins.JenkinsJustificationSystem;
import fr.axonic.jf.instance.redmine.RedmineJustificationSystem;
import fr.axonic.jf.instance.reproducibleXP.ReproducibleXPJustificationSystem;
import fr.axonic.jf.instance.school.SchoolJustificationSystem;
import fr.axonic.validation.exception.VerificationException;

/**
 * Created by cduffau on 18/01/17.
 */
public class JustificationSystemFactory {

    public static JustificationSystem create(JustificationSystemEnum justificationSystem) throws VerificationException, WrongEvidenceException {
        switch (justificationSystem) {
            case JENKINS:
                return new JenkinsJustificationSystem();
            case REDMINE:
                return new RedmineJustificationSystem();
            case CLINICAL_STUDIES:
                return new AVEKJustificationSystem();
            case SCHOOL:
                return new SchoolJustificationSystem();
            case VALID_XP_SYSTEM:
                return new ValidXpSystem();
            case REPRODUCIBLE_XP:
                return new ReproducibleXPJustificationSystem();
            default:
                return null;
        }
    }
}
