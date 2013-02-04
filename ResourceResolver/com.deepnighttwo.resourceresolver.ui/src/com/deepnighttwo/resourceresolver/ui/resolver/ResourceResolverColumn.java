package com.deepnighttwo.resourceresolver.ui.resolver;

import org.eclipse.swt.graphics.Image;

import com.deepnighttwo.resourceresolver.ui.views.utils.DefaultColumnItemComparator;

/**
 * This is used by extensions to customerize the
 * 
 * @author mzang
 * 
 */
public class ResourceResolverColumn {

	/**
	 * column image
	 */
	private Image image;

	/**
	 * column display name
	 */
	private String columnName;

	/**
	 * column width
	 */
	private int columnWidth;

	/**
	 * comparator to compare items in the column
	 */
	private IColumnItemComparator comparator;

	public ResourceResolverColumn(String columnName, int columnWidth,
			IColumnItemComparator comparator, Image image) {
		this.image = image;
		this.columnName = columnName;
		this.columnWidth = columnWidth;
		this.comparator = comparator;
	}

	public ResourceResolverColumn(String columnName, int columnWidth,
			Image image) {
		this(columnName, columnWidth,
				DefaultColumnItemComparator.getInstance(), image);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getColumnWidth() {
		return columnWidth;
	}

	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
	}

	public IColumnItemComparator getComparator() {
		return comparator;
	}

	public void setComparator(IColumnItemComparator comparator) {
		this.comparator = comparator;
	}

}
