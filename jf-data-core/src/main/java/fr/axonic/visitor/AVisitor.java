package fr.axonic.visitor;

import fr.axonic.base.AContiniousDate;
import fr.axonic.base.AContinuousNumber;
import fr.axonic.base.ADate;
import fr.axonic.base.AEnum;
import fr.axonic.base.ANumber;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.ARangedString;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AElement;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AStructure;
import fr.axonic.base.engine.AVar;

/**
 * 
 * @author cboinaud
 * @author bgrabiec
 *
 * @param <T>
 */
public interface AVisitor<T extends AVisitor> {

    default T visit(AEntity e) {

        if (e instanceof AStructure) {
            return (T) visit((AStructure) e);
        } else if (e instanceof AVar) {
            return (T) visit((AVar) e);
        } else if (e instanceof AList) {
            return (T) visit((AList) e);
        }
        return (T) this;
    }

    default T visit(AElement e) {

        if (e instanceof AStructure) {
            return (T) visit((AStructure) e);
        } else if (e instanceof AVar) {
            return (T) visit((AVar) e);
        }

        return (T) this;
    }

    default T visit(AStructure e) {

        e.getFieldsContainer().values().forEach(this::visit);
        return (T) this;
    }

    default <S extends AEntity> T visit(AList<S> e) {

        e.forEach(this::visit);
        return (T) this;
    }

    default T visit(AVar e) {

        if (e instanceof AContinuousNumber) {
            return visit((AContinuousNumber) e);
        } else if (e instanceof ANumber) {
            return visit((ANumber) e);
        } else if (e instanceof AContiniousDate) {
            return visit((AContiniousDate) e);
        } else if (e instanceof ADate) {
            return visit((ADate) e);
        } else if (e instanceof ARangedEnum) {
            return visit((ARangedEnum) e);
        } else if (e instanceof AEnum) {
            return visit((AEnum) e);
        } else if (e instanceof ARangedString) {
            return visit((ARangedString) e);
        } else if (e instanceof AString) {
            return visit((AString) e);
        }

        return (T) this;
    }

    default T visit(ANumber e) {
        return (T) visit((AVar) e);
    }

    default T visit(AContinuousNumber e) {
        return (T) visit((AVar) e);
    }

    default T visit(ADate e) {
        return (T) visit((AVar) e);
    }

    default T visit(AContiniousDate e) {
        return (T) visit((AVar) e);
    }

    default T visit(AString e) {
        return (T) visit((AVar) e);
    }

    default T visit(ARangedString e) {
        return (T) visit((AVar) e);
    }

    default T visit(AEnum e) {
        return (T) visit((AVar) e);
    }

    default T visit(ARangedEnum e) {
        return (T) visit((AVar) e);
    }

}
