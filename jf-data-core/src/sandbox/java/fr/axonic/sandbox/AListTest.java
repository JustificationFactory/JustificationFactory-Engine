package fr.axonic.sandbox;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.DatatypeConfigurationException;

import org.junit.Assert;

import fr.axonic.base.ADate;
import fr.axonic.base.ANumber;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AList;

@XmlRootElement
class TestDate {

    // @XmlElement
    private AString str;

    @XmlElement
    public ADate    to;

    {
        str = new AString("Test");
        // to = new ADate(new GregorianCalendar(2016, 2, 22));
        to = new ADate(new GregorianCalendar(2016, 2, 22));
    }

    @Override
    public String toString() {
        return to.toString();
    }
}

public class AListTest {

    public static <T> byte[] marshal(T object) throws JAXBException, IOException {
        JAXBContext context;
        context = JAXBContext.newInstance(object.getClass());
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            m.marshal(object, baos);
            System.out.println(baos.toString());
            return baos.toByteArray();
        }
    }

    public static <T> T unmarchal(byte[] bytes, Class<T> objectClass) throws JAXBException, IOException {
        T result = null;
        JAXBContext context;
        context = JAXBContext.newInstance(objectClass);
        Unmarshaller u = context.createUnmarshaller();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        result = (T) u.unmarshal(bais);
        return result;
    }

    public <T> void marchalUnmarshal(T object) {

    }

    // @Test
    // public void testListOfAString() {
    // // AString[] entities = { new AString("labelA", "AAA"), new AString("BBB"), new AString("CCC") };
    // // AList<AString> list = new AList<AString>();
    //
    // ANumber[] entities = { new ANumber("labelA", 111), new ANumber(222), new ANumber(333) };
    // AList<ANumber> list = new AList<ANumber>();
    //
    // ANumber number = new ANumber("label", 123);
    // number.setCode("myCode");
    // number.setPath("myPath");
    //
    // list.addAll(Arrays.asList(entities));
    // System.out.println(list);
    // byte[] bytes;
    // try {
    // bytes = marshal(new Electrode());
    // Electrode result = unmarchal(bytes, Electrode.class);
    // System.out.println(result);
    // } catch (JAXBException | IOException e) {
    // e.printStackTrace();
    // Assert.fail();
    // }
    //
    // }

    public static void main(String[] args) throws JAXBException, IOException, DatatypeConfigurationException {

        // List<ANumber> list = new AList<>();
        // list.add(new Integer(12));
        // list.add(new Byte((byte) 123));

        // System.out.println(XmlTestHelper.marchalUnmarshal(list;

        TestDate td = new TestDate();

        byte[] bytes = XmlTestHelper.marshal(td);
        TestDate result = XmlTestHelper.unmarshal(bytes, TestDate.class);

        System.out.println("CP1");

        System.out.println(result.to.getValue().getClass());

        if (result.to.getValue() instanceof GregorianCalendar) {
            System.out.println("OK");
        }

        ADate date = result.to;

        // XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(date.getValue());

        // GregorianCalendar cal = result.to.getValue();

        System.out.println(date.getValue().getTime());
        System.out.println(td.to.getValue().getTime());

        System.out.println(date.getValue().getTime().equals(td.to.getValue().getTime()));

    }

}
