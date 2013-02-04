package com.deepnighttwo.resourceresolver.ui.resolver;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

/**
 * A wrapper to represent a resource resolver.
 * 
 * @author mzang
 * 
 */
public class ResourceResolverType {

	private String id;
	private String name;
	private IResourceResolver resolver;
	private boolean enabled = true;

	public ResourceResolverType(String id, String name,
			IResourceResolver resolver) {
		this.id = id;
		this.name = name;
		this.resolver = resolver;
		this.enabled = true;
	}

	public static ResourceResolverType createResourceResolverTypeFromConfigElement(
			IConfigurationElement configElement) {
		if (configElement.getName().equals("resolverProvider") == false) {
			return null;
		}
		String p_id = configElement.getAttribute("id");
		String p_name = configElement.getAttribute("name");
		try {
			IResourceResolver p_resolver = (IResourceResolver) configElement
					.createExecutableExtension("ResolverClassName");
			return new ResourceResolverType(p_id, p_name, p_resolver);
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IResourceResolver getResolver() {
		return resolver;
	}

	public void setResolver(IResourceResolver resolver) {
		this.resolver = resolver;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
