package com.deepnighttwo.resourceresolver.ui.views.utils;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.deepnighttwo.resourceresolver.ui.resolver.data.IResourceDetailsData;
import com.deepnighttwo.resourceresolver.ui.views.ResourceDetailsView;

public class ResourceViewSorter extends ViewerSorter {

	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;

	public ResourceViewSorter() {
		this.propertyIndex = 1;
		direction = DESCENDING;
	}

	public void setColumn(int column) {
		if (column == this.propertyIndex) {
			// Same column as last sort; toggle the direction
			direction = 1 - direction;
		} else {
			// New column; do an ascending sort
			this.propertyIndex = column;
			direction = DESCENDING;
		}
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		int ret = 0;
		Object[] row1 = (Object[]) e1;
		Object[] row2 = (Object[]) e2;

		Object ele1 = row1[propertyIndex];
		Object ele2 = row2[propertyIndex];

		ret =  ResourceDetailsView.comparators[propertyIndex].compare(
				(IResourceDetailsData) ele1, (IResourceDetailsData) ele2);
		// if (ele1 instanceof IResourceDetailsData
		// && ele2 instanceof IResourceDetailsData) {
		// } else if (ele1 instanceof IResourceDetailsData
		// && ele2 instanceof IResourceDetailsData == false) {
		// ret = Integer.MAX_VALUE;
		// } else if (ele1 instanceof IResourceDetailsData == false
		// && ele2 instanceof IResourceDetailsData) {
		// ret = Integer.MIN_VALUE;
		// } else {
		// ret = stringCompare(ele1, ele2);
		// }
		if (direction == DESCENDING) {
			ret = -ret;
		}
		return ret;
	}
	//
	// private int stringCompare(Object ele1, Object ele2) {
	// if (ele1 == null && ele2 == null) {
	// return 0;
	// }
	// if (ele1 == null) {
	// return Integer.MIN_VALUE;
	// }
	// if (ele2 == null) {
	// return Integer.MAX_VALUE;
	// }
	// String str1 = ele1.toString();
	// String str2 = ele2.toString();
	// if (ele1 instanceof IResourceDetailsData) {
	// str1 = ((IResourceDetailsData) ele1).getDisplayText();
	// }
	// if (ele2 instanceof IResourceDetailsData) {
	// str2 = ((IResourceDetailsData) ele2).getDisplayText();
	// }
	// return str1.compareTo(str2);
	// }

}
