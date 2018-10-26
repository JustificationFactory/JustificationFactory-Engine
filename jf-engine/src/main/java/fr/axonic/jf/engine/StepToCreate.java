package fr.axonic.jf.engine;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by cduffau on 22/08/17.
 */
@XmlRootElement
public class StepToCreate {

    private List<Support> supports;
    private Conclusion conclusion;

    public StepToCreate() {
    }

    public StepToCreate(List<Support> supports, Conclusion conclusion) {
        this.supports = supports;
        this.conclusion = conclusion;
    }

    @XmlElementWrapper
    @XmlElement
    public List<Support> getSupports() {
        return supports;
    }

    public void setSupports(List<Support> supports) {
        this.supports = supports;
    }

    @XmlElement
    public Conclusion getConclusion() {
        return conclusion;
    }

    public void setConclusion(Conclusion conclusion) {
        this.conclusion = conclusion;
    }

}
