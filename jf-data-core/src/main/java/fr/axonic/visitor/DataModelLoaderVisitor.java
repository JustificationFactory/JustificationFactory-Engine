package fr.axonic.visitor;

import fr.axonic.base.*;
import fr.axonic.validation.exception.VerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by cboinaud on 19/09/2016.
 */
public class DataModelLoaderVisitor implements AVisitor<DataModelLoaderVisitor> {

    private static Logger       LOGGER = LoggerFactory.getLogger(DataModelLoaderVisitor.class);

    private Map<String, String> data;

    public DataModelLoaderVisitor(Map<String, String> data) {
        this.data = data;
    }

    @Override
    public DataModelLoaderVisitor visit(ANumber e) {
        try {
            String value = data.get(e.getPath() + "." + e.getCode());
            if (!value.equals("null")) {
                e.setValue(Double.parseDouble(value));
            }
        } catch (VerificationException e1) {
            LOGGER.error(e1.getMessage(), e1);
        }

        return this;
    }

    @Override
    public DataModelLoaderVisitor visit(AContinuousNumber e) {
        return visit((ANumber) e);
    }

    @Override
    public DataModelLoaderVisitor visit(ADate e) {
        // TODO
        return null;
    }

    @Override
    public DataModelLoaderVisitor visit(AContiniousDate e) {
        return visit((ADate) e);
    }

    @Override
    public DataModelLoaderVisitor visit(AString e) {
        try {
            String value = data.get(e.getPath() + "." + e.getCode());
            if (!value.equals("null")) {
                e.setValue(value);
            }
        } catch (VerificationException e1) {
            LOGGER.error(e1.getMessage(), e1);
        }

        return this;
    }

    @Override
    public DataModelLoaderVisitor visit(ARangedString e) {
        return visit((AString) e);
    }

    @Override
    public DataModelLoaderVisitor visit(AEnum e) {
        try {
            String value = data.get(e.getPath() + "." + e.getCode());
            if (!value.equals("null")) {
                e.setValue(e.valueOf(value));
            }
        } catch (VerificationException e1) {
            LOGGER.error(e1.getMessage(), e1);
        }

        return this;
    }

    @Override
    public DataModelLoaderVisitor visit(ARangedEnum e) {
        return visit((AEnum) e);
    }

    public Map<String, String> getData() {
        return data;
    }
}
