package fr.axonic.visitor;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fr.axonic.base.AEnum;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AStructure;
import fr.axonic.base.engine.AVar;

/**
 * Created by cboinaud on 13/09/2016.
 */
public class ModelToDOMVisitor implements AVisitor<ModelToDOMVisitor> {

    private static Logger        LOGGER = LoggerFactory.getLogger(ModelToDOMVisitor.class);

    private Document             document;
    private Map<String, Element> elements;

    public ModelToDOMVisitor() {

        try {

            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            elements = new HashMap<>();

        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    @Override
    public ModelToDOMVisitor visit(AStructure e) {

        if (e.isPersistent()) {

            if (e.getCode() == null) {
                throw new IllegalArgumentException("An entity has an undefined code");
            }

            Element structure = document.createElement(e.getCode());

            if (!document.hasChildNodes()) {
                document.appendChild(structure);
            }

            if (e.getPath() != null && !e.getPath().isEmpty()) {
                Attr path = document.createAttribute("path");
                path.setValue(e.getPath());
                structure.setAttributeNode(path);
            }

            if (e.getLabel() != null && !e.getLabel().isEmpty()) {
                Attr label = document.createAttribute("label");
                label.setValue(e.getLabel());
                structure.setAttributeNode(label);
            }

            Attr type = document.createAttribute("type");
            type.setValue(e.getClass().getName());
            structure.setAttributeNode(type);

            e.getFieldsContainer().forEach((name, entity) -> {

                Element element = visit(entity).getElement(entity.getPath() + "#" + entity.getCode());

                if (element != null) {
                    Attr field = document.createAttribute("field");
                    field.setValue(name);

                    element.setAttributeNode(field);
                    structure.appendChild(element);
                } else if (entity.isPersistent()) {
                    throw new NullPointerException(
                            "An element has not been visited correctly --> NullPointerException returned");
                }

            });

            elements.put(e.getPath() + "#" + e.getCode(), structure);
        }

        return this;
    }

    @Override
    public <S extends AEntity> ModelToDOMVisitor visit(AList<S> e) {

        if (e.isPersistent()) {

            if (e.getCode() == null) {
                throw new IllegalArgumentException("An entity has an undefined code");
            }

            Element list = document.createElement(e.getCode());

            if (!document.hasChildNodes()) {
                document.appendChild(list);
            }

            if (e.getPath() != null && !e.getPath().isEmpty()) {
                Attr path = document.createAttribute("path");
                path.setValue(e.getPath());
                list.setAttributeNode(path);
            }

            if (e.getLabel() != null && !e.getLabel().isEmpty()) {
                Attr label = document.createAttribute("label");
                label.setValue(e.getLabel());
                list.setAttributeNode(label);
            }

            e.forEach(entity -> {

                Element item = visit(entity).getElement(entity.getPath() + "#" + entity.getCode());

                if (item != null) {
                    list.appendChild(item);
                } else if (entity.isPersistent()) {
                    throw new NullPointerException("An element has not been visited correctly");
                }

            });

            elements.put(e.getPath() + "#" + e.getCode(), list);
        }

        return this;
    }

    @Override
    public ModelToDOMVisitor visit(AVar aVar) {

        if (aVar.isPersistent()) {

            if (aVar.getCode() == null) {
                throw new IllegalArgumentException("An entity has an undefined code");
            }

            Element element = document.createElement(aVar.getCode());

            if (!document.hasChildNodes()) {
                document.appendChild(element);
            }

            Attr path = document.createAttribute("path");
            path.setValue(aVar.getPath());

            if (aVar.getLabel() != null && !aVar.getLabel().isEmpty()) {
                Attr label = document.createAttribute("label");
                label.setValue(aVar.getLabel());
                element.setAttributeNode(label);
            }
            
            Attr type = document.createAttribute("type");
            type.setValue(aVar.getClass().getName());
            element.setAttributeNode(type);

            Element value = document.createElement("value");
            String valueString;
            if (aVar instanceof AEnum) {
                AEnum aEnum = (AEnum) aVar;
                valueString = ((Enum) aEnum.getValue()).name();
            } else {
                valueString = aVar.getValue().toString();
            }
            value.appendChild(document.createTextNode((aVar.getValue() == null) ? "null" : valueString));

            element.appendChild(value);
            element.setAttributeNode(path);

            elements.put(aVar.getPath() + "#" + aVar.getCode(), element);
        }

        return this;
    }

    public Document getDocument() {
        return document;
    }

    private Element getElement(String uri) {

        if (elements.containsKey(uri)) {
            return elements.get(uri);
        }

        return null;
    }

}
