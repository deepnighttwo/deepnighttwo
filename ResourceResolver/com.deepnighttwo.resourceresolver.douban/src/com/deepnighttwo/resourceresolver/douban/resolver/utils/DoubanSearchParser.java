package com.deepnighttwo.resourceresolver.douban.resolver.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * SAXP parser working with XMLSearchUnit.
 * 
 * @author mzang
 */

public class DoubanSearchParser extends DefaultHandler {

	// create and initial search units
	public static final XMLSearchUnit DETAILS_LINK_API_PATH = new XMLSearchUnit(
			"/feed/entry/id");

	public static final XMLSearchUnit DETAILS_CONTENT_PATH = new XMLSearchUnit(
			"/entry/summary");

	public static final XMLSearchUnit DETAILS_TITLE_PATH = new XMLSearchUnit(
			"/entry/title");

	public static final XMLSearchUnit DETAILS_CHINESE_NAME_PATH = new XMLSearchUnit(
			"/entry/db:attribute");

	public static final XMLSearchUnit DETAILS_RATINGE_PATH = new XMLSearchUnit(
			"/entry/gd:rating");

	public static final XMLSearchUnit DETAILS_RATINGE_RATER_COUNT_PATH = new XMLSearchUnit(
			"/entry/gd:rating");

	public static final XMLSearchUnit DETAILS_LINK_URL_PATH = new XMLSearchUnit(
			"/feed/entry/link");

	static {
		DETAILS_LINK_URL_PATH.addAttributeValidation("rel", "alternate");
		DETAILS_LINK_URL_PATH.setExpectedAttr("href");

		DETAILS_CHINESE_NAME_PATH.addAttributeValidation("lang", "zh_CN");
		DETAILS_CHINESE_NAME_PATH.addAttributeValidation("name", "aka");

		DETAILS_RATINGE_PATH.setExpectedAttr("average");

		DETAILS_RATINGE_RATER_COUNT_PATH.setExpectedAttr("numRaters");

	}

	// a map to store the XMLSearchUnit and value
	private Map<XMLSearchUnit, String> results = new HashMap<XMLSearchUnit, String>();

	// a counter of search unit. if it is 0, then all search unit finds a match
	// value and the result of the XML will be skipped.
	private int count = 0;

	private StringBuilder path = new StringBuilder();

	private static final String pathSeparater = "/";

	private XMLSearchUnit[] searchUnits;

	List<XMLSearchUnit> foundItems = new ArrayList<XMLSearchUnit>();

	/**
	 * constructor, accept XML input stream, 0 or more search unit instances.
	 * 
	 * @param input
	 * @param expectedPath
	 * @return
	 */
	public Map<XMLSearchUnit, String> parseResults(InputStream input,
			XMLSearchUnit... expectedPath) {
		for (XMLSearchUnit search : expectedPath) {
			results.put(search, null);
		}

		searchUnits = expectedPath;

		count = expectedPath.length;

		XMLReader xmlReader = null;
		try {
			SAXParserFactory spfactory = SAXParserFactory.newInstance();
			spfactory.setValidating(false);
			SAXParser saxParser = spfactory.newSAXParser();
			xmlReader = saxParser.getXMLReader();
			xmlReader.setContentHandler(this);
			xmlReader.parse(new InputSource(input));
		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
		return results;
	}

	private void addToPath(String addPath) {
		path.append(pathSeparater).append(addPath.toLowerCase());
	}

	private void popPath() {
		int index = path.lastIndexOf(pathSeparater);
		// String removedPath = path.substring(index);
		path.delete(index, path.length());
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		foundItems.clear();
		if (count == 0) {
			return;
		}

		// update path
		addToPath(qName);

		List<XMLSearchUnit> foundAttrItems = null;

		// check if current node matches search units. if it is a node value
		// search, then store it in a member variable named foundItems because
		// the value of the node is known only when reaches the end of the
		// node.but for attribute search, it value is known here. So then are
		// put in a local variable list named foundAttrItems.
		for (XMLSearchUnit unit : searchUnits) {
			if (unit.match(path.toString(), attributes) == true) {

				if (unit.getExpectedAttr() == null) {
					foundItems.add(unit);
				} else {
					if (foundAttrItems == null) {
						foundAttrItems = new ArrayList<XMLSearchUnit>();
					}
					foundAttrItems.add(unit);
				}
			}
		}
		// if no attribute match, return.
		if (foundAttrItems == null) {
			return;
		}

		// fill search unit value using attribute value. update count.
		for (XMLSearchUnit attrUnit : foundAttrItems) {
			String attrValue = attributes.getValue(attrUnit.getExpectedAttr());
			if (results.get(attrUnit) == null) {
				count--;
			}
			results.put(attrUnit, attrValue);
			count--;
		}
	}

	/**
	 * if current node matches, the the node value is useful, store it.
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (count == 0) {
			return;
		}
		if (foundItems.size() == 0) {
			return;
		}

		for (XMLSearchUnit unit : foundItems) {
			String content = new String(ch, start, length);
			if (results.get(unit) == null) {
				count--;
			}
			results.put(unit, content);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		foundItems.clear();
		if (count == 0) {
			return;
		}
		popPath();
	}
}
