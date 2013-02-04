package com.deepnighttwo.resourceresolver.ui.resolver.data;

import org.eclipse.swt.graphics.Image;

public interface IResourceDetailsData extends Comparable<Object> {

	String getDisplayText();

	Image getImage();
	
	DataType getDataType();

}
