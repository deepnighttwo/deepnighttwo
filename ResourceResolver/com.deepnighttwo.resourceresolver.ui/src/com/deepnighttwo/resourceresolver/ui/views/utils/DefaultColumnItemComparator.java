package com.deepnighttwo.resourceresolver.ui.views.utils;

import com.deepnighttwo.resourceresolver.ui.resolver.IColumnItemComparator;
import com.deepnighttwo.resourceresolver.ui.resolver.data.DataType;
import com.deepnighttwo.resourceresolver.ui.resolver.data.DateData;
import com.deepnighttwo.resourceresolver.ui.resolver.data.IResourceDetailsData;
import com.deepnighttwo.resourceresolver.ui.resolver.data.IntegerData;
import com.deepnighttwo.resourceresolver.ui.resolver.data.TextData;

public class DefaultColumnItemComparator implements IColumnItemComparator {

	private static DefaultColumnItemComparator comparator = new DefaultColumnItemComparator();

	private DefaultColumnItemComparator() {

	}

	public static DefaultColumnItemComparator getInstance() {
		return comparator;
	}

	@Override
	public int compare(IResourceDetailsData ele1, IResourceDetailsData ele2) {
		if (ele1 == null && ele2 == null) {
			return 0;
		}

		if (ele1 == null) {
			ele1 = getDefaultValueForNull(ele2);
		}

		if (ele2 == null) {
			ele2 = getDefaultValueForNull(ele1);
		}

		if (ele1.getClass() == ele2.getClass()) {
			return ele1.compareTo(ele2);
		}

		if (ele1.getDataType() == ele2.getDataType()
				&& (ele1.getDataType() == DataType.NUMBER || ele1.getDataType() == DataType.DATE)) {
			return Double.compare(Double.valueOf(ele1.getDisplayText()),
					Double.valueOf(ele1.getDisplayText()));
		}

		return ele1.getDisplayText().compareTo(ele2.getDisplayText());

	}

	private static IResourceDetailsData getDefaultValueForNull(
			IResourceDetailsData another) {
		if (another.getDataType() == DataType.NUMBER) {
			return new IntegerData(Integer.MIN_VALUE);
		}

		if (another.getDataType() == DataType.TEXT) {
			return new TextData("");
		}

		if (another.getDataType() == DataType.DATE) {
			return new DateData(0);
		}

		throw new IllegalArgumentException();

	}

}
