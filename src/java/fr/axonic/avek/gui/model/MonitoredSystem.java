package fr.axonic.avek.gui.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Nathaël N on 29/06/16.
 */

@XmlRootElement(name="MonitoredSystem")
public class MonitoredSystem {
	@XmlElement(name="id")
	private int id;

	@XmlElementWrapper(name="categories")
	private final LinkedHashMap<String, LinkedHashSet<AVar>> categories;

	public MonitoredSystem() {
		this.categories = new LinkedHashMap<>();
	}
	public MonitoredSystem(int id) {
		this();
		this.id = id;
	}

	public LinkedHashSet<AVar> put(String key, LinkedHashSet<AVar> value) {
		return categories.put(key, value);
	}

	public static void main(String[] args) throws JAXBException {
		String json = null;
		//Gson gson = new Gson();//new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
		ObjectMapper mapper = new ObjectMapper();
		AnnotationIntrospector introspector = new JacksonAnnotationIntrospector();
		mapper.setAnnotationIntrospector(introspector);

		{
			MonitoredSystem ms = new MonitoredSystem(42);

			LinkedHashSet<AVar> l = new LinkedHashSet<AVar>();
			l.add(new AVar("size", Double.class, 123.45, "cm"));
			l.add(new AVar("weight", Double.class, 67.89, "kg"));

			Calendar c = Calendar.getInstance();
			c.set(1994, 10, 10);
			l.add(new AVar("birth", String.class,
					new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ").format(c.getTime())));
			ms.put("Static data", l);

			try {
				json = mapper.writeValueAsString(ms);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			//json = gson.toJson(ms); // TODO This is for a test only
			System.out.println(json);
		}

		System.out.println("-----------------------------------------------------");

		{
			MonitoredSystem ms2 = null;
			try {
				ms2 = mapper.readValue(json, MonitoredSystem.class);
			} catch (IOException e) {
				e.printStackTrace();
			}

			//MonitoredSystem ms2 = gson.fromJson(json, MonitoredSystem.class);
			System.out.println(ms2);

			for (String k : ms2.keySet()) {
				System.out.println("-- "+k+" --");
				for (AVar av : ms2.get(k))
				try {
					System.out.println("\t" + av.getKey() + ": " + (av.getValueType().cast(av.getValue())));
				}catch(ClassCastException c) {
					try{Thread.sleep(1);}catch(Exception ex){}
					System.err.println("\t"+av.getKey()+": "+av.getValue()+"\n");
					c.printStackTrace();
					System.err.println();
					try{Thread.sleep(1);}catch(Exception ex){}
				}
			}
		}

	}

	private LinkedHashSet<AVar> get(String birth) {
		return categories.get(birth);
	}
	private Set<String> keySet() {
		return categories.keySet();
	}

	@Override
	public String toString() {
		return "MonitoredSystem:{"+this.id+","+this.categories +"}";
	}
}
