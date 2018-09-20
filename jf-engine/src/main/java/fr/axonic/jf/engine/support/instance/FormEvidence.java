package fr.axonic.jf.engine.support.instance;

import fr.axonic.jf.engine.support.evidence.Evidence;
import fr.axonic.jf.engine.support.evidence.Form;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FormEvidence extends Evidence<Form> {
    public FormEvidence(String name, Form element) {
        super(name, element);
    }

    public FormEvidence() {
    }
}
