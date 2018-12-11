package fr.axonic.jf.instance.ReproducibleExperiment.documents;

import fr.axonic.jf.engine.support.evidence.Document;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

public class ThresholdDocument extends Document {

    private double value;

    public ThresholdDocument(double thresholdValue) {
        this.value = thresholdValue;
        super.init();
    }
    public ThresholdDocument(){}

    @XmlElement
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ThresholdDocument that= (ThresholdDocument) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
