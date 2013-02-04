package com.deepnighttwo.resourceresolver.ui.views.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.deepnighttwo.resourceresolver.ui.Activator;
import com.deepnighttwo.resourceresolver.ui.resolver.ResourceResolverType;
import com.deepnighttwo.resourceresolver.ui.views.ResourceDetailsView;

public class ResourceContentProvider implements IStructuredContentProvider {

	private Object[] data;

	private static Object[][] refreshingData;

	private static Object[][] emptyData;

	private static IPreferenceStore prefs = Activator.getDefault()
			.getPreferenceStore();

	private ResourceDetailsView view;

	public ResourceContentProvider(ResourceDetailsView view) {
		this.view = view;
		refreshingData = new String[1][view.getColumnCount()];
		emptyData = new String[1][view.getColumnCount()];
		for (int i = 0; i < view.getColumnCount(); i++) {
			refreshingData[0][i] = "";
			emptyData[0][i] = "";
		}
		refreshingData[0][0] = "Refreshing...";
	}

	private volatile Job currentJob = null;

	public synchronized void refreshData() {
		data = refreshingData;
		Job runner = new Job("Update Resource content") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				List<Object> dataList = new ArrayList<Object>();
				String path = prefs
						.getString(ResourceDetailsView.SOURCE_PATH_PREF);
				File dir = new File(path);

				int currIdx = 0;
				File[] allFiles = dir.listFiles();
				if (allFiles == null) {
					if (shouldContinue() == true) {
						data = emptyData;
						pushDataToView();
					}
					return Status.CANCEL_STATUS;
				}

				monitor.beginTask("Update resource content", allFiles.length);
				long lastPush = System.currentTimeMillis();
				int lastSize = 0;
				ResourceResolverType[] types = view.getResourceResolverTypes();
				for (File file : dir.listFiles()) {
					if (shouldContinue() == false) {
						return Status.CANCEL_STATUS;
					}
					Object[] rowData = new Object[view.getColumnCount()];
					for (ResourceResolverType type : types) {
						if (shouldContinue() == false) {
							return Status.CANCEL_STATUS;
						}
						if (type.isEnabled() == false) {
							continue;
						}
						Object[] partData = type.getResolver().getResolvedData(
								file);
						int partLen = partData.length;
						System.arraycopy(partData, 0, rowData, currIdx, partLen);
						currIdx += partLen;
					}
					monitor.worked(1);
					dataList.add(rowData);
					currIdx = 0;

					// addtional operation
					long currentTime = System.currentTimeMillis();
					int currentSize = dataList.size();
					if (shouldContinue() == true
							&& (((currentTime - lastPush) / 1000 > 1) || (currentSize
									- lastSize > 5))) {
						data = dataList.toArray();
						pushDataToView();
						lastPush = currentTime;
						lastSize = currentSize;
					}
				}
				if (shouldContinue() == true) {
					data = dataList.toArray();
					pushDataToView();
					return Status.OK_STATUS;
				} else {
					return Status.CANCEL_STATUS;
				}
			}

			private boolean shouldContinue() {
				return (this == currentJob) && (view.viewIsDisposed() == false)
						&& (Activator.getDefault() != null);
			}
		};
		currentJob = runner;
		runner.schedule();
	}

	private void pushDataToView() {
		view.refreshViewTable();
	}

	@Override
	public void dispose() {
		data = null;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		refreshData();
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return data;
	}

}
