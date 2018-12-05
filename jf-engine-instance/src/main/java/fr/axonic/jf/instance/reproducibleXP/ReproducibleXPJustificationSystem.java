package fr.axonic.jf.instance.reproducibleXP;
import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.diagram.JustificationPatternDiagram;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.pattern.DiagramPatternsBase;

import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.type.InputType;
import fr.axonic.jf.engine.pattern.type.OutputType;
import fr.axonic.jf.engine.pattern.type.Type;
import fr.axonic.jf.instance.ValidXp.conclusion.ValidXpConclusion;
import fr.axonic.jf.instance.reproducibleXP.conclusions.AccuracyMetricsConclusion;
import fr.axonic.jf.instance.reproducibleXP.conclusions.ReproducibleMetricsConclusion;
import fr.axonic.jf.instance.reproducibleXP.conclusions.ReproducibleXPConclusion;
import fr.axonic.jf.instance.reproducibleXP.conclusions.TotalTimeMetricsConclusion;
import fr.axonic.jf.instance.reproducibleXP.evidences.AccuracyMetric;
import fr.axonic.jf.instance.reproducibleXP.evidences.TotalTimeMetric;
import fr.axonic.jf.instance.reproducibleXP.strategies.*;
import fr.axonic.validation.exception.VerificationException;

import java.util.ArrayList;
import java.util.List;

public class ReproducibleXPJustificationSystem extends JustificationSystem<DiagramPatternsBase> {

    public ReproducibleXPJustificationSystem() throws VerificationException, WrongEvidenceException {
        super(createPatternsBase());
    }

    private static DiagramPatternsBase createPatternsBase() {
        return new DiagramPatternsBase(createJPD());
    }

    private static JustificationPatternDiagram createJPD() {
        JustificationPatternDiagram jpd = new JustificationPatternDiagram();

        //Supports or evidences
        InputType accuracyMetric = new InputType <>(AccuracyMetric.EVIDENCE_NAME, new Type<>(AccuracyMetric.class, "ACCURACY_METRIC"));
        InputType totalTimeMetric = new InputType<>(TotalTimeMetric.EVIDENCE_NAME, new Type <>(TotalTimeMetric.class,"TOTALTIME_METRIC"));


        //Strategies
        TopLevelReproducibleXPStrategy reproducibleXPStrategy = new TopLevelReproducibleXPStrategy("REPRODUCIBLE_XP_STRATEGY");

        ReproducibleMetricStrategy reproducibleMetricStrategy = new ReproducibleMetricStrategy("REPRODUCIBLE_METRICS_STRATEGY");
        AccuracyMetricStrategy accuracyMetricStrategy = new AccuracyMetricStrategy("ACCURACY_STRATEGY");
        TotalTimeMetricStrategy totalTimeMetricStrategy =  new TotalTimeMetricStrategy("TOTALTIME_STRATEGY");


        //Metrics Conclusions
        OutputType<ReproducibleXPConclusion> reproducibleXPConclusion = new OutputType<>("REPRODUCIBLE_XP_CONCLUSION", new Type<>(ReproducibleXPConclusion.class, "REPRODUCIBLE_XP_CONCLUSION"));

        OutputType<ReproducibleMetricsConclusion> reproducibleMetricsConclusion = new OutputType<>("METRICS_CONCLUSION", new Type<>(ReproducibleMetricsConclusion.class, "METRICS_CONCLUSION"));

        OutputType<AccuracyMetricsConclusion> accuracyMetricsConclusion = new OutputType<>("ACCURACY_CONCLUSION", new Type<>(AccuracyMetricsConclusion.class, "ACCURACY_CONCLUSION"));

        OutputType<TotalTimeMetricsConclusion> totalTimeMetricsConclusion = new OutputType<>("TOTALTIME_CONCLUSION", new Type<>(TotalTimeMetricsConclusion.class, "TOTALTIME_CONCLUSION"));


        //List of supports for each pattern
        List<InputType> accuracySupports = new ArrayList();
        accuracySupports.add(accuracyMetric);

        List<InputType> totaltimeSupports = new ArrayList();
        totaltimeSupports.add(totalTimeMetric);

        List<InputType> metricsConclusionsSupports = new ArrayList <>();
        metricsConclusionsSupports.add(accuracyMetricsConclusion.transformToInput());
        metricsConclusionsSupports.add(totalTimeMetricsConclusion.transformToInput());

        List<InputType> XPConclusionsSupports = new ArrayList <>();
        XPConclusionsSupports.add(reproducibleMetricsConclusion.transformToInput());
        //TODO: Add the conclusion of ValidXP to have the second part of the diagram


       //Patterns
       Pattern accuracyPattern = new Pattern("ACCURACY_PATTERN","ACCURACY_PATTERN",accuracyMetricStrategy,accuracySupports,accuracyMetricsConclusion);

       Pattern totaltimePattern = new Pattern("TOTALTIME_PATTERN","TOTALTIME_PATTERN",totalTimeMetricStrategy,totaltimeSupports,totalTimeMetricsConclusion);

        //Pattern which takes two conclusions (conclusion of the accuracy metric analysis and conclusion of the total-time metric analysis)
        Pattern reproducibleMetricsPattern = new Pattern("REPRODUCIBLE_METRICS_PATTERN","REPRODUCIBLE_METRICS_PATTERN",reproducibleMetricStrategy,metricsConclusionsSupports,reproducibleMetricsConclusion);


        Pattern reproducibleXPPattern = new Pattern("REPRODUCIBLE_XP_PATTERN","REPRODUCIBLE_XP_PATTERN",reproducibleXPStrategy,XPConclusionsSupports,reproducibleXPConclusion);


        jpd.addStep(accuracyPattern);
        jpd.addStep(totaltimePattern);
        jpd.addStep(reproducibleMetricsPattern);
        jpd.addStep(reproducibleXPPattern);
        return jpd;
    }
}
