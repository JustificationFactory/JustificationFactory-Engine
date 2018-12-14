package fr.axonic.io.loader;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AStructure;
import fr.axonic.base.engine.AVar;
import fr.axonic.util.HierarchicalElement;
import fr.axonic.visitor.InitModelPathVisitor;

/**
 * 
 * @author bgrabiec
 * @author cboinaud
 *
 * @param <T>
 */
public class DOMToModelLoader<T extends AEntity> {

    private static Logger LOGGER                   = LoggerFactory.getLogger(DOMToModelLoader.class);

    private T             model;
    private Element       root;

    private boolean       ignoreMissingXmlEntities = true;

    private static class Pair {

        private AVar   variable;
        private String value;

        public Pair(AVar variable, String value) {
            super();
            this.variable = variable;
            this.value = value;
        }

        public AVar getVariable() {
            return variable;
        }

        public void setVariable(AVar variable) {
            this.variable = variable;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    private List<Pair> variableValuePairs;

    public DOMToModelLoader(Document document)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        root = document.getDocumentElement();
        root.normalize();

        variableValuePairs = new ArrayList<>();

        String modelClassPath = root.getAttribute("type");
        Class modelClass = Class.forName(modelClassPath);
        model = (T) instantiate((AEntity) modelClass.newInstance(), root, null);

        InitModelPathVisitor visitor = new InitModelPathVisitor();

        model.setPath("fr.axonic.maps");
        visitor.visit(model);

        setValues();

    }

    private void setValues() {
        for (Pair pair : variableValuePairs) {
            AVar variable = pair.getVariable();
            String value = pair.getValue();
            AVarSetter.set(variable, value);
        }
    }

    private AEntity instantiate(final AEntity entity, Node entityElement, AStructure parent)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        if (entity.isPersistent()) {
            if (entity instanceof AStructure) {
                instantiate((AStructure) entity, entityElement, parent);
            } else if (entity instanceof AList) {
                instantiate((AList) entity, entityElement, parent);
            } else {
                instantiate((AVar) entity, entityElement, parent);
            }
        }

        return entity;
    }

    private AStructure instantiate(final AStructure structure, Node structureElement, final AStructure parent) {

        if (structureElement == null) {
            if (!isIgnoreMissingXmlEntities()) {
                throw new NullPointerException("Error in " + parent);
            } else {
                return structure;
            }
        }

        if (structureElement.hasAttributes()) {
            Node label = structureElement.getAttributes().getNamedItem("label");
            if (label != null && !label.getNodeValue().isEmpty()) {
                structure.setLabel(label.getNodeValue());
            }
        }

        if (parent != null && structure instanceof HierarchicalElement) {
            ((HierarchicalElement) structure).setParent(parent);
        }

        structure.getFieldsContainer().entrySet().forEach(entry -> {

            Node node = getElementByAttribute(structureElement, "field", entry.getKey());

            if (entry.getValue() == null) {
                throw new NullPointerException(node.toString());
            }

            try {
                instantiate(entry.getValue(), node, structure);
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                LOGGER.error(e.getMessage(), e);
            }

        });

        return structure;

    }

    private <T extends AEntity> AList<T> instantiate(final AList<T> list, Node listElement, final AStructure parent)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        if (listElement == null) {
            if (!isIgnoreMissingXmlEntities()) {
                throw new NullPointerException("Error in " + parent);
            } else {
                return list;
            }
        }

        if (listElement.hasAttributes()) {
            Node label = listElement.getAttributes().getNamedItem("label");
            if (label != null && !label.getNodeValue().isEmpty()) {
                list.setLabel(label.getNodeValue());
            }
        }

        NodeList nodeList = listElement.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {

            Node element = nodeList.item(i);
            String modelClassPath = ((Attr) element.getAttributes().getNamedItem("type")).getValue();
            Class modelClass = Class.forName(modelClassPath);

            list.add((T) instantiate((AEntity) modelClass.newInstance(), element, parent));
        }

        return list;

    }

    private AVar instantiate(final AVar var, Node varElement, final AStructure parent) {

        if (varElement == null) {
            if (!isIgnoreMissingXmlEntities()) {
                throw new NullPointerException("Error in " + parent);
            } else {
                return var;
            }
        }

        if (varElement.hasAttributes()) {
            Node label = varElement.getAttributes().getNamedItem("label");
            if (label != null && !label.getNodeValue().isEmpty()) {
                var.setLabel(label.getNodeValue());
            }
        }

        NodeList nodeList = varElement.getChildNodes();

        var.setPath(varElement.getAttributes().getNamedItem("path").getNodeValue());
        var.setCode(varElement.getNodeName());

        for (int i = 0; i < nodeList.getLength(); i++) {

            Element element = (Element) nodeList.item(i);

            if (element.getTagName().equals("value")) {

                variableValuePairs.add(new Pair(var, element.getFirstChild().getNodeValue()));

                break;
            }

        }

        return var;
    }

    public T getModel() {
        return model;
    }

    private Node getElementByAttribute(Node root, String key, String value) {

        NodeList children = root.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {

            Node element = children.item(i);

            if (element.hasAttributes()) {
                Attr val = (Attr) element.getAttributes().getNamedItem(key);
                if (value.equals(val.getValue())) {
                    return element;
                }
            }
        }

        return null;
    }

    /**
     * This variable indicates whether an exception should be thrown in case when a value is missing in the XML model.
     * In other words there is a variable in a target AEntity but there is no value for the entity in the XML.
     * 
     * @return
     */
    public boolean isIgnoreMissingXmlEntities() {
        return ignoreMissingXmlEntities;
    }

    public void setIgnoreMissingXmlEntities(boolean ignoreMissingXmlEntities) {
        this.ignoreMissingXmlEntities = ignoreMissingXmlEntities;
    }

}
