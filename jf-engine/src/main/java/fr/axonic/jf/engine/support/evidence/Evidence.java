package fr.axonic.jf.engine.support.evidence;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.Support;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Evidence<T extends Element> extends Support<T> implements Cloneable{
    public Evidence(String name, T element) {
        super(name, element);
    }

    public Evidence() {
    }
}
