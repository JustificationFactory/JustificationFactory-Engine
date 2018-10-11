package fr.axonic.jf.engine;

import fr.axonic.jf.engine.kernel.JustificationDiagramAPI;

import java.util.Objects;

public class AlignedDiagrams {

    private JustificationDiagramAPI first;
    private JustificationDiagramAPI second;

    public AlignedDiagrams(JustificationDiagramAPI first, JustificationDiagramAPI second) {
        this.first = first;
        this.second = second;
    }

    public AlignedDiagrams() {
        // For marshalling purpose.
    }

    public JustificationDiagramAPI getFirst() {
        return first;
    }

    public void setFirst(JustificationDiagramAPI first) {
        this.first = first;
    }

    public JustificationDiagramAPI getSecond() {
        return second;
    }

    public void setSecond(JustificationDiagramAPI second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlignedDiagrams that = (AlignedDiagrams) o;
        return Objects.equals(first, that.first) &&
                Objects.equals(second, that.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
