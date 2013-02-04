package com.deepnighttwo.resourceresolver.ui.resolver.data;

import org.eclipse.swt.graphics.Image;

public class IntegerData implements IResourceDetailsData {

	private Integer number;

	public IntegerData(Integer number) {
		this.number = number;
	}

	public IntegerData(String numberStr) {
		try {
			this.number = Integer.valueOf(numberStr);
		} catch (Throwable ex) {
			this.number = -1;
		}
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof IntegerData) {
			return number.compareTo(((IntegerData) o).number);
		} else {
			return 1;
		}
	}

	@Override
	public String getDisplayText() {
		return number.toString();
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public DataType getDataType() {
		return DataType.NUMBER;
	}
}
