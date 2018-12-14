package fr.axonic.visitor;

import fr.axonic.base.ABoolean;
import fr.axonic.base.ANumber;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AStructure;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import static org.junit.Assert.*;

/**
 * Created by cboinaud on 14/09/2016.
 */
public class ModelToDOMVisitorTest {

    private ModelToDOMVisitor visitor;

    @Before
    public void cleanUp(){
        visitor = new ModelToDOMVisitor();
    }

    @After
    public void tearDown(){
        visitor = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void aVarNonInitializedVisitingTest(){
        ANumber number = new ANumber(10);
        visitor.visit(number);
    }

    @Test
    public void aVarVisitingTest(){
        ANumber number = new ANumber(10);
        number.setCode("myNumber");
        number.setPath("myPath");

        Document document = visitor.getDocument();
        assertFalse(document.hasChildNodes());

        document = visitor.visit(number).getDocument();

        assertEquals(document.getChildNodes().getLength(), 1);
        assertEquals(document.getFirstChild().getNodeName(), "myNumber");

        assertEquals(document.getFirstChild().getFirstChild().getNodeName(), "value");
        assertEquals(document.getFirstChild().getFirstChild().getFirstChild().getNodeValue(), "10");

        assertTrue(document.getFirstChild().hasAttributes());
        assertEquals(document.getFirstChild().getAttributes().getNamedItem("path").getNodeValue(), "myPath");
    }

    @Test(expected = IllegalArgumentException.class)
    public void aListNonInitializedVisitingTest(){
        AList<ANumber> list = new AList<>();
        visitor.visit(list);
    }

    @Test
    public void aListVisitingTest(){
        AList<ANumber> list = new AList<>();
        list.setCode("list");
        list.setPath("aPath");

        for(int i = 0; i < 5; i++){
            ANumber number = new ANumber(i);
            number.setPath("list");
            number.setCode("element"+i);

            list.add(number);
        }

        Document document = visitor.visit(list).getDocument();

        assertEquals(document.getChildNodes().getLength(), 1);
        assertEquals(document.getFirstChild().getNodeName(), "list");
        assertTrue(document.getFirstChild().hasAttributes());

        assertEquals(document.getFirstChild().getChildNodes().getLength(), 5);

        for(int i = 0; i < document.getFirstChild().getChildNodes().getLength() ; i++){
            Node element = document.getFirstChild().getChildNodes().item(i);

            assertTrue(element.hasAttributes());
            assertEquals(element.getNodeName(), "element"+i);
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void aStructureNonInitializedVisitingTest(){
        TestStructure structure = new TestStructure();
        visitor.visit(structure);
    }

    @Test
    public void aStructureVisitingTest(){

        TestStructure structure = new TestStructure();
        structure.setCode("structure");
        structure.setPath("root");

        Document document = visitor.visit(structure).getDocument();

        assertEquals(document.getChildNodes().getLength(), 1);

        Node mainStructure = document.getFirstChild();

        NamedNodeMap attributes = mainStructure.getAttributes();
        assertEquals(attributes.getLength(), 2);
        assertEquals(attributes.getNamedItem("path").getNodeValue(), "root");
        assertEquals(attributes.getNamedItem("type").getNodeValue(), TestStructure.class.getName());

        assertEquals(mainStructure.getChildNodes().getLength(), 4);

        Node subStructure = mainStructure.getChildNodes().item(1);

        assertEquals(subStructure.getNodeName(), "subStructure");
        assertEquals(subStructure.getChildNodes().getLength(), 2);

        attributes = subStructure.getAttributes();
        assertEquals(attributes.getLength(), 3);
        assertEquals(attributes.getNamedItem("path").getNodeValue(), "root.structure");
        assertEquals(attributes.getNamedItem("type").getNodeValue(), TestSubStructure.class.getName());
        assertEquals(attributes.getNamedItem("field").getNodeValue(), "myStructure");
    }

    class TestStructure extends AStructure {

        public ANumber          myNumber;
        public AString          myString;
        public TestSubStructure myStructure;
        public ABoolean         myBoolean;

        public TestStructure() {
            myNumber = new ANumber(10);
            myNumber.setCode("number");
            myNumber.setPath("root.structure");

            myString = new AString("hello");
            myString.setCode("string");
            myString.setPath("root.structure");

            myStructure = new TestSubStructure();
            myStructure.setCode("subStructure");
            myStructure.setPath("root.structure");

            myBoolean = new ABoolean(false);
            myBoolean.setCode("boolean");
            myBoolean.setPath("root.structure");

            init();
        }
    }

    class TestSubStructure extends AStructure {

        public ANumber myNumber;
        public AString myString;

        public TestSubStructure(){
            myNumber = new ANumber(5);
            myNumber.setCode("number");
            myNumber.setPath("root.structure.subStructure");

            myString = new AString("world");
            myString.setCode("string");
            myString.setPath("root.structure.subStructure");

            init();
        }
    }

}
