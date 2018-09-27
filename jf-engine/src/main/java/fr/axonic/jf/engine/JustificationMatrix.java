package fr.axonic.jf.engine;

import fr.axonic.jf.engine.pattern.JustificationStep;
import fr.axonic.jf.engine.pattern.Pattern;
import javafx.util.Pair;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class JustificationMatrix {

    private List<Pair<Pattern,JustificationStep>> content;

    @XmlElement
    public List<Pair<Pattern, JustificationStep>> getContent() {
        return content;
    }

    public void setContent(List<Pair<Pattern, JustificationStep>> content) {
        this.content = content;
    }
}
