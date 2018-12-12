package fr.axonic.jf.instance.ReproducibleExperiment;

import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.diagram.JustificationPatternDiagram;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.pattern.DiagramPatternsBase;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.type.InputType;
import fr.axonic.jf.engine.pattern.type.OutputType;
import fr.axonic.jf.engine.pattern.type.Type;
import fr.axonic.jf.engine.strategy.Strategy;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.AccuracyMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.ReproducibleExperimentConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.ReproducibleMetricsConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.conclusion.TotalTimeMetricConclusion;
import fr.axonic.jf.instance.ReproducibleExperiment.evidences.*;
import fr.axonic.jf.instance.ReproducibleExperiment.strategies.AccuracyMetricStrategy;
import fr.axonic.jf.instance.ReproducibleExperiment.strategies.ReproducibleExperimentFinalStrategy;
import fr.axonic.jf.instance.ReproducibleExperiment.strategies.ReproducibleMetricsStrategy;
import fr.axonic.jf.instance.ReproducibleExperiment.strategies.TotalTimeMetricStrategy;
import fr.axonic.jf.instance.ValidXp.conclusion.ValidXpConclusion;
import fr.axonic.jf.instance.ValidXp.evidence.LogEvidence;
import fr.axonic.jf.instance.ValidXp.evidence.XpEvidence;
import fr.axonic.jf.instance.ValidXp.strategies.ValidXpStrategy1;
import fr.axonic.validation.exception.VerificationException;

import java.util.Arrays;

public class ReproducibleExperimentSystem extends JustificationSystem<DiagramPatternsBase> {


    public ReproducibleExperimentSystem()throws VerificationException, WrongEvidenceException {
        super(createPatternsBase());
    }

    private static DiagramPatternsBase createPatternsBase() {
        return new DiagramPatternsBase(createJPD());
    }

    private static JustificationPatternDiagram createJPD() {
        JustificationPatternDiagram jpd = new JustificationPatternDiagram();


        //Accuracy metric pattern
        InputType<AccuracyMetricEvidence> Accuracy_Evidence = new InputType <>(AccuracyMetricEvidence.EVIDENCE_NAME, new Type<>(AccuracyMetricEvidence.class, "ACCURACY_METRIC_EVIDENCE"));
        InputType<ExperimentEvidence> Experiment_Evidence = new InputType <>(ExperimentEvidence.EVIDENCE_NAME, new Type<>(ExperimentEvidence.class,"EXPERIMENT_EVIDENCE"));
        InputType<AccuracyReproducibilityThreshold> Accuracy_Threshold_Evidence = new InputType <>(AccuracyReproducibilityThreshold.EVIDENCE_NAME, new Type<>(AccuracyReproducibilityThreshold.class,"ACCURACY_THRESHOLD_EVIDENCE"));
        OutputType<AccuracyMetricConclusion> Accuracy_Conclusion = new OutputType<>("ACCURACY_METRIC_CONCLUSION", new Type<>(AccuracyMetricConclusion.class, "ACCURACY_METRIC_CONCLUSION"));

        Strategy Accuracy_Strategy = new AccuracyMetricStrategy("ACCURACY_METRIC_STRATEGY");
        Pattern Accuracy_Pattern = new Pattern("ACCURACY_METRIC_PATTERN", "ACCURACY_METRIC_PATTERN", Accuracy_Strategy, Arrays.asList(Accuracy_Evidence,Experiment_Evidence,Accuracy_Threshold_Evidence), Accuracy_Conclusion);

        //Total time metric pattern
        InputType<TotalTimeMetricEvidence> Total_Time_Evidence = new InputType <>(TotalTimeMetricEvidence.EVIDENCE_NAME, new Type<>(TotalTimeMetricEvidence.class, "TOTAL_TIME_METRIC_EVIDENCE"));
        InputType<TotalTimeReproducibilityThreshold> TotalTime_Threshold_Evidence = new InputType <>(TotalTimeReproducibilityThreshold.EVIDENCE_NAME, new Type<>(TotalTimeReproducibilityThreshold.class,"TOTALTIME_THRESHOLD_EVIDENCE"));
        OutputType<TotalTimeMetricConclusion> Total_Time_Conclusion = new OutputType<>("TOTAL_TIME_METRIC_CONCLUSION", new Type<>(TotalTimeMetricConclusion.class, "TOTAL_TIME_METRIC_CONCLUSION"));
        Strategy Total_Time_Strategy = new TotalTimeMetricStrategy("TOTAL_TIME_METRIC_STRATEGY");
        Pattern Total_Time_Pattern = new Pattern("TOTAL_TIME_METRIC_PATTERN", "TOTAL_TIME_METRIC_PATTERN", Total_Time_Strategy, Arrays.asList(Total_Time_Evidence,Experiment_Evidence,TotalTime_Threshold_Evidence), Total_Time_Conclusion);

        //Reproducible metrics pattern
        OutputType<ReproducibleMetricsConclusion> Reproducible_Metrics_Conclusion = new OutputType<>("REPRODUCIBLE_METRICS_CONCLUSION", new Type<>(ReproducibleMetricsConclusion.class, "REPRODUCIBLE_METRICS_CONCLUSION"));
        Strategy Reproducible_Metrics_Strategy = new ReproducibleMetricsStrategy("REPRODUCIBLE_METRICS_STRATEGY");
        Pattern Reproducible_Metrics_Pattern = new Pattern("REPRODUCIBLE_METRICS_PATTERN", "REPRODUCIBLE_METRICS_PATTERN", Reproducible_Metrics_Strategy, Arrays.asList(Accuracy_Pattern.getOutputType().transformToInput(), Total_Time_Pattern.getOutputType().transformToInput()), Reproducible_Metrics_Conclusion);

        //Valid experiment pattern
        InputType<LogEvidence> Log_Evidence = new InputType <>(LogEvidence.EVIDENCE_NAME, new Type<>(LogEvidence.class, "LOG_E"));
        InputType<XpEvidence> Xp_Evidence = new InputType <>(XpEvidence.EVIDENCE_NAME, new Type<>(XpEvidence.class, "XP_E"));
        OutputType<ValidXpConclusion> Valid_Xp_Conclusion = new OutputType<>("VALID_XP_C", new Type<>(ValidXpConclusion.class, "VALID_XP_C"));
        Strategy Valid_Xp_Strategy = new ValidXpStrategy1("VALIDE_XP_STRATEGY");
        Pattern Valid_Xp_Pattern = new Pattern("VALIDE_XP_PATTERN", "VALIDE_XP_PATTERN", Valid_Xp_Strategy, Arrays.asList(Log_Evidence,Xp_Evidence), Valid_Xp_Conclusion);


        //Top level reproducible experiment pattern
        OutputType<ReproducibleExperimentConclusion> Reproducible_Experiment_Conclusion = new OutputType<>("REPRODUCIBLE_EXPERIMENT_CONCLUSION", new Type<>(ReproducibleExperimentConclusion.class, "REPRODUCIBLE_EXPERIMENT_CONCLUSION"));
        Strategy Reproducible_Experiment_Strategy = new ReproducibleExperimentFinalStrategy("REPRODUCIBLE_EXPERIMENT_STRATEGY");
        Pattern Reproducible_Experiment_Pattern = new Pattern("REPRODUCIBLE_EXPERIMENT_PATTERN", "REPRODUCIBLE_EXPERIMENT_PATTERN", Reproducible_Experiment_Strategy, Arrays.asList(Reproducible_Metrics_Pattern.getOutputType().transformToInput(), Valid_Xp_Pattern.getOutputType().transformToInput()), Reproducible_Experiment_Conclusion);

        jpd.addStep(Accuracy_Pattern);
        jpd.addStep(Total_Time_Pattern);
        jpd.addStep(Reproducible_Metrics_Pattern);
        jpd.addStep(Reproducible_Experiment_Pattern);
        jpd.addStep(Valid_Xp_Pattern);
        return jpd;
    }

}
