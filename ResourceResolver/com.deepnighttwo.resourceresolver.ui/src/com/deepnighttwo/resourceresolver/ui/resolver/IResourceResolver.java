package com.deepnighttwo.resourceresolver.ui.resolver;

import java.io.File;

import com.deepnighttwo.resourceresolver.ui.resolver.data.IResourceDetailsData;

/**
 * Interface of providing extra content for resource.
 * 
 * @author mzang
 * 
 */
public interface IResourceResolver {

	/**
	 * For a resolver, this array length should not change between different
	 * items
	 * 
	 * @return columns that the resolver need
	 */
	ResourceResolverColumn[] getResolveColumnNames();

	/**
	 * The resource path, it could be a file path or directory path.the data
	 * returned by the resolver. The length of the returned array must be the
	 * same as the be the same as the column name length.
	 * 
	 * @param resourcePath
	 * 
	 * @return resolved data
	 */
	IResourceDetailsData[] getResolvedData(final File resourcePath);

}
