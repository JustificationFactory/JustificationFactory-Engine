package fr.axonic.jf.bus.business;

import fr.axonic.jf.bus.business.supports.ListKnownSupportsDAO;
import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.JustificationSystemAPI;
import fr.axonic.jf.engine.exception.StepBuildingException;
import fr.axonic.jf.engine.exception.WrongEvidenceException;
import fr.axonic.jf.engine.support.evidence.Document;
import fr.axonic.jf.instance.JustificationSystemEnum;
import fr.axonic.jf.instance.JustificationSystemFactory;
import fr.axonic.jf.instance.redmine.RedmineDocument;
import fr.axonic.jf.instance.redmine.RedmineDocumentApproval;
import fr.axonic.jf.instance.redmine.RedmineDocumentEvidence;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
            public Map<String, JustificationSystem> getJustificationSystems() {
                return content;
            }

            @Override
            public JustificationSystem getJustificationSystem(String name) {
                return content.get(name);
            }

            @Override
            public void saveJustificationSystem(String name, JustificationSystemAPI argumentationSystem) {

            }

            @Override
            public void removeJustificationSystem(String name) {

            }
        };

        stepBuilder = new StepBuilder(dao, new ListKnownSupportsDAO());
    }

    @Test
    public void shouldNotBuildAnythingWithoutSupport() throws StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));

        assertEquals(0, stepBuilder.getBuiltSteps().size());
    }

    @Test
    public void shouldNotBuildWithUnexpectedSupports() throws StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_CTR_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_CTR_0001", "A"));

        assertEquals(0, stepBuilder.getBuiltSteps().size());
    }

    @Test
    public void shouldNotBuildWithSupportsWhenMissingDependency() throws StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0002", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0002", "A"));

        assertEquals(0, stepBuilder.getBuiltSteps().size());
    }

    @Test
    public void shouldBuildWithAdequateSupports() throws StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "A"));

        assertEquals(1, stepBuilder.getBuiltSteps().size());
        assertNotNull(stepBuilder.getBuiltSteps().get(0));
    }

    @Test
    public void shouldBuildWithNotAdequateVersions() throws StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "A"));

        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "B"));

        assertEquals(1, stepBuilder.getBuiltSteps().size());
        assertNotNull(stepBuilder.getBuiltSteps().get(0));
    }

    @Test
    public void shouldBuildWithAdequateVersions() throws StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "A"));

        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "B"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "B"));


        assertEquals(1, stepBuilder.getBuiltSteps().size());
        assertNotNull(stepBuilder.getBuiltSteps().get(0));
        assertEquals("B", stepBuilder.getBuiltSteps().get(0).getSupports().get(0).getElement().getVersion());
        assertEquals("B", stepBuilder.getBuiltSteps().get(0).getSupports().get(1).getElement().getVersion());
    }

    @Test
    public void shouldBuildWithAdequateVersionsTwice() throws StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "A"));

        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "B"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "B"));

        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "C"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "C"));

        assertEquals(1, stepBuilder.getBuiltSteps().size());
        assertNotNull(stepBuilder.getBuiltSteps().get(0));
        assertEquals("C", stepBuilder.getBuiltSteps().get(0).getSupports().get(0).getElement().getVersion());
        assertEquals("C", stepBuilder.getBuiltSteps().get(0).getSupports().get(1).getElement().getVersion());
    }

    @Test
    public void shouldNotBuildAPatternTwice() throws StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "A"));

        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "A"));

        assertEquals(1, stepBuilder.getBuiltSteps().size());
        assertNotNull(stepBuilder.getBuiltSteps().get(0));
    }

    @Test
    public void shouldBuildWithMoreThanEnoughSupports() throws StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0002", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0002", "A"));
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "A"));

        assertEquals(2, stepBuilder.getBuiltSteps().size());
        assertNotNull(stepBuilder.getBuiltSteps().get(0));
        assertNotNull(stepBuilder.getBuiltSteps().get(1));
    }

    @Test
    public void shouldBuildEvenStepsWithoutSupport() throws StepBuildingException {
        for (int i = 0; i < 14; i++) {
            stepBuilder.acknowledgeSupport(evidence("SWAM_ST_" + stNumber(i), "A"));
            stepBuilder.acknowledgeSupport(approval("SWAM_ST_" + stNumber(i), "A"));
        }

        assertEquals(14, stepBuilder.getBuiltSteps().size());
    }

    @Test
    public void shouldNotLoop() throws StepBuildingException {
        acknowledge("SWAM_ST_0001", "A");
        acknowledge("SWAM_ST_0003", "A");
        acknowledge("SWAM_ST_0006", "A");
        acknowledge("SWAM_ST_0007", "A");
        acknowledge("SWAM_ST_0002", "A");
        acknowledge("SWAM_ST_0004", "A");
        acknowledge("SWAM_ST_0005", "A");
        acknowledge("SWAM_ST_0010", "A");
        acknowledge("SWAM_ST_0002", "B");
        acknowledge("SWAM_ST_0003", "B");
        acknowledge("SWAM_ST_0007", "B");
        acknowledge("SWAM_ST_0006", "B");
        acknowledge("SWAM_ST_0008", "A");
        acknowledge("SWAM_ST_0001", "B");
        acknowledge("SWAM_ST_0002", "C");
        acknowledge("SWAM_ST_0013", "A");
        acknowledge("SWAM_ST_0003", "C");
        acknowledge("SWAM_ST_0011", "A");
        acknowledge("SWAM_ST_0001", "C");
        acknowledge("SWAM_ST_0002", "D");
        acknowledge("SWAM_ST_0004", "B");
        acknowledge("SWAM_ST_0013", "B");
        acknowledge("SWAM_ST_0002", "E");
        acknowledge("SWAM_ST_0008", "B");
        acknowledge("SWAM_ST_0009", "A");
        acknowledge("SWAM_ST_0012", "A");
        acknowledge("SWAM_ST_0005", "B");
        acknowledge("SWAM_ST_0002", "F");
        acknowledge("SWAM_ST_0013", "C");
        acknowledge("SWAM_ST_0001", "D");
        acknowledge("SWAM_ST_0002", "G");
        acknowledge("SWAM_ST_0002", "H");
        acknowledge("SWAM_ST_0008", "C");
        acknowledge("SWAM_ST_0013", "D");
        acknowledge("SWAM_ST_0013", "E");
        acknowledge("SWAM_ST_0006", "C");
        acknowledge("SWAM_ST_0001", "E");
        acknowledge("SWAM_ST_0001", "F");
        acknowledge("SWAM_ST_0002", "I");
        acknowledge("SWAM_ST_0002", "J");
        acknowledge("SWAM_ST_0007", "C");
        acknowledge("SWAM_ST_0007", "D");
        acknowledge("SWAM_ST_0007", "E");
        acknowledge("SWAM_ST_0008", "D");
        acknowledge("SWAM_ST_0011", "B");
        acknowledge("SWAM_ST_0013", "F");
        acknowledge("SWAM_ST_0001", "G");
        acknowledge("SWAM_ST_0001", "H");
        acknowledge("SWAM_ST_0002", "K");
        acknowledge("SWAM_ST_0002", "L");
        acknowledge("SWAM_ST_0013", "G");
        acknowledge("SWAM_ST_0013", "H");
        acknowledge("SWAM_ST_0001", "I");
        acknowledge("SWAM_ST_0002", "M");
        acknowledge("SWAM_ST_0003", "D");
        acknowledge("SWAM_ST_0008", "E");
        acknowledge("SWAM_ST_0008", "F");
        acknowledge("SWAM_ST_0013", "I");
        acknowledge("SWAM_ST_0007", "F");
        acknowledge("SWAM_ST_0007", "G");
        acknowledge("SWAM_ST_0007", "H");
        acknowledge("SWAM_ST_0001", "J");
    }

    @Test
    public void shouldBuildStepsWithOnlyConclusionsSupports() throws StepBuildingException, IOException {
        for (int i = 1; i < 14; i++) {
            StringBuilder numberBuilder = new StringBuilder(Integer.toString(i));
            while (numberBuilder.length() < 4) {
                numberBuilder.insert(0, "0");
            }

            acknowledge("SWAM_ST_" + numberBuilder.toString(), "A");
        }

        assertTrue(dao.getJustificationSystem("REDMINE")
                .matrix()
                .getContent().stream()
                .anyMatch(i -> i.getStep().getPatternId().equals("SWAM_ST_VALIDATION")));
    }

    private void acknowledge(String name, String version) throws StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence(name, version));
        stepBuilder.acknowledgeSupport(approval(name, version));
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