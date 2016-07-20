package fr.axonic.avek.model.base;



import fr.axonic.avek.model.base.engine.AVar;
import fr.axonic.avek.model.base.engine.AVarProperty;
import fr.axonic.avek.model.base.engine.Format;
import fr.axonic.avek.model.base.engine.FormatType;
import fr.axonic.avek.model.verification.Verifiable;
import fr.axonic.avek.model.verification.Verify;
import fr.axonic.avek.model.verification.exception.VerificationException;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class ANumber extends AVar<Number> implements Verifiable {

    private String unit;

    public ANumber() {
        this(null);
    }

    public ANumber(Number value) {
        super(new Format(FormatType.NUMBER), value);
    }
    public ANumber(String label, Number value) {
        super(label,new Format(FormatType.NUMBER), value);
    }

    @Override
    @Verify
    public void verify(boolean verifyConsistency) throws VerificationException {
    }

    @Override
    public String toString() {
        return "ANumber{" +
                "value=" + getValue() +
                ", code='" + getCode() + '\'' +
                ", path='" + getPath() + '\'' +
                '}';
    }
    protected void setPropertyValue(String propertyName, Object newPropertyValue) {
        switch (AVarProperty.valueOf(propertyName)) {
            case UNIT: {
                unit = (String) newPropertyValue;
            }
            break;

            default: {
                super.setPropertyValue(propertyName,newPropertyValue);
            }
        }

    }

    protected Object getPropertyValue(String propertyName) {
        Object result;
        switch (AVarProperty.valueOf(propertyName)) {
            case UNIT: {
                result = unit;
            }
            break;
            default: {
                result = super.getPropertyValue(propertyName);
            }

        }
        return result;
    }
    @Override
    protected boolean isPropertyVerifiable(String propertyName) {
        boolean result;
        switch (AVarProperty.valueOf(propertyName)) {
            case UNIT: {
                result = false;
            }
            break;
            default: {
                result = super.isPropertyVerifiable(propertyName);
            }
        }
        return result;
    }

    @XmlTransient
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) throws VerificationException {
        setProperty(AVarProperty.UNIT.name(),unit);
    }
    @Override
    public AVar clone() throws CloneNotSupportedException {
        ANumber result = (ANumber) super.clone();
        result.unit=unit;

        return result;
    }
}
