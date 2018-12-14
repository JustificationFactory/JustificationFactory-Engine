package fr.axonic.sandbox;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * This class serves to test XML serialization and deserialization
 * 
 * @author bgrabiec
 *
 */
public class XmlTestHelper {

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

    public static <T> T unmarshal(byte[] bytes, Class<T> objectClass) throws JAXBException, IOException {
        T result = null;
        JAXBContext context;
        context = JAXBContext.newInstance(objectClass);
        Unmarshaller u = context.createUnmarshaller();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        result = (T) u.unmarshal(bais);
        System.out.println(result);
        return result;
    }

    public static <T> boolean marchalUnmarshal(T object) throws JAXBException, IOException {
        byte[] bytes = marshal(object);
        Object result = unmarshal(bytes, object.getClass());
        return object.equals(result);
    }

}
