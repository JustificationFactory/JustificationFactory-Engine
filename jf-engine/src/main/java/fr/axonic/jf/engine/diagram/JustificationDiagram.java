package fr.axonic.jf.engine.diagram;

import fr.axonic.jf.engine.AlignedDiagrams;
import fr.axonic.jf.engine.kernel.ComparisonType;
import fr.axonic.jf.engine.kernel.JustificationDiagramAPI;
import fr.axonic.jf.engine.pattern.JustificationStep;

import javax.ws.rs.NotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JustificationDiagram implements JustificationDiagramAPI<JustificationStep> {

    private List<JustificationStep> steps;

    public JustificationDiagram(){
        steps=new ArrayList<>();
    }

    public JustificationDiagram(List<JustificationStep> steps) {
        this.steps = steps;
    }

    @Override
    public JustificationDiagramAPI derivatedFrom() {
        return null;
    }

    @Override
    public void addStep(JustificationStep step) {
        steps.add(step);
    }

    @Override
    public List<JustificationStep> getSteps() {
        return steps;
    }

    private void setSteps(List<JustificationStep> steps) {
        this.steps = steps;
    }

    @Override
    public JustificationDiagramAPI copy() {
        try {
            return (JustificationDiagramAPI) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<ComparisonType, List<JustificationStep>> compare(JustificationDiagramAPI justificationDiagramAPI) {
        throw new NotSupportedException("Not yet implemented");
    }

    @Override
    public AlignedDiagrams align(Map<ComparisonType, List<JustificationStep>> stepsToKeep) {
        throw new NotSupportedException("Not yet implemented");
    }

    @Override
    public List<JustificationDiagramAPI> conformsTo() {
        throw new NotSupportedException("Not yet implemented");
    }
}
