package com.deepnighttwo.resourceresolver.ui.resolver.data;

import java.text.DateFormat;
import java.util.Date;

import org.eclipse.swt.graphics.Image;

import com.deepnighttwo.resourceresolver.ui.Activator;

/**
 * Date date should be wrapped by this class.
 * 
 * @author mzang
 * 
 */
public class DateData implements IResourceDetailsData {

	private static Image image = Activator.getImageDescriptor(
			"icons/date_calendar.gif").createImage();

	private Date date;

	public DateData(long timeInMilli) {
		date = new Date(timeInMilli);
	}

	public DateData(Date date) {
		this.date = date;
	}

	public String toString() {
		DateFormat formatter = DateFormat.getDateTimeInstance();
		return formatter.format(date);
	}

	@Override
	public int compareTo(Object anotherDate) {
		if (anotherDate instanceof DateData == false) {
			return 1;
		}
		return date.compareTo(((DateData) anotherDate).date);
	}

	@Override
	public String getDisplayText() {
		return toString();
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		DateData other = (DateData) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}

	@Override
	public DataType getDataType() {
		return DataType.DATE;
	}

}
