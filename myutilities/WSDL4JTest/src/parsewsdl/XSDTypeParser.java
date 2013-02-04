package parsewsdl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

enum ParseStatus {
    ProcessingType, ProcessingOther
}

public class XSDTypeParser extends DefaultHandler {

    public static final String typeNameMarker = "TypeName";

    public static final String namespaceMarker = "Namespace";

    public static final String documentMarker = "Document";

    private CommonTypeProp current = null;

    // contains xml header and import.
    private StringBuffer commonHeader = new StringBuffer(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");

    private StringBuffer currTypeContent = new StringBuffer();

    private List<CommonTypeProp> allTypes = new ArrayList<CommonTypeProp>();

    private static final String COMPLEX_TYPE = "complexType";

    private static final String SIMPLE_TYPE = "simpleType";

    private static final String SCHEMA_NODE_NAME = "schema";

    private static final String ANNOTATION_NODE_NAME = "annotation";

    private static final String DOCUMENT_NODE_NAME = "documentation";

    private static final String NAME_ATTR = "name";

    private static final String TARGET_NAMESPACE_ATTR = "targetNamespace";

    ParseStatus status = ParseStatus.ProcessingOther;

    private String targetNamespace = null;

    private List<String> nodePath = new ArrayList<String>();

    private int documentIndex = -1;

    private String prefix = "xs";

    private String schemaEnd = null;

    public XSDTypeParser(InputStream xsdStream) {
        try {
            SAXParserFactory saxfac = SAXParserFactory.newInstance();
            SAXParser saxParser = saxfac.newSAXParser();
            saxParser.parse(xsdStream, this);

            String commonHeaderStr = commonHeader.toString();
            for (CommonTypeProp type : allTypes) {
                type.setSchemaTemplate(commonHeaderStr
                        + type.getSchemaTemplate() + schemaEnd);
            }

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

    private static boolean isTypeNode(String nodeName) {
        nodeName = getNodeName(nodeName);
        return SIMPLE_TYPE.equalsIgnoreCase(nodeName)
                || COMPLEX_TYPE.equalsIgnoreCase(nodeName);
    }

    private static boolean isSchemaNode(String nodeName) {
        return SCHEMA_NODE_NAME.equalsIgnoreCase(getNodeName(nodeName));
    }

    private static String getNodeName(String qName) {
        int index = qName.indexOf(':');
        if (index > -1) {
            qName = qName.substring(index + 1);
        }
        return qName;
    }

    private static String getPrefix(String qName) {
        int index = qName.indexOf(':');
        if (index > -1) {
            return qName.substring(0, index);
        }
        return "xs";
    }

    private boolean isTypeDocument() {
        if (nodePath.size() == 4 && isSchemaNode(nodePath.get(0))
                && isTypeNode(nodePath.get(1))) {
            return ANNOTATION_NODE_NAME.equalsIgnoreCase(getNodeName(nodePath
                    .get(2)))
                    && DOCUMENT_NODE_NAME.equalsIgnoreCase(getNodeName(nodePath
                            .get(3)));
        }
        return false;
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content = new String(ch, start, length);
        if (isTypeDocument() == true) {
            current.setDescription(content);
            appendXSD("${" + documentMarker + "}");
        } else {
            appendXSD(content);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        nodePath.remove(nodePath.size() - 1);
        if (isSchemaNode(qName) == true) {
            schemaEnd = "\r\n</" + qName + ">";
        } else {
            appendXSD("</" + qName + ">");
        }
        if ((status == ParseStatus.ProcessingType)
                && (isTypeNode(qName) == true)) {
            status = ParseStatus.ProcessingOther;
            if (current != null) {
                current.setNamespace(targetNamespace);
                if (current.getDescription() == null) {
                    current.setDescription("");
                    currTypeContent.insert(documentIndex, getDummyDocument());
                }
                current.setSchemaTemplate(currTypeContent.toString());
                currTypeContent.delete(0, currTypeContent.length());
                allTypes.add(current);
            }
            documentIndex = -1;
        }
    }

    private String getDummyDocument() {

        return "\r\n<" + prefix + ":annotation>" + "\r\n<" + prefix
                + ":documentation>" + "\r\n${" + documentMarker + "}"
                + "\r\n</" + prefix + ":documentation>" + "\r\n </" + prefix
                + ":annotation>";

    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        nodePath.add(qName);
        boolean inTypeNode = false;
        boolean inSchemaNode = false;
        if ((status == ParseStatus.ProcessingOther)
                && (isTypeNode(qName) == true)) {
            status = ParseStatus.ProcessingType;
            inTypeNode = true;
            current = new CommonTypeProp();
        }

        inSchemaNode = isSchemaNode(qName);

        if (inSchemaNode) {
            prefix = getPrefix(qName);
        }

        appendXSD("<" + qName + "");
        for (int index = 0; index < attributes.getLength(); index++) {

            String localNameAttr = attributes.getLocalName(index);
            String value = attributes.getValue(index);

            if (inTypeNode == true && NAME_ATTR.equalsIgnoreCase(localNameAttr)) {
                // replace type name with type name marker
                current.setTypeName(value);
                appendXSD(" " + localNameAttr + "=\"${" + typeNameMarker
                        + "}\" ");
            } else if (inSchemaNode == true
                    && TARGET_NAMESPACE_ATTR.equalsIgnoreCase(localNameAttr)) {
                targetNamespace = value;
                appendXSD(" " + localNameAttr + "=\"${" + namespaceMarker
                        + "}\" ");
            } else {
                appendXSD(" " + localNameAttr + "=\"" + value + "\" ");
            }
        }
        appendXSD(">");

        if (inTypeNode == true) {
            documentIndex = currTypeContent.length();
        }

    }

    private void appendXSD(String content) {
        if (status == ParseStatus.ProcessingType) {
            currTypeContent.append(content);
        } else {
            commonHeader.append(content);
        }

    }

    public List<CommonTypeProp> getAllTypes() {
        return allTypes;
    }

    public void setAllTypes(List<CommonTypeProp> allTypes) {
        this.allTypes = allTypes;
    }

}

class CommonTypeProp {

    protected String schemaTemplate;

    protected String typeName;

    protected String namespace;

    protected String description;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchemaTemplate() {
        return schemaTemplate;
    }

    public void setSchemaTemplate(String schemaTemplate) {
        this.schemaTemplate = schemaTemplate;
    }

    public void reset() {
        typeName = null;
        namespace = null;
        description = null;
        schemaTemplate = null;
    }

}
