package fr.axonic.jf.instance.ValidXp.documents;

import fr.axonic.jf.engine.support.evidence.Document;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class LogDocument extends Document{

    private String jobId;
    private int exitCode;

    public LogDocument(String jobId, int exitCode) {
        this.jobId = jobId;
        this.exitCode = exitCode;
        super.init();
    }

    public LogDocument() {
    }


    @XmlElement
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    @XmlElement
    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LogDocument that = (LogDocument) o;
        return exitCode == that.exitCode &&
                Objects.equals(jobId, that.jobId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), jobId, exitCode);
    }
}
