package fr.axonic.io;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import fr.axonic.base.engine.AEntity;
import fr.axonic.io.loader.DOMToModelLoader;
import fr.axonic.visitor.InitModelPathVisitor;
import fr.axonic.visitor.ModelToDOMVisitor;

/**
 * Created by cboinaud on 14/09/2016.
 */
public class IOModel<T extends AEntity> {

    /**
     * Transforms a model (or a part of it) as an XML file
     * (destination is specified by the given stream)
     *
     * @param modelRoot
     *            : root of model to transform
     * @param stream
     *            : destination stream (console, file, etc)
     * @throws TransformerException
     */
    public void saveXML(T modelRoot, OutputStream stream) throws TransformerException {

        saveXML(modelRoot, modelRoot.getPath() == null ? "fr.axonic.maps" : modelRoot.getPath(), stream);
    }

    /**
     * Transforms a model (or a part of it) as an XML file
     * (destination is specified by the given stream)
     *
     * @param modelRoot
     *            : root of model to transform
     * @param modelRootPath
     *            : specify model's root path
     * @param stream
     *            : destination stream (console, file, etc)
     * @throws TransformerException
     */
    public void saveXML(T modelRoot, String modelRootPath, OutputStream stream) throws TransformerException {

        if (!modelRoot.equals(modelRootPath)) {
            modelRoot.setPath(modelRootPath);
        }

        InitModelPathVisitor initModelPath = new InitModelPathVisitor();
        initModelPath.visit(modelRoot);

        ModelToDOMVisitor modelToDOM = new ModelToDOMVisitor();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(modelToDOM.visit(modelRoot).getDocument());
        StreamResult result = new StreamResult(stream);

        transformer.transform(source, result);
    }

    /**
     * Initializes model following given XML input.
     * It will also load its data and its meta-data.
     *
     * @param stream
     *            : XML content as InputStream
     * @return model's root
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public T loadXML(InputStream stream) throws ParserConfigurationException, IOException, SAXException,
            IllegalAccessException, InstantiationException, ClassNotFoundException {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(trim(stream));

        // Load the model from the document
        DOMToModelLoader<T> modelLoader = new DOMToModelLoader(document);
        T model = modelLoader.getModel();

        // DataModelLoaderVisitor dataLoader = new DataModelLoaderVisitor(modelLoader.getDataValues());
        //
        //
        //
        // dataLoader.visit(model);

        return model;
    }

    /**
     * Trim XML content (to remove new lines and white space for each line)
     *
     * @param content
     *            : XML content as InputStream
     * @return trimmed XML content as InputStream
     * @throws IOException
     */
    private static InputStream trim(InputStream content) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(content));
        StringBuffer sb = new StringBuffer();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line.trim());
        }

        return new ByteArrayInputStream(sb.toString().getBytes());
    }

}
