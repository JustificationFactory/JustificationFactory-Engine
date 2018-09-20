package fr.axonic.jf.instance.clinical.evidence;

import fr.axonic.jf.engine.support.evidence.Evidence;

/**
 * Created by cduffau on 16/03/17.
 */
public class StimulationEvidence extends Evidence<Stimulation>{

    public StimulationEvidence(String name, Stimulation stimulation) {
        super(name,stimulation);
    }

    public StimulationEvidence() {
        super();
    }
}
