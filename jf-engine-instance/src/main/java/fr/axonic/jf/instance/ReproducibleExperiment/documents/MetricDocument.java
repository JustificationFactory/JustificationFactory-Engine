package fr.axonic.jf.instance.ReproducibleExperiment.documents;

import fr.axonic.jf.engine.support.evidence.Document;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.Objects;

public class MetricDocument extends Document {

    private String jobId;
    private List<Double> values;

    public MetricDocument(String jobId, List<Double> metricValues) {
        this.jobId = jobId;
        this.values = metricValues;
        super.init();
    }
    public MetricDocument(){}

    @XmlElement
    public String getJobId() {
        return jobId;
    }

    @XmlElement
    public List<Double> getValues() {
        return values;
    }


    public void setJobId(String id) {
        this.jobId = id;
    }
    public void setValue(List<Double> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MetricDocument that= (MetricDocument) o;
        return values == that.values;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), values);
    }

}
