package fr.axonic.jf.engine.support.instance;

import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Form;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FormConclusion extends Conclusion<Form> {
    public FormConclusion(String name, Form element) {
        super(name, element);
    }

    public FormConclusion() {
    }

    public FormConclusion(Form element) {
        super(element);
    }
}
