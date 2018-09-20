package fr.axonic.jf.instance.clinical.evidence;

import fr.axonic.jf.engine.support.evidence.Evidence;

/**
 * Created by cduffau on 16/03/17.
 */
public class SubjectEvidence extends Evidence<Subject>{
    public SubjectEvidence(String name, Subject element) {
        super(name, element);
    }

    public SubjectEvidence() {
        super();
    }
}
