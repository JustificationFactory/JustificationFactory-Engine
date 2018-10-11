package fr.axonic.jf.engine;

import fr.axonic.jf.engine.pattern.JustificationStep;
import fr.axonic.jf.engine.pattern.Pattern;

import java.util.Objects;

public class InstantiatedStep {

    private Pattern associatedPattern;
    private JustificationStep step;

    public InstantiatedStep(Pattern associatedPattern, JustificationStep step) {
        this.associatedPattern = associatedPattern;
        this.step = step;
    }

    public InstantiatedStep() {
        // For marshalling purpose.
    }

    public Pattern getAssociatedPattern() {
        return associatedPattern;
    }

    public JustificationStep getStep() {
        return step;
    }

    public void setAssociatedPattern(Pattern associatedPattern) {
        this.associatedPattern = associatedPattern;
    }

    public void setStep(JustificationStep step) {
        this.step = step;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstantiatedStep that = (InstantiatedStep) o;
        return Objects.equals(associatedPattern, that.associatedPattern) &&
                Objects.equals(step, that.step);
    }

    @Override
    public int hashCode() {
        return Objects.hash(associatedPattern, step);
    }
}
