package fr.axonic.jf.instance.evidence;

import fr.axonic.jf.engine.support.evidence.Evidence;

/**
 * Created by cduffau on 17/03/17.
 */
public class ResultsEvidence extends Evidence<Result>{
    public ResultsEvidence(String name, Result element) {
        super(name, element);
    }

    public ResultsEvidence() {
        super();
    }
}
