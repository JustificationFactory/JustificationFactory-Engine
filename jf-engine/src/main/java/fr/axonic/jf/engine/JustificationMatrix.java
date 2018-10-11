package fr.axonic.jf.engine;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class JustificationMatrix {

    private List<InstantiatedStep> content;

    @XmlElement
    public List<InstantiatedStep> getContent() {
        return content;
    }

    public void setContent(List<InstantiatedStep> content) {
        this.content = content;
    }
}
