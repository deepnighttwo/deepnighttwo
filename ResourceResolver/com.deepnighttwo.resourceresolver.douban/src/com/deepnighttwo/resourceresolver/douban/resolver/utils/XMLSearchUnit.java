package com.deepnighttwo.resourceresolver.douban.resolver.utils;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

/**
 * 
 * Represent a search task. Target could be value of a node or attribute of the
 * node. note that this is for on value only.
 * 
 * @author mzang
 */
public class XMLSearchUnit {

	// attribute values to be matched during search
	private Map<String, String> attributeMatchValidation = new HashMap<String, String>();

	// if target is an attribute, then set this member to be the attribute name.
	// if it is null or empty, then means the target is node value.
	private String expectedAttr;

	// xml path, format is: /node_name/node_name/...
	private String xmlPath;

	public XMLSearchUnit(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	/**
	 * if current node meets the search conditions or not. Meets means the path
	 * is correct and the attribute value is matched.
	 * 
	 * @param path
	 * @param attributes
	 * @return
	 */
	public boolean match(String path, Attributes attributes) {
		if (xmlPath.equals(path) == false) {
			return false;
		}

		for (String key : attributeMatchValidation.keySet()) {
			String exp = attributeMatchValidation.get(key);
			String compare = attributes.getValue(key);
			if (exp.equalsIgnoreCase(compare) == false) {
				return false;
			}
		}
		return true;
	}

	public Map<String, String> getAttributeMatchValidation() {
		return attributeMatchValidation;
	}

	public void addAttributeValidation(String key, String value) {
		attributeMatchValidation.put(key, value);
	}

	public String getXmlPath() {
		return xmlPath;
	}

	public void setAttributeMatchValidation(
			Map<String, String> attributeMatchValidation) {
		this.attributeMatchValidation = attributeMatchValidation;
	}

	public String getExpectedAttr() {
		return expectedAttr;
	}

	/**
	 * if target is node value, then set expectedAttr to null. if target is an
	 * attribute value, set it to be the attribute name.
	 * 
	 * @param expectedAttr
	 */
	public void setExpectedAttr(String expectedAttr) {
		this.expectedAttr = expectedAttr;
	}

	/**
	 * hash code can be cached if all properties are not be be changed.
	 */

	private int hashCodeCache = -1;

	@Override
	public int hashCode() {
		if (hashCodeCache != -1) {
			return hashCodeCache;
		}
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((attributeMatchValidation == null) ? 0
						: attributeMatchValidation.hashCode());
		result = prime * result
				+ ((expectedAttr == null) ? 0 : expectedAttr.hashCode());
		result = prime * result + ((xmlPath == null) ? 0 : xmlPath.hashCode());
		hashCodeCache = result;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XMLSearchUnit other = (XMLSearchUnit) obj;
		if (attributeMatchValidation == null) {
			if (other.attributeMatchValidation != null)
				return false;
		} else if (!attributeMatchValidation
				.equals(other.attributeMatchValidation))
			return false;
		if (expectedAttr == null) {
			if (other.expectedAttr != null)
				return false;
		} else if (!expectedAttr.equals(other.expectedAttr))
			return false;
		if (xmlPath == null) {
			if (other.xmlPath != null)
				return false;
		} else if (!xmlPath.equals(other.xmlPath))
			return false;
		return true;
	}

}
