package fr.axonic.jf.instance.ValidXp.documents;


import fr.axonic.jf.engine.support.evidence.Document;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class XpDocument extends Document{

    private String jobId;



    private boolean valid;

    public XpDocument(String jobId, boolean validxp) {
        this.jobId = jobId;
        this.valid = validxp;
        super.init();
    }


    public XpDocument() {
    }

    @XmlElement
    public boolean getValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
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
        return Objects.equals(jobId, that.jobId) && valid == that.valid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), jobId,valid);
    }
}
