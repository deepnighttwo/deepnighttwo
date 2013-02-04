package wsdltypehandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TypeModel {

	private String typeName;

	private String namespace;

	private String typeContent;

	private String documentation;

	private Set<String> dependencies = null;

	private Map<String, String> nsMappingSchema = null;

	private Map<String, String> freemarkerReplacement = null;

	private List<String> errors = null;

	// properties that only available for type library type, which is the type
	// in wsdl but has the typeLibrarySource node. They must both have validated
	// value or both are null.
	private String typelibName = null;

	private String typelibNamespace = null;

	public TypeModel() {

	}

	public TypeModel(String typeName, String namespace,
			Map<String, String> nsMappingSchema, String typeContent,
			Map<String, String> freemarkerReplacement, String documentation,
			Set<String> dependencies, String typelibName,
			String typelibNamespace) {
		this.typeName = typeName;
		this.namespace = namespace;
		this.nsMappingSchema = nsMappingSchema;
		this.typeContent = typeContent;
		this.freemarkerReplacement = freemarkerReplacement;
		this.documentation = documentation;
		this.dependencies = dependencies;

		// type from type library
		this.typelibName = typelibName;
		this.typelibNamespace = typelibNamespace;
	}

	public boolean isNeedToImport() {
		return typelibName == null && typelibNamespace == null;
	}

	public boolean isTypeLibraryType() {
		return typelibName != null && typelibNamespace != null;
	}

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

	public String getTypeContent() {
		return typeContent;
	}

	public void setTypeContent(String typeContent) {
		this.typeContent = typeContent;
	}

	public Set<String> getDependencies() {
		return dependencies;
	}

	public void setDependencies(Set<String> dependencies) {
		this.dependencies = dependencies;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public Map<String, String> getNsMappingSchema() {
		return nsMappingSchema;
	}

	public void setNsMappingSchema(Map<String, String> nsMappingSchema) {
		this.nsMappingSchema = nsMappingSchema;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public String getTypelibName() {
		return typelibName;
	}

	public void setTypelibName(String typelibName) {
		this.typelibName = typelibName;
	}

	public String getTypelibNamespace() {
		return typelibNamespace;
	}

	public void setTypelibNamespace(String typelibNamespace) {
		this.typelibNamespace = typelibNamespace;
	}

	public Map<String, String> getFreemarkerReplacement() {
		return freemarkerReplacement;
	}

	public void setFreemarkerReplacement(
			Map<String, String> freemarkerReplacement) {
		this.freemarkerReplacement = freemarkerReplacement;
	}

	public void addError(String error) {
		if (errors == null) {
			errors = new ArrayList<String>();
		}
		errors.add(error);
	}

}
