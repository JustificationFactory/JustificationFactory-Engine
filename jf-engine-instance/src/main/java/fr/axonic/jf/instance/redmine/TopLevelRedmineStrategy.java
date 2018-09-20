package fr.axonic.jf.instance.redmine;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;

import java.util.List;

public class TopLevelRedmineStrategy extends RedmineStrategy {

    private String outputName;

    public TopLevelRedmineStrategy(String name, String outputName) {
        super(name);

        this.outputName = outputName;
    }

    public TopLevelRedmineStrategy() {

    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        RedmineConclusion conclusion = new RedmineConclusion();

        conclusion.setName(outputName);

        return conclusion;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }
}
