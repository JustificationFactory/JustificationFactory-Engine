package fr.axonic.avek.bus;

import fr.axonic.avek.dao.JustificationSystemsDAO;
import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.JustificationSystemAPI;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.support.evidence.Document;
import fr.axonic.avek.instance.JustificationSystemEnum;
import fr.axonic.avek.instance.JustificationSystemFactory;
import fr.axonic.avek.instance.redmine.RedmineDocument;
import fr.axonic.avek.instance.redmine.RedmineDocumentApproval;
import fr.axonic.avek.instance.redmine.RedmineDocumentEvidence;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * These tests use the known implementation of the Redmine JPD.
 */
public class StepBuilderTest {

    private JustificationSystemsDAO dao;
    private StepBuilder stepBuilder;

    @Before
    public void initialize() throws VerificationException, WrongEvidenceException {
        dao = new JustificationSystemsDAO() {

            private Map<String, JustificationSystem> content;

            {
                content = new HashMap<>();
                content.put("REDMINE", JustificationSystemFactory.create(JustificationSystemEnum.REDMINE));
            }

            @Override
            public Map<String, JustificationSystem> loadJustificationSystems() throws IOException {
                return content;
            }

            @Override
            public void saveJustificationSystem(String name, JustificationSystemAPI argumentationSystem) throws IOException {

            }

            @Override
            public void removeJustificationSystem(String name) throws IOException {

            }
        };

        stepBuilder = new StepBuilder(dao);
    }

    @Test
    public void shouldNotBuildAnythingWithoutSupport() throws StrategyException, StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));

        assertEquals(0, stepBuilder.getBuiltSteps().size());
    }


    @Test
    public void shouldNotBuildWithUnexpectedSupports() throws StrategyException, StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_CTR_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_CTR_0001", "A"));

        assertEquals(0, stepBuilder.getBuiltSteps().size());
    }


    @Test
    public void shouldNotBuildWithSupportsWhenMissingDependency() throws StrategyException, StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0002", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0002", "A"));

        assertEquals(0, stepBuilder.getBuiltSteps().size());
    }


    @Test
    public void shouldBuildWithAdequateSupports() throws StrategyException, StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "A"));

        assertEquals(1, stepBuilder.getBuiltSteps().size());
        assertNotNull(stepBuilder.getBuiltSteps().get(0));
    }

    @Test
    public void shouldBuildWithNotAdequateVersions() throws StrategyException, StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "A"));

        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "B"));

        assertEquals(1, stepBuilder.getBuiltSteps().size());
        assertNotNull(stepBuilder.getBuiltSteps().get(0));
    }

    @Test
    public void shouldBuildWithAdequateVersions() throws StrategyException, StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "A"));

        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "B"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "B"));


        assertEquals(1, stepBuilder.getBuiltSteps().size());
        assertNotNull(stepBuilder.getBuiltSteps().get(0));
        assertEquals("B",stepBuilder.getBuiltSteps().get(0).getSupports().get(0).getElement().getVersion());
        assertEquals("B",stepBuilder.getBuiltSteps().get(0).getSupports().get(1).getElement().getVersion());
    }

    @Test
    public void shouldBuildWithAdequateVersionsTwice() throws StrategyException, StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "A"));

        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "B"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "B"));

        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "C"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "C"));

        assertEquals(1, stepBuilder.getBuiltSteps().size());
        assertNotNull(stepBuilder.getBuiltSteps().get(0));
        assertEquals("C",stepBuilder.getBuiltSteps().get(0).getSupports().get(0).getElement().getVersion());
        assertEquals("C",stepBuilder.getBuiltSteps().get(0).getSupports().get(1).getElement().getVersion());
    }
    @Test
    public void shouldNotBuildAPatternTwice() throws StrategyException, StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "A"));

        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "A"));

        assertEquals(1, stepBuilder.getBuiltSteps().size());
        assertNotNull(stepBuilder.getBuiltSteps().get(0));
    }

    @Test
    public void shouldBuildWithMoreThanEnoughSupports() throws StrategyException, StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0002", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0002", "A"));
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "A"));

        assertEquals(2, stepBuilder.getBuiltSteps().size());
        assertNotNull(stepBuilder.getBuiltSteps().get(0));
        assertNotNull(stepBuilder.getBuiltSteps().get(1));
    }

    @Test
    public void shouldBuildEvenStepsWithoutSupport() throws StrategyException, StepBuildingException {
        for (int i = 0; i < 14; i++) {
            stepBuilder.acknowledgeSupport(evidence("SWAM_ST_" + stNumber(i), "A"));
            stepBuilder.acknowledgeSupport(approval("SWAM_ST_" + stNumber(i), "A"));
        }

        assertEquals(14, stepBuilder.getBuiltSteps().size());
    }

    private static String stNumber(int number) {
        StringBuilder strNumber = new StringBuilder(Integer.toString(number));

        while (strNumber.length() < 4) {
            strNumber.insert(0, "0");
        }

        return strNumber.toString();
    }

    private static RedmineDocumentEvidence evidence(String name, String version) {
         RedmineDocument document = new RedmineDocument("http://aurl.com/" + name);
        document.setVersion(version);

        return new RedmineDocumentEvidence(name, document);
    }

    private static RedmineDocumentApproval approval(String name, String version) {
        String approvalName = name + "_APPROVAL";

        Document document = new Document("http://aurl.com/" + approvalName);
        document.setVersion(version);

        return new RedmineDocumentApproval(approvalName, document);
    }
}