package fr.axonic.jf.engine;

import fr.axonic.jf.engine.constraint.PatternConstraintException;
import fr.axonic.jf.engine.exception.StepBuildingException;
import fr.axonic.jf.engine.exception.StrategyException;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.kernel.JustificationDiagramAPI;
import fr.axonic.jf.engine.pattern.JustificationStep;
import fr.axonic.jf.engine.pattern.Pattern;
import fr.axonic.jf.engine.pattern.PatternsBase;
import fr.axonic.jf.engine.support.Support;
import fr.axonic.jf.engine.support.conclusion.Conclusion;
import fr.axonic.jf.engine.support.evidence.Hypothesis;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * Created by cduffau on 04/08/16.
 */
@XmlRootElement
@XmlSeeAlso(JustificationSystem.class)
public interface JustificationSystemAPI<T extends PatternsBase> {

    List<Support> getUnusedAssertions(List<Support> supports);

    List<Pattern> getApplicablePatterns(List<Support> supports);

    JustificationStep constructStep(Pattern pattern, List<Support> evidences, Conclusion conclusion) throws StepBuildingException, WrongEvidenceException, StrategyException;

    JustificationDiagramAPI getJustificationDiagram();

    T getPatternsBase();

    boolean validate();

    boolean isComplete();

    JustificationMatrix matrix();

    void resolveHypothesis(JustificationStep step, Hypothesis hypothesis, Support support) throws WrongEvidenceException, PatternConstraintException;

}
