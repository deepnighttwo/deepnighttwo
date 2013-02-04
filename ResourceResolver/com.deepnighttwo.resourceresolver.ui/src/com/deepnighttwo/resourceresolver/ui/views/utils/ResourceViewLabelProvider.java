package com.deepnighttwo.resourceresolver.ui.views.utils;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.deepnighttwo.resourceresolver.ui.resolver.data.IResourceDetailsData;

public class ResourceViewLabelProvider extends LabelProvider implements
		ITableLabelProvider {
	public String getColumnText(Object obj, int index) {
		Object[] data = (Object[]) obj;
		if (data[index] instanceof IResourceDetailsData) {
			return ((IResourceDetailsData) data[index]).getDisplayText();
		}
		return data[index].toString();
	}

	public Image getColumnImage(Object obj, int index) {
		Object[] data = (Object[]) obj;
		return getImage(data[index]);
	}

	public Image getImage(Object obj) {
		// PlatformUI.getWorkbench().getSharedImages()
		// .getImage(ISharedImages.IMG_LCL_LINKTO_HELP);
		if (obj instanceof IResourceDetailsData) {
			return ((IResourceDetailsData) obj).getImage();
		}
		return null;
	}
}