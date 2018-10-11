package fr.axonic.jf.instance.school;

import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.diagram.JustificationPatternDiagram;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.pattern.DiagramPatternsBase;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.type.InputType;
import fr.axonic.jf.engine.pattern.type.OutputType;
import fr.axonic.jf.engine.pattern.type.Type;
import fr.axonic.jf.engine.strategy.Strategy;
import fr.axonic.jf.instance.school.conclusions.SchoolSuccessConclusion;
import fr.axonic.jf.instance.school.conclusions.TakeTheNecessaryToSucceedSubConclusion;
import fr.axonic.jf.instance.school.evidences.ContinuousIntegrationSystemEvidence;
import fr.axonic.jf.instance.school.evidences.ProjectGradeEvidence;
import fr.axonic.jf.instance.school.evidences.ReadResearchArticlesEvidence;
import fr.axonic.jf.instance.school.strategies.GettingGoodResultsStrategy;
import fr.axonic.jf.instance.school.strategies.WorkHardStrategy;
import fr.axonic.validation.exception.VerificationException;

import java.util.Arrays;

public class SchoolJustificationSystem extends JustificationSystem<DiagramPatternsBase> {

    public SchoolJustificationSystem() throws VerificationException, WrongEvidenceException {
        super(createPatternsBase());
    }

    private static DiagramPatternsBase createPatternsBase() {
        return new DiagramPatternsBase(createJPD());
    }

    private static JustificationPatternDiagram createJPD() {
        JustificationPatternDiagram jpd = new JustificationPatternDiagram();

        InputType<ReadResearchArticlesEvidence> readResearchArticle =
                new InputType<>(ReadResearchArticlesEvidence.EVIDENCE_NAME, new Type<>(ReadResearchArticlesEvidence.class, "HAVE_READ_RESEARCH_ARTICLES"));
        InputType<ContinuousIntegrationSystemEvidence> continuousIntegrationSystem =
                new InputType<>(ContinuousIntegrationSystemEvidence.EVIDENCE_NAME, new Type<>(ContinuousIntegrationSystemEvidence.class, "HAVE_A_CONTINUOUS_INTEGRATION_SYSTEM"));
        OutputType<TakeTheNecessaryToSucceedSubConclusion> takeTheNecessaryToSucceed =
                new OutputType<>("TAKE THE_NECESSARY TO SUCCEED", new Type<>(TakeTheNecessaryToSucceedSubConclusion.class, "TAKE_THE_NECESSARY_TO_SUCCEED"));
        Strategy workHard = new WorkHardStrategy("WORK HARD");
        Pattern takeTheNecessaryPattern = new Pattern("TAKE_THE_NECESSARY_TO_SUCCEED_STUDIES", "Take the necessary to succeed studies", workHard,
                Arrays.asList(readResearchArticle, continuousIntegrationSystem), takeTheNecessaryToSucceed);

        InputType<ProjectGradeEvidence> projectGrade =
                new InputType<>(ProjectGradeEvidence.EVIDENCE_NAME, new Type<>(ProjectGradeEvidence.class, "HAVE_GOT_A_GOOD_PROJECT_GRADE"));
        OutputType<SchoolSuccessConclusion> schoolSuccess = new OutputType<>("SUCCEEDED STUDIES", new Type<>(SchoolSuccessConclusion.class, "SUCCEEDED_STUDIES"));
        Strategy gettingGoodResults = new GettingGoodResultsStrategy("GETTING GOOD RESULTS");
        Pattern schoolSuccessPattern = new Pattern("SCHOOL_SUCCESS", "School success", gettingGoodResults,
                Arrays.asList(takeTheNecessaryToSucceed.transformToInput(), projectGrade), schoolSuccess);

        jpd.addStep(takeTheNecessaryPattern);
        jpd.addStep(schoolSuccessPattern);

        return jpd;
    }
}
