package saxparsertest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

enum SchemaProcessStage {
	NotStart, Processing, Processed
}

public class SAXParserForTypesInWSDL extends DefaultHandler {

	static StringBuffer xsd = new StringBuffer(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");

	String currentTag = "";
	String typeTag = "wsdl:types";
	String defTag = "wsdl:definitions";
	String schemaTag = "xs:schema";
	SchemaProcessStage status = SchemaProcessStage.NotStart;

	String XMLNS = "xmlns";
	StringBuffer attrInWSDL = new StringBuffer();

	public static void main(String[] args) {
		InputStream stream;
		try {
			stream = SAXParserForTypesInWSDL.class
					.getResourceAsStream("type.wsdl");
			SAXParserFactory saxfac = SAXParserFactory.newInstance();
			SAXParser saxParser = saxfac.newSAXParser();
			saxParser.parse(stream, new SAXParserForTypesInWSDL());
			log(xsd);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void log(Object obj) {
		System.out.println(obj);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		log("====characters====");
		log(new String(ch, start, length));
		if (status == SchemaProcessStage.Processing) {
			appendXSD(new String(ch, start, length));
		}
		super.characters(ch, start, length);
	}

	@Override
	public void endDocument() throws SAXException {
		log("====endDocument====");
		log("Doc end");
		super.endDocument();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		log("====endElement====");
		log("uri: " + uri);
		log("localName: " + localName);
		log("qName: " + qName);
		if (status == SchemaProcessStage.Processing) {
			appendXSD("</" + qName + ">");
		}

		if (schemaTag.equals(qName) == true) {
			status = SchemaProcessStage.Processed;
		}
		super.endElement(uri, localName, qName);
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		log("====endPrefixMapping====");
		log("prefix: " + prefix);
		super.endPrefixMapping(prefix);
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		// TODO Auto-generated method stub
		super.error(e);
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		// TODO Auto-generated method stub
		super.fatalError(e);
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.ignorableWhitespace(ch, start, length);
	}

	@Override
	public void notationDecl(String name, String publicId, String systemId)
			throws SAXException {
		log("====notationDecl====");
		log("name: " + name);
		log("publicId: " + publicId);
		log("systemId: " + systemId);
		super.notationDecl(name, publicId, systemId);
	}

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		log("====processingInstruction====");
		log("target: " + target);
		log("data: " + data);
		super.processingInstruction(target, data);
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId)
			throws IOException, SAXException {
		log("====resolveEntity====");
		log("publicId: " + publicId);
		log("systemId: " + systemId);
		return super.resolveEntity(publicId, systemId);
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
		super.setDocumentLocator(locator);
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		log("====skippedEntity====");
		log("name: " + name);
		super.skippedEntity(name);
	}

	@Override
	public void startDocument() throws SAXException {
		log("====startDocument====");
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		log("====startElement====");
		log("uri: " + uri);
		log("localName: " + localName);
		log("qName: " + qName);
		StringBuffer buffer = new StringBuffer();
		boolean inDefStart = defTag.equals(qName);
		boolean inSchema = schemaTag.equals(qName);
		if (inSchema == true) {
			status = SchemaProcessStage.Processing;
		}
		if (status == SchemaProcessStage.Processing) {
			appendXSD("<" + qName + "");
		}

		for (int index = 0; index < attributes.getLength(); index++) {
			String str1 = "\r\n\tLocalName:" + attributes.getLocalName(index);
			String str2 = "\r\n\tQName:" + attributes.getQName(index);
			String str3 = "\r\n\tType:" + attributes.getType(index);
			String str4 = "\r\n\tURI:" + attributes.getURI(index);
			String str5 = "\r\n\tValue:" + attributes.getValue(index);
			buffer.append("\r\n=====\r\n").append(str1).append(str2)
					.append(str3).append(str4).append(str5)
					.append("\r\n=====\r\n");
			String localNameAttr = attributes.getLocalName(index);
			if (inDefStart) {
				if (localNameAttr.toLowerCase().contains(XMLNS)) {
					attrInWSDL.append(" " + localNameAttr + "=\""
							+ attributes.getValue(index) + "\" ");
				}
			} else if (inSchema) {
				appendXSD(" " + localNameAttr + "=\""
						+ attributes.getValue(index) + "\" ");
			} else if (status == SchemaProcessStage.Processing) {
				appendXSD(" " + localNameAttr + "=\""
						+ attributes.getValue(index) + "\" ");
			}
		}
		if (inSchema == true && attrInWSDL != null) {
			appendXSD(attrInWSDL.toString());
			appendXSD(">");
		}
		if ((inSchema == false) && status == SchemaProcessStage.Processing) {
			appendXSD(">");
		}
		super.startElement(uri, localName, qName, attributes);
	}

	public void appendXSD(String content) {
		if (status == SchemaProcessStage.Processing) {
			xsd.append(content);
		}
	}

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		log("====startPrefixMapping====");
		log("prefix: " + prefix);
		log("uri: " + uri);
		super.startPrefixMapping(prefix, uri);
	}

	@Override
	public void unparsedEntityDecl(String name, String publicId,
			String systemId, String notationName) throws SAXException {
		log("====unparsedEntityDecl====");
		log("name: " + name);
		log("publicId: " + publicId);
		log("systemId: " + systemId);
		log("notationName: " + notationName);
		super.unparsedEntityDecl(name, publicId, systemId, notationName);
	}

	@Override
	public void warning(SAXParseException e) throws SAXException {
		// TODO Auto-generated method stub
		super.warning(e);
	}

}
