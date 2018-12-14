package fr.axonic.visitor;

import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AStructure;
import fr.axonic.base.engine.AVar;

/**
 * Created by cboinaud on 15/09/2016.
 */
public class InitModelPathVisitor implements AVisitor<InitModelPathVisitor> {

    @Override
    public InitModelPathVisitor visit(AStructure e) {

        if (e.getCode() == null) {
            e.setCode(e.getClass().getSimpleName().substring(0, 1).toLowerCase()
                    + e.getClass().getSimpleName().substring(1));
        }

        e.getFieldsContainer().entrySet().stream().filter(entry -> entry.getValue().getPath() == null)
                .forEach(entry -> {

                    AEntity value = entry.getValue();

                    value.setPath(e.getPath() + "." + e.getCode());

                    if (value.getCode() == null && (value instanceof AVar || value instanceof AList)) {
                        value.setCode(entry.getKey());

                        if (value instanceof AList && !value.getCode().endsWith("s")) {
                            value.setCode(value.getCode() + "s");
                        }
                    }

                    visit(entry.getValue());
                });

        return this;
    }

    @Override
    public <S extends AEntity> InitModelPathVisitor visit(AList<S> e) {

        e.stream().filter(field -> field.getPath() == null).forEach(field -> {
            field.setPath(e.getPath() + "." + e.getCode());

            if (field instanceof AVar && field.getCode() == null) {
                if (e.getCode().endsWith("s")) {
                    field.setCode(e.getCode().substring(0, e.getCode().length() - 1));
                } else {
                    field.setCode(e.getCode() + "Elt");
                }
            } else if (field instanceof AList && field.getCode() == null) {
                field.setCode("sub" + e.getCode().substring(0, 1).toUpperCase() + e.getCode().substring(1));
            }

            visit(field);
        });

        return this;
    }

    @Override
    public InitModelPathVisitor visit(AVar e) {

        if (e.getCode() == null) {
            e.setCode(e.getClass().getSimpleName().substring(0, 1).toLowerCase()
                    + e.getClass().getSimpleName().substring(1));
        }

        return this;
    }

}
