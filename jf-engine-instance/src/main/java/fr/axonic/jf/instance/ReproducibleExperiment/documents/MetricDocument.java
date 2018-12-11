package fr.axonic.jf.instance.ReproducibleExperiment.documents;

import fr.axonic.jf.engine.support.evidence.Document;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

public class MetricDocument extends Document {

    private String jobId;
    private double value;

    public MetricDocument(String jobId, double metricValue) {
        this.jobId = jobId;
        this.value = metricValue;
        super.init();
    }
    public MetricDocument(){}

    @XmlElement
    public String getJobId() {
        return jobId;
    }

    @XmlElement
    public double getValue() {
        return value;
    }


    public void setJobId(String id) {
        this.jobId = id;
    }
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MetricDocument that= (MetricDocument) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

}
