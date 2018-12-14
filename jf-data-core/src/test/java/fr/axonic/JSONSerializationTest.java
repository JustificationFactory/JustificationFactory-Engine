package fr.axonic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import fr.axonic.base.ANumber;
import fr.axonic.base.engine.AStructure;
import fr.axonic.base.engine.AVar;
import org.junit.Before;
import org.junit.Test;
import sun.security.x509.AVA;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by cduffau on 21/08/17.
 */
public class JSONSerializationTest {

    private ObjectMapper mapper;


    @Before
    public void setup(){
        mapper = new ObjectMapper();
        AnnotationIntrospector aiJaxb = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        AnnotationIntrospector aiJackson = new JacksonAnnotationIntrospector();
        // first Jaxb, second Jackson annotations
        mapper.setAnnotationIntrospector(AnnotationIntrospector.pair(aiJaxb, aiJackson));

    }

    @Test
    public void aVarTest() throws IOException {
        AVar aVar=new ANumber(10);
        String json = mapper.writeValueAsString(aVar);
        System.out.println(json);
        AVar var=mapper.readValue(json, ANumber.class);
        assertEquals(aVar,var);

    }

    @Test
    public void aStructureTest() throws IOException {
        AStructure aStructure=new AStructureTest();
        String json = mapper.writeValueAsString(aStructure);
        System.out.println(json);
        AStructure struct=mapper.readValue(json, AStructureTest.class);
        assertEquals(aStructure,struct);

    }
}
