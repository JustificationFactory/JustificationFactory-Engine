package fr.axonic.jf.instance.jenkins.conclusion;

/**
 * Created by cduffau on 25/08/17.
 */
public class TDDJenkinsConclusion extends JenkinsConclusion{
    public TDDJenkinsConclusion() {
        this(null);
    }

    public TDDJenkinsConclusion(JenkinsStatus jenkinsStatus) {
        super("TDD-jenkins", jenkinsStatus);
    }
}
