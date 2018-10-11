package fr.axonic.jf.instance.school.documents;

import fr.axonic.jf.engine.support.evidence.Document;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class ProjectGradeDocument extends Document {

    private int grade;

    public ProjectGradeDocument(String url, int grade) {
        super(url);
        this.grade = grade;
    }

    public ProjectGradeDocument() {
        // Constructor used for marshalling.
    }

    @XmlElement
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProjectGradeDocument that = (ProjectGradeDocument) o;
        return grade == that.grade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), grade);
    }
}
