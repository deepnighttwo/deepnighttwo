package wsdltypehandler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CutXSDFromWSDL extends DefaultHandler {

	// this can be removed later, maybe. the path of wsdl file.
	String path = null;

	public void cutWSDL(String wsdlPath) throws SAXException, IOException,
			ParserConfigurationException {
		path = wsdlPath;
		SAXParserFactory saxfac = SAXParserFactory.newInstance();
		saxfac.setNamespaceAware(false);
		saxfac.setXIncludeAware(true);
		saxfac.setValidating(false);
		SAXParser saxParser = saxfac.newSAXParser();
		saxParser.parse(wsdlPath, this);
	}

	// variables that need to be clear up once a type is processed.
	private StringBuilder xsdContent = new StringBuilder();

	private String typeName = null;

	private String tlName = null;

	private String tlNamespace = null;

	private String documentation = "";

	private boolean acceptContent = false;

	private Set<String> dependencies = new HashSet<String>();

	private Map<String, String> freemarkerReplacement = new HashMap<String, String>();

	// variables for all types in the same schema node.
	private Map<String, String> nsMappingSchema = new HashMap<String, String>();

	private String targetNamespace = null;

	// variables for all types in current wsdl.
	private Map<String, TypeModel> xsds = new HashMap<String, TypeModel>();

	private Map<String, String> nsMappingWSDL = new HashMap<String, String>();

	private Map<String, String> tpyeAttrs = new HashMap<String, String>();

	private List<String> xmlPath = new ArrayList<String>();

	private int noTypeNameCounter = 0;

	// node names
	private static String WSDL_DEF = "wsdl:definitions";

	private static String WSDL_TYPE_DEF = "wsdl:types";

	private static String WSDL_SCHEMA_DEF = "xs:schema";

	private static String COMPLEX_TYPE_NODE = "xs:complexType";

	private static String SIMPLE_TYPE_NODE = "xs:simpleType";

	private static String ANNOTATION_NODE = "xs:annotation";

	private static String TYPE_DOCUMENTATION_NODE = "xs:documentation";

	private static String APPINFO_NODE = "xs:appinfo";

	private static String TL_SOURCE_NODE = "typeLibrarySource";

	// attribute names
	private static String ATTR_TARGET_NS_LB = "targetNamespace";

	private static String TYPE_LIBRARY_ATTR = "library";

	private static String TYPE_NS_ATTR = "namespace";

	private static String TYPE_NAME_ATTR = "name";

	private static String TYPE_BASE_ATTR = "base";

	private static String TYPE_TYPE_ATTR = "type";

	// namespace in attribtue value
	private static String XS_NS = "xs:";
	private static String XML_NS = "xmlns:";

	// post process type variables
	// keys needed to resolve for free marker replacement
	private static final String EXTERNAL_NS_KEY = "EXTERNAL_NS";
	private static final String TARGET_NS_KEY = "EXTERNAL_NS";

	private static final String NAMESPACE_KEY = "NAMESPACE";
	private static final String TYPELIB_KEY = "TYPE_LIB";
	private static final String TYPE_NAME_KEY = "TYPE_NAME";

	private static final String W3C_TYPE_NS = "http://www.w3.org/[0-9]{4}/XMLSchema";

	private static final Pattern W3C_TYPE_NS_PATTERN = Pattern.compile(
			W3C_TYPE_NS, Pattern.CASE_INSENSITIVE);

	// import statement and included statement.
	private static final String TYPE_INCLUDE = "\r\n\t<xs:include schemaLocation=\"typelib://{0}//{1}.xsd\" />";
	private static final String TYPE_IMPORT = "\r\n\t<xs:import namespace=\"{0}\" \r\n"
			+ "\t\tschemaLocation=\"typelib://{1}//{2}.xsd\" />";

	private String getNameForNoTypeWithoutName() {
		noTypeNameCounter++;
		return "NoName" + noTypeNameCounter;
	}

	private boolean isWSDLNode() {
		if (xmlPath.size() != 1) {
			return false;
		}
		return WSDL_DEF.equals(xmlPath.get(0));
	}

	private boolean isSchemaNode() {
		if (xmlPath.size() != 3) {
			return false;
		}
		return WSDL_DEF.equals(xmlPath.get(0))
				&& WSDL_TYPE_DEF.equals(xmlPath.get(1))
				&& WSDL_SCHEMA_DEF.equals(xmlPath.get(2));
	}

	// private boolean isInSchemaNode() {
	// if (xmlPath.size() <= 3) {
	// return false;
	// }
	// return WSDL_DEF.equals(xmlPath.get(0))
	// && WSDL_TYPE_DEF.equals(xmlPath.get(1))
	// && WSDL_SCHEMA_DEF.equals(xmlPath.get(2));
	// }

	private boolean isTypeNode() {
		if (xmlPath.size() != 4) {
			return false;
		}
		return WSDL_DEF.equals(xmlPath.get(0))
				&& WSDL_TYPE_DEF.equals(xmlPath.get(1))
				&& WSDL_SCHEMA_DEF.equals(xmlPath.get(2))
				&& (COMPLEX_TYPE_NODE.equals(xmlPath.get(3)) || SIMPLE_TYPE_NODE
						.equals(xmlPath.get(3)));
	}

	private boolean isInTypeNode() {
		if (xmlPath.size() < 5) {
			return false;
		}
		return WSDL_DEF.equals(xmlPath.get(0))
				&& WSDL_TYPE_DEF.equals(xmlPath.get(1))
				&& WSDL_SCHEMA_DEF.equals(xmlPath.get(2))
				&& (COMPLEX_TYPE_NODE.equals(xmlPath.get(3)) || SIMPLE_TYPE_NODE
						.equals(xmlPath.get(3)));
	}

	private boolean isTypeLibrarySourceNode() {
		if (xmlPath.size() != 7) {
			return false;
		}
		return WSDL_DEF.equals(xmlPath.get(0))
				&& WSDL_TYPE_DEF.equals(xmlPath.get(1))
				&& WSDL_SCHEMA_DEF.equals(xmlPath.get(2))
				&& (COMPLEX_TYPE_NODE.equals(xmlPath.get(3)) || SIMPLE_TYPE_NODE
						.equals(xmlPath.get(3)))
				&& ANNOTATION_NODE.equals(xmlPath.get(4))
				&& APPINFO_NODE.equals(xmlPath.get(5))
				&& TL_SOURCE_NODE.equals(xmlPath.get(6));
	}

	private boolean isTypeDocumentationNode() {
		if (xmlPath.size() != 6) {
			return false;
		}
		return WSDL_DEF.equals(xmlPath.get(0))
				&& WSDL_TYPE_DEF.equals(xmlPath.get(1))
				&& WSDL_SCHEMA_DEF.equals(xmlPath.get(2))
				&& (COMPLEX_TYPE_NODE.equals(xmlPath.get(3)) || SIMPLE_TYPE_NODE
						.equals(xmlPath.get(3)))
				&& ANNOTATION_NODE.equals(xmlPath.get(4))
				&& TYPE_DOCUMENTATION_NODE.equals(xmlPath.get(5));
	}

	private boolean isTypeDependencyRelated(String attrName, String attrValue) {
		if (TYPE_BASE_ATTR.equals(attrName) == false
				&& TYPE_TYPE_ATTR.equals(attrName) == false) {
			return false;
		}

		return true;
	}

	private static boolean isBasicTypeSchemaNamespace(String namespace) {
		Matcher mather = W3C_TYPE_NS_PATTERN.matcher(namespace);
		return mather.matches();
	}

	private boolean isInW3CBasicSchemaTypeNamespace(String typeQName)
			throws SAXException {
		String[] qName = typeQName.split(":");
		if (qName.length != 2) {
			throw new SAXException("Invalidated type:" + typeQName);
		}
		String namespace = nsMappingSchema.get(XML_NS + qName[0]);
		return isBasicTypeSchemaNamespace(namespace);
	}

	private boolean isInTypeLibrary(String typeQName) throws SAXException {
		String[] qName = typeQName.split(":");
		if (qName.length != 2) {
			throw new SAXException("Invalidated type:" + typeQName);
		}
		String namespace = nsMappingSchema.get(XML_NS + qName[0]);
		return findTypeInTypeLibrary(namespace, qName[1]) != null;
	}

	private Object findTypeInTypeLibrary(String namespace, String typeName) {
		return null;
	}

	// fill EXTERNAL_NS_KEY with all external type namespace. fill TARGET_NS_KEY
	// with the real type library namespace
	private static final String COMMON_TYPE_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
			+ "<xs:schema "
			+ "{0}\r\n"
			+ "\t attributeFormDefault=\"unqualified\" elementFormDefault=\"qualified\" "
			+ "targetNamespace=\"{1}\" version=\"1.0.0\">\r\n";

	/**
	 * key part of this method is to handle dependency. there are four kinds of
	 * dependencies. 1) Using a standard type from W3C schema type. How to check
	 * - check the namespace value is W3C_TYPE_NS or not. How to handle - For
	 * this kind of dependency, just adding a xmlns definition attribute to the
	 * xs:schema node. 2) Using a type library type. How to check - using the
	 * namespace and type name to try to find such a type in type library. If
	 * found, then it is a dependency to a type library type. Otherwise, it is
	 * not a type library dependency type. How to handle - for a TL type
	 * dependency, need to add a xs:import statement. TYPE_IMPORT is provided to
	 * use. just provide the type library name and the type name. also need to
	 * adding a xmlns definition attribute to the xs:schema node. 3) An internal
	 * type dependency. How to check - if 2) not match and the namespace of that
	 * dependency type is the same as the namespace of current type, so it must
	 * be an internal dependency. How to handle - for internal dependency, there
	 * are two more thing to handle. First, the name of the dependency type may
	 * be changed. Second, the namespace of the dependency type may changed (in
	 * fact, must change). So for the xsd content, the attribute where used the
	 * dependency type, must use "${}" and waiting to be replaced (already use
	 * it when adding content to XSD). For the XSD header, also should use "${}"
	 * and waiting to be replaced. 4) An internal dependency which in fact is
	 * and external type library dependency. How to check - find the dependency
	 * from xsds map and method isTypeLibraryType returns true. How to handle -
	 * first, it is a type library dependency, so there is no need to using free
	 * marker because the type name will never change. Set the type name value
	 * to be it self. Then need to handle the namespace part because the
	 * namespace part is incorrect.
	 * 
	 * 
	 * Notice that we need to check 2) first because a type may have the same
	 * namespace with an existing type library. just like a class named MyClass
	 * in java.lang package.
	 */
	private void postProcessTypes() throws SAXException {
		Set<String> keys = xsds.keySet();
		for (String key : keys) {
			TypeModel model = xsds.get(key);

			if (model.isNeedToImport() == false) {
				continue;
			}

			String content = model.getTypeContent();
			StringBuilder header = new StringBuilder();

			Map<String, String> typeNSMapping = model.getNsMappingSchema();

			// key-value for free marker mapping
			Map<String, String> freeMarkerMapping = model
					.getFreemarkerReplacement();

			// the following Collection instance using set to avoid duplication
			// all xmlns attributes for current type
			Set<String> xmlnsAttrs = new HashSet<String>();

			// all include node for current type
			Set<String> includeNodes = new HashSet<String>();

			// all importNode for current type
			Set<String> importNodes = new HashSet<String>();

			// key-value for namespace and xmlns, just for case 4)
			Map<String, String> namespaceToXMLNS = new HashMap<String, String>();

			for (String dep : model.getDependencies()) {
				String[] qName = dep.split(":");
				// in fact, before an dependency is added ,this is already
				// checked.
				if (qName.length != 2) {
					throw new SAXException("Invalidated type:" + dep);
				}
				String nsShort = qName[0];
				String shortNSDefinition = XML_NS + nsShort;

				String depNamespace = typeNSMapping.get(shortNSDefinition);
				String depTypeName = qName[1];

				// handle case 1), add ns definition
				if (isBasicTypeSchemaNamespace(depNamespace) == true) {
					xmlnsAttrs.add("\r\n\t" + shortNSDefinition + "=\""
							+ depNamespace + "\"");
					namespaceToXMLNS.put(depNamespace, shortNSDefinition);
					continue;
				}

				// handle case 2), add import and xmlns attributes.
				Object typeLibratyType = this.findTypeInTypeLibrary(
						depNamespace, depTypeName);

				if (typeLibratyType != null) {
					xmlnsAttrs.add("\r\n\t" + shortNSDefinition + "=\""
							+ depNamespace + "\"");
					String importNode = MessageFormat.format(TYPE_IMPORT,
							depNamespace, "TYPE_LIBRARY_NAME", depTypeName);
					importNodes.add(importNode);

					namespaceToXMLNS.put(depNamespace, shortNSDefinition);
					continue;
				}

				// handle case 3)
				TypeModel depInternalType = xsds.get(depNamespace + ":"
						+ depTypeName);
				if (model.getNamespace().equals(depNamespace)
						&& (depInternalType != null)
						&& (depInternalType.isNeedToImport() == true)) {
					xmlnsAttrs.add("\r\n\t" + shortNSDefinition + "=\""
							+ depNamespace + "\"");
					String includeNode = MessageFormat.format(TYPE_INCLUDE,
							"${" + TYPELIB_KEY + "}", "${" + depTypeName + "}");
					includeNodes.add(includeNode);

					namespaceToXMLNS.put(depNamespace, shortNSDefinition);
					continue;
				}

				if (model.getNamespace().equals(depNamespace)
						&& (depInternalType != null)
						&& (depInternalType.isTypeLibraryType() == true)) {
					// FIXME: check if this type do exists in type library
					String typelibNamespace = depInternalType
							.getTypelibNamespace();
					String xmlns = namespaceToXMLNS.get(typelibNamespace);
					if (xmlns == null) {
						xmlns = createNewXMLNS(namespaceToXMLNS.values());
						xmlnsAttrs.add("\r\n\t" + xmlns + "=\""
								+ typelibNamespace + "\"");
					}

					String nsPart = xmlns.substring(xmlns.lastIndexOf(':') + 1);

					// change the type to the real type in type library.
					freeMarkerMapping.put(dep, nsPart + ":" + depTypeName);

					String importNode = MessageFormat.format(TYPE_IMPORT,
							typelibNamespace, "TYPE_LIBRARY_NAME",
							depInternalType.getTypeName());
					importNodes.add(importNode);

					continue;
				}

				System.out.println("Unhandled dependency for type :"
						+ model.getTypeName() + ". Dependency not found for "
						+ dep);

			}

			StringBuilder xmlnsString = new StringBuilder();

			for (String attr : xmlnsAttrs) {
				xmlnsString.append(attr);
			}

			String schemaNode = MessageFormat.format(COMMON_TYPE_HEADER,
					xmlnsString.toString(), "${TARGET_NS_KEY}");

			StringBuilder importIncludeString = new StringBuilder();
			for (String includeStr : includeNodes) {
				importIncludeString.append(includeStr);
			}

			for (String importStr : importNodes) {
				importIncludeString.append(importStr);
			}

			String fullContent = schemaNode + importIncludeString + "\r\n"
					+ model.getTypeContent();

			model.setTypeContent(fullContent);
		}
	}

	public String createNewXMLNS(Collection<String> existingXMLNS) {
		int counter = 1;
		String startXMLNS = XML_NS + "tns";
		while (true) {
			String xmlns = startXMLNS + counter;
			if (existingXMLNS.contains(xmlns) == false) {
				return xmlns;
			}
			counter++;
		}
	}

	@Override
	public void endDocument() throws SAXException {
		postProcessTypes();
		File file = new File(path);
		File parentFile = file.getParentFile();
		String wsdlFileName = file.getName();
		wsdlFileName = wsdlFileName.substring(0, wsdlFileName.lastIndexOf('.'));
		File xsdFolder = new File(parentFile, wsdlFileName);
		if (xsdFolder.exists() == false || xsdFolder.isDirectory() == false) {
			boolean createFolder = xsdFolder.mkdir();
			if (createFolder == false) {
				throw new SAXException("Unable to create folder:" + xsdFolder);
			}
		}

		Set<String> keys = xsds.keySet();
		for (String key : keys) {
			String fileName = key.substring(key.lastIndexOf(':') + 1);
			File xsdFile = new File(xsdFolder, fileName + ".xsd");
			TypeModel model = xsds.get(key);
			try {
				if (xsdFile.exists() == true && xsdFile.isFile() == true) {
					xsdFile.delete();
				}
				xsdFile.createNewFile();
				PrintWriter pw = new PrintWriter(xsdFile);
				pw.write(model.getTypeContent());
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		xmlPath.add(qName);

		// current node is wsdl:definitions
		if (isWSDLNode() == true) {
			// store the namespace attributes in the nsMappingWSDL
			for (int index = 0; index < attributes.getLength(); index++) {
				String attrName = attributes.getQName(index);
				if (attrName.toLowerCase().startsWith((XML_NS))) {
					nsMappingWSDL.put(attrName, attributes.getValue(index));
				}
			}
			return;
		}

		// current node is xs:schema
		if (isSchemaNode() == true) {
			// first, put all NS mappings of WSDL to schema NS mapping.
			nsMappingSchema.putAll(nsMappingWSDL);
			for (int index = 0; index < attributes.getLength(); index++) {
				String attrName = attributes.getQName(index);
				if (attrName.toLowerCase().startsWith((XML_NS))) {
					/*
					 * store the namespace attributes in the nsMappingSchema.
					 * this is after putting ns attrs into nsMappingWSDL. so if
					 * there are any duplicate ns definitions, the one in type
					 * node will be used.
					 */
					nsMappingSchema.put(attrName, attributes.getValue(index));
				} else {
					// put other attributes into tpyeAttrs map.
					tpyeAttrs.put(attrName, attributes.getValue(index));
				}
			}
			targetNamespace = tpyeAttrs.get(ATTR_TARGET_NS_LB);
			return;
		}

		// current node is a type definition node
		if (isTypeNode() == true) {
			// begin to add whatever content to xsdContent. this is used in
			// character method.
			acceptContent = true;
			// extract type name from attribute
			for (int index = 0; index < attributes.getLength(); index++) {
				String attrName = attributes.getQName(index);
				String attrValue = attributes.getValue(index);
				if (TYPE_NAME_ATTR.equals(attrName)) {
					// extract type name attribute value and store it to
					// typeName
					typeName = attrValue;
				}
			}
			// if there is no type name, just use a default type name;
			if (typeName == null) {
				typeName = this.getNameForNoTypeWithoutName();
			}
		}

		// current node is typeLibrarySource node, get the library value and
		// namespace value
		if (isTypeLibrarySourceNode() == true) {
			tlName = attributes.getValue(TYPE_LIBRARY_ATTR);
			tlNamespace = attributes.getValue(TYPE_NS_ATTR);
		}

		// current node is in a type definition node or type definition node
		if (isInTypeNode() == true || isTypeNode() == true) {
			// write whatever into xsdContent,
			xsdContent.append("<" + qName);
			for (int index = 0; index < attributes.getLength(); index++) {
				// trim name and value, just for sure.
				String attrName = attributes.getQName(index).trim();
				String attrValue = attributes.getValue(index).trim();
				// if this attribute is dependency related, using a marker to
				// replace it later. and, add the dependency to dependency list.
				if (isTypeDependencyRelated(attrName, attrValue) == true) {
					dependencies.add(attrValue);
					if (isInW3CBasicSchemaTypeNamespace(attrValue) == true
							|| isInTypeLibrary(attrValue) == true) {
						// it is a standard type or a type library type
					} else {
						// then it is an internal dependency type, using free
						// marker for replacement
						attrValue = "${" + attrValue + "}";
						freemarkerReplacement.put(attrValue, null);
					}
				}

				xsdContent.append(" " + attrName + "=\"" + attrValue + "\"");
			}
			xsdContent.append(">");
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (isInTypeNode() == true || isTypeNode() == true) {
			// write whatever into XSD content if it is type node or in type
			// node.
			xsdContent.append("</" + qName + ">");
		}
		if (isTypeNode() == true) {
			// end of a type node. create a TypeModel instance for current type
			TypeModel type = null;
			if ((tlName == null) == (tlNamespace == null)) {
				type = new TypeModel(typeName, targetNamespace,
						nsMappingSchema, xsdContent.toString(),
						freemarkerReplacement, documentation, dependencies,
						tlName, tlNamespace);
			} else {
				type = new TypeModel(typeName, targetNamespace,
						nsMappingSchema, xsdContent.toString(),
						freemarkerReplacement, documentation, dependencies,
						tlName, tlNamespace);
				type.addError("Type library source is not complete!");
			}
			// put current type to map, key is the NS:TypeName
			String key = targetNamespace + ":" + typeName;
			if (xsds.get(key) != null) {
				throw new SAXException("Duplicated type found!" + key);
			}
			xsds.put(key, type);
			// clear type content, type name and documentation.
			typeName = null;
			xsdContent.delete(0, xsdContent.length());
			freemarkerReplacement = new HashMap<String, String>();
			documentation = "";
			// clear tlName and tlNamespace
			tlName = null;
			tlNamespace = null;
			// create a new list for next type
			dependencies = new HashSet<String>();
			// no longer accept content until meet another type
			acceptContent = false;
		} else if (isSchemaNode() == true) {
			// end of a type schema block. clear schema node related variables -
			// schema NS and namespace.
			nsMappingSchema = new HashMap<String, String>();
			targetNamespace = null;
		}
		xmlPath.remove(xmlPath.size() - 1);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (acceptContent == false) {
			return;
		}
		String content = new String(ch, start, length);
		if (isTypeDocumentationNode() == true) {
			documentation = content;
		}
		/*
		 * SAXP will change these characters to the real characters. But the
		 * content is in fact used as XSD content and the characters need to be
		 * transformed back. Otherwise, the XSD content would be invalidated.
		 */
		content = content.replace("&", "&amp;");
		content = content.replace("<", "&lt;");
		content = content.replace(">", "&gt;");
		content = content.replace("\"", "&quot;");
		content = content.replace("'", "&apos;");
		xsdContent.append(content);
	}

	public static void main(String[] args) throws SAXException, IOException,
			ParserConfigurationException {
		File f = new File("F:\\aaaaa");
		// File f = new
		// File("C:\\Documents and Settings\\mzang\\Desktop\\aaaaa");

		for (File wsdl : f.listFiles()) {
			if (isWSDL(wsdl) == false) {
				continue;
			}
			CutXSDFromWSDL instance = new CutXSDFromWSDL();
			instance.cutWSDL(wsdl.toString());
		}
	}

	private static boolean isWSDL(File f) {
		if (f.isFile() == false) {
			return false;
		}
		String uri = f.toString();
		int tail = uri.lastIndexOf('.');
		if (tail == -1) {
			return false;
		}
		String end = uri.substring(tail + 1);
		if (end.equalsIgnoreCase("wsdl") == false) {
			return false;
		}
		return true;
	}

}
