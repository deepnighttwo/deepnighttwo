package com.deepnighttwo.resourceresolver.ui.resolver.data;

import org.eclipse.swt.graphics.Image;

import com.deepnighttwo.resourceresolver.ui.Activator;

/**
 * If a data is a link, e.g. to local folder or an URL, should use this class to
 * wrap it.
 * 
 * @author mzang
 * 
 */
public class LinkData implements IResourceDetailsData {
	private static Image image = Activator.getImageDescriptor("icons/link.gif")
			.createImage();

	private String linkLocation;

	public LinkData(String linkLocation) {
		if (linkLocation == null) {
			linkLocation = "";
		}
		this.linkLocation = linkLocation;
	}

	public String getLinkLocation() {
		return linkLocation;
	}

	public void setLinkLocation(String linkLocation) {
		this.linkLocation = linkLocation;
	}

	public String toString() {
		return linkLocation;
	}

	@Override
	public int compareTo(Object anotherLinkData) {
		return linkLocation.compareTo(anotherLinkData.toString());
	}

	@Override
	public String getDisplayText() {
		return toString();
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((linkLocation == null) ? 0 : linkLocation.hashCode());
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
		LinkData other = (LinkData) obj;
		if (linkLocation == null) {
			if (other.linkLocation != null)
				return false;
		} else if (!linkLocation.equals(other.linkLocation))
			return false;
		return true;
	}

	@Override
	public DataType getDataType() {
		return DataType.TEXT;
	}
}
