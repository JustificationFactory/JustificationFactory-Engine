package fr.axonic.jf.instance.ReproducibleExperiment.documents;

import fr.axonic.jf.engine.support.evidence.Document;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

public class ThresholdDocument extends Document {

    private int value;

    public ThresholdDocument(String url, int thresholdValue) {
        super(url);
        this.value = thresholdValue;
    }
    public ThresholdDocument(){}

    @XmlElement
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
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
