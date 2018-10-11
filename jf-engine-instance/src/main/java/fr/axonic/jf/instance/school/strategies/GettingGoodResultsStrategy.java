package fr.axonic.jf.instance.school.strategies;

import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.instance.school.conclusions.SchoolSuccessConclusion;
import fr.axonic.jf.instance.school.evidences.ProjectGradeEvidence;

import java.util.List;

public class GettingGoodResultsStrategy extends SchoolStrategy {

    public GettingGoodResultsStrategy(String name) {
        super(name);
    }

    public GettingGoodResultsStrategy() {
        super(null);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        int grade = supportList.stream().filter(s -> s instanceof ProjectGradeEvidence)
                .map(s -> (ProjectGradeEvidence) s)
                .findFirst()
                .map(e -> e.getElement().getGrade())
                .orElse(0);

        if (grade < 10) {
            return null;
        }

        return new SchoolSuccessConclusion();
    }
}
