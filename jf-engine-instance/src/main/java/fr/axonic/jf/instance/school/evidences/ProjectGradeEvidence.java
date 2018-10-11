package fr.axonic.jf.instance.school.evidences;

import fr.axonic.jf.engine.support.evidence.Evidence;
import fr.axonic.jf.instance.school.documents.ProjectGradeDocument;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProjectGradeEvidence extends Evidence<ProjectGradeDocument> {

    public static final String EVIDENCE_NAME = "HAVE GOT A GOOD PROJECT GRADE";

    public ProjectGradeEvidence(ProjectGradeDocument element) {
        super(EVIDENCE_NAME, element);
    }

    public ProjectGradeEvidence() {
    }
}
