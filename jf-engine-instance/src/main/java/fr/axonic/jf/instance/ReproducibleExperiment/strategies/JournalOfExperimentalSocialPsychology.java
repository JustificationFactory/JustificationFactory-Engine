package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import com.fasterxml.jackson.annotation.*;
import fr.axonic.jf.engine.strategy.Project;
import fr.axonic.jf.engine.strategy.Rationale;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement
public class JournalOfExperimentalSocialPsychology implements Project {

    private Methodology methodology;
    private Target target;

    private JournalOfExperimentalSocialPsychology() {
    }

    public JournalOfExperimentalSocialPsychology(Methodology method, Target t) {
        this.methodology = method;
        this.target = t;
    }


    @XmlElement
    public Methodology getMethodology() {
        return methodology;
    }

    @XmlElement
    public Target getTarget() {
        return target;
    }

    public void setMethodology(Methodology methodology) {
        this.methodology = methodology;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JournalOfExperimentalSocialPsychology)) return false;

        JournalOfExperimentalSocialPsychology that = (JournalOfExperimentalSocialPsychology) o;

        if (methodology != that.methodology) return false;
        return target == that.target;
    }

    @Override
    public int hashCode() {
        int result = methodology != null ? methodology.hashCode() : 0;
        result = 31 * result + (target != null ? target.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JournalOfExperimentalSocialPsychology{" +
                "methodology=" + methodology +
                ", target=" + target +
                '}';
    }

    @Override
    public String name() {
        return this.methodology+ " to detect "+this.target;
    }
}
