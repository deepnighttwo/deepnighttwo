package com.deepnighttwo.resourceresolver.ui.resolver.data;

import org.eclipse.swt.graphics.Image;

public class DoubleData implements IResourceDetailsData {

	private Double number;

	public DoubleData(Double number) {
		this.number = number;
	}

	public DoubleData(String numberStr) {
		try {
			this.number = Double.valueOf(numberStr);
		} catch (Throwable ex) {
			this.number = -1.0;
		}
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof DoubleData) {
			return number.compareTo(((DoubleData) o).number);
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
