package com.deepnighttwo.resourceresolver.ui.resolver.data;

import org.eclipse.swt.graphics.Image;

public class TextData implements IResourceDetailsData {

	private String content;

	public TextData(String content) {
		if (content == null) {
			content = "";
		}
		this.content = content;
	}

	@Override
	public String getDisplayText() {
		return content;
	}

	@Override
	public Image getImage() {
		return null;
	}

	public String toString() {
		return content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
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
		TextData other = (TextData) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object anotherObj) {
		return content.compareTo(anotherObj.toString());
	}
	
	
	@Override
	public DataType getDataType() {
		return DataType.TEXT;
	}

}
