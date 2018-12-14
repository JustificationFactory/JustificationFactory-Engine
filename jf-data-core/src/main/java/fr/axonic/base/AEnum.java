package fr.axonic.base;

import javax.xml.bind.annotation.XmlRootElement;

import fr.axonic.base.engine.AEnumItem;
import fr.axonic.base.engine.AVar;
import fr.axonic.base.format.EnumFormat;
import fr.axonic.validation.Verifiable;
import fr.axonic.validation.Verify;
import fr.axonic.validation.exception.VerificationException;
import fr.axonic.visitor.AVisitor;

/**
 * Created by cduffau on 08/07/16.
 */
@XmlRootElement
public class AEnum<T extends Enum<T> & AEnumItem> extends AVar<T> implements Verifiable {

    /**
     * NEVER USE THIS CONSTRUCTOR (except if you set immediatly the format after)
     */
    @Deprecated
    public AEnum() {
        super(new EnumFormat<>(), null);
    }

    protected AEnum(T value) {
        super(new EnumFormat(), value);
    }

    public AEnum(Class<T> tClass, T value) {
        super(new EnumFormat<T>(tClass), value);
    }

    public AEnum(Class<T> tClass) {
        super(new EnumFormat<T>(tClass), null);
    }

    @Override
    @Verify
    public void verify(boolean verifyConsistency) throws VerificationException {
        // DO NOTHING
        // There are no constraints to be verified in AVar.
    }

    @Override
    public String toString() {
        return "AEnum{" + super.toString() + "}";
    }

    @Override
    public void accept(AVisitor v) {
        v.visit(this);
    }

    public T valueOf(String value) {
        return (T) Enum.valueOf(getType(), value);
    }

    public T[] values() {
        return (T[]) this.getValue().getClass().getEnumConstants();
    }
}
