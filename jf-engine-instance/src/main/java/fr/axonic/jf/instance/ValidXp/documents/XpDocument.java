package fr.axonic.jf.instance.ValidXp.documents;


import fr.axonic.jf.engine.support.evidence.Document;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class XpDocument extends Document{

    private String jobId;

    public XpDocument(String url, String jobId) {
        super(url);
        this.jobId = jobId;
    }

    public XpDocument() {
    }


    @XmlElement
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        XpDocument that = (XpDocument) o;
        return Objects.equals(jobId, that.jobId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), jobId);
    }
}
