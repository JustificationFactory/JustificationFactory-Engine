package fr.axonic.jf.instance.ReproducibleExperiment.documents;

import fr.axonic.jf.engine.support.evidence.Document;

import javax.xml.bind.annotation.XmlElement;

public class ReproducibleDocument extends Document {

    private String jobId;
    private boolean reproducible;

    public ReproducibleDocument(String jobId, boolean reproducible) {
        this.jobId = jobId;
        this.reproducible = reproducible;
        super.init();
    }
    public ReproducibleDocument(){}

    @XmlElement
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String id) {
        this.jobId = id;
    }

    @XmlElement
    public boolean isReproducible() {
        return reproducible;
    }

    public void setReproducible(boolean reproducible) {
        this.reproducible = reproducible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ReproducibleDocument that= (ReproducibleDocument) o;
        return reproducible == that.reproducible;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
