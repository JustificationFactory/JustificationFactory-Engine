package fr.axonic.jf.engine.pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.diagram.JustificationPatternDiagram;
import fr.axonic.jf.engine.pattern.type.OutputType;
import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.diagram.JustificationPatternDiagram;

import java.util.List;

@JsonIgnoreProperties(value = {"objective"})
public class DiagramPatternsBase extends PatternsBase {

    private JustificationPatternDiagram jpd;

    public DiagramPatternsBase(JustificationPatternDiagram jpd) {
        super(PatternsBaseType.PATTERN_DIAGRAM);
        this.jpd = jpd;
    }

    public DiagramPatternsBase() {
        super(PatternsBaseType.PATTERN_DIAGRAM);
    }

    @Override
    public List<Pattern> getPatterns() {
        return jpd.getSteps();
    }

    @Override
    public void setPatterns(List<Pattern> patterns) {
        jpd = new JustificationPatternDiagram(patterns);
    }

    @Override
    public Pattern getPattern(String patternId) {
        return jpd.getSteps().stream().filter(pattern -> pattern.getId().equals(patternId)).collect(JustificationSystem.singletonCollector());
    }

    @Override
    public OutputType getObjective() {
        return jpd.getSteps().get(jpd.getSteps().size() - 1).getOutputType();
    }
}
