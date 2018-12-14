package fr.axonic.io.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.axonic.base.ABoolean;
import fr.axonic.base.AContiniousDate;
import fr.axonic.base.AContinuousNumber;
import fr.axonic.base.ADate;
import fr.axonic.base.AEnum;
import fr.axonic.base.ANumber;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.ARangedString;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AVar;
import fr.axonic.validation.exception.VerificationException;

public class AVarSetter {

    private static Logger LOGGER = LoggerFactory.getLogger(AVarSetter.class);

    public static void set(AVar variable, String value) {

        if (variable instanceof AContinuousNumber) {
            set((AContinuousNumber) variable, value);
        } else if (variable instanceof ANumber) {
            set((ANumber) variable, value);
        } else if (variable instanceof AContiniousDate) {
            set((AContiniousDate) variable, value);
        } else if (variable instanceof ADate) {
            set((ADate) variable, value);
        } else if (variable instanceof ARangedEnum) {
            set((ARangedEnum) variable, value);
        } else if (variable instanceof AEnum) {
            set((AEnum) variable, value);
        } else if (variable instanceof ARangedString) {
            set((ARangedString) variable, value);
        } else if (variable instanceof AString) {
            set((AString) variable, value);
        } else if (variable instanceof ABoolean) {
            set((ABoolean) variable, value);
        }
    }

    private static void set(ABoolean variable, String value) {
        try {
            if (!value.equals("null")) {
                variable.setValue(Boolean.parseBoolean(value));
            }
        } catch (VerificationException e1) {
            LOGGER.error(e1.getMessage(), e1);
        }
    }

    private static void set(ANumber number, String value) {
        try {
            if (!value.equals("null")) {
                number.setValue(Double.parseDouble(value));
            }
        } catch (VerificationException e1) {
            LOGGER.error(e1.getMessage(), e1);
        }
    }

    private static void set(AString string, String value) {
        try {
            if (!value.equals("null")) {
                string.setValue(value);
            }
        } catch (VerificationException e1) {
            LOGGER.error(e1.getMessage(), e1);
        }
    }

    private static void set(AEnum aEnum, String value) {
        try {
            if (!value.equals("null")) {
                aEnum.setValue(aEnum.valueOf(value));
            }
        } catch (VerificationException e1) {
            LOGGER.error(e1.getMessage(), e1);
        }
    }
}
