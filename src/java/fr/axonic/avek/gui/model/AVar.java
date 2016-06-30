package fr.axonic.avek.gui.model;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Nathaël N on 29/06/16.
 */
public class AVar {
	@XmlElement
	private String key;

	@XmlElement(name="type")
	private String valueType;

	@XmlElement
	private Object value;

	@XmlElement(name="unit",required=false)
	private String unit;

	public AVar() {}
	public AVar(String key, Class valueType, Object value) {
		this(key, valueType, value, null);
	}
	public AVar(String key, Class valueType, Object value, String unit) {
		this.key = key;
		this.valueType = valueType.getName();
		this.value = valueType.cast(value); // verify class validity
		this.unit = unit;
	}

	public String getKey() {
		return key;
	}

	public Class getValueType() {
		try {
			return Class.forName(valueType);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString()+(unit==null?"":(" "+unit));
	}
}
