package fr.axonic.jf.instance.ReproducibleExperiment.documents;

import fr.axonic.jf.engine.support.evidence.Document;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

public class MetricDocument extends Document {
    private String metric_id;
    private int value;

    public MetricDocument(String url, String metricId, int metricValue) {
        super(url);
        this.metric_id = metricId;
        this.value = metricValue;
    }
    public MetricDocument(){}

    @XmlElement
    public String getMetricId() {
        return metric_id;
    }

    @XmlElement
    public int getValue() {
        return value;
    }


    public void setMetricId(String id) {
        this.metric_id = id;
    }
    public void setValue(int value) {
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
