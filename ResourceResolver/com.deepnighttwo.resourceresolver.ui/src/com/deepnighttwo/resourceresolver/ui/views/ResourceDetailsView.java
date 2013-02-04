package com.deepnighttwo.resourceresolver.ui.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.deepnighttwo.resourceresolver.ui.Activator;
import com.deepnighttwo.resourceresolver.ui.resolver.IColumnItemComparator;
import com.deepnighttwo.resourceresolver.ui.resolver.ResourceResolverColumn;
import com.deepnighttwo.resourceresolver.ui.resolver.ResourceResolverType;
import com.deepnighttwo.resourceresolver.ui.resolver.data.LinkData;
import com.deepnighttwo.resourceresolver.ui.resolver.data.LongTextData;
import com.deepnighttwo.resourceresolver.ui.views.utils.FilePathValidator;
import com.deepnighttwo.resourceresolver.ui.views.utils.ResourceContentProvider;
import com.deepnighttwo.resourceresolver.ui.views.utils.ResourceViewLabelProvider;
import com.deepnighttwo.resourceresolver.ui.views.utils.ResourceViewSorter;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class ResourceDetailsView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "movieresolver.ui.views.MovieDetailsView";

	private TableViewer viewer;
	private Action changeRootAction;
	private Action refreshAction;
	private Action doubleClickAction;
	private Map<ResourceResolverType, TableColumn[]> resolverColumns = new HashMap<ResourceResolverType, TableColumn[]>();

	public static IColumnItemComparator[] comparators;

	public static final String SOURCE_PATH_PREF = "movieresolver.source.path";

	private final static List<ResourceResolverType> resolverTypes = new ArrayList<ResourceResolverType>();

	private final static ResourceResolverType[] TYPES;

	private static IPreferenceStore prefs;

	private static final int COLUMN_COUNT;

	private static final ImageDescriptor refreshImage = Activator
			.getImageDescriptor("icons/refresh_root.gif");

	private static final ImageDescriptor changeRootDirImage = Activator
			.getImageDescriptor("icons/change_root_dir.gif");

	static {
		// set C:\ as the default source path
		prefs = Activator.getDefault().getPreferenceStore();
		String currDefault = prefs.getDefaultString(SOURCE_PATH_PREF);
		if (currDefault == null || currDefault.length() == 0) {
			prefs.setDefault(SOURCE_PATH_PREF, "C:\\test");
		}

		// get all extensions
		IExtension[] extensions = Platform.getExtensionRegistry()
				.getExtensionPoint(Activator.PLUGIN_ID, "resolverprovider")
				.getExtensions();
		for (IExtension extension : extensions) {
			IConfigurationElement[] configElements = extension
					.getConfigurationElements();
			for (IConfigurationElement element : configElements) {
				ResourceResolverType type = ResourceResolverType
						.createResourceResolverTypeFromConfigElement(element);
				if (type != null) {
					resolverTypes.add(type);
				}
			}
		}
		TYPES = resolverTypes.toArray(new ResourceResolverType[0]);

		// get total columns
		int columnCount = 0;
		for (ResourceResolverType type : TYPES) {
			columnCount += type.getResolver().getResolveColumnNames().length;
		}
		COLUMN_COUNT = columnCount;
	}

	private Display cacheDisplay = null;

	private volatile boolean refreshInQueue = false;

	/**
	 * runnable for refresh table data. this should be executed in UI thread.
	 */
	private Runnable refreshViewTableRunnable = new Runnable() {

		@Override
		public void run() {
			viewer.refresh();
			refreshInQueue = false;
		}

	};

	/**
	 * sorter for resource viewer.
	 */
	private ResourceViewSorter sorter = new ResourceViewSorter();

	/**
	 * default constructor.
	 */
	public ResourceDetailsView() {
	}

	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	public ResourceResolverType[] getResourceResolverTypes() {
		return TYPES;
	}

	/**
	 * refresh table data.
	 */
	public synchronized void refreshViewTable() {
		if (cacheDisplay == null || refreshInQueue == true) {
			return;
		}
		refreshInQueue = true;
		cacheDisplay.asyncExec(refreshViewTableRunnable);
	}

	public boolean viewIsDisposed() {
		return viewer.getTable().isDisposed();
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION);
		createTableColumns(viewer);
		viewer.setContentProvider(new ResourceContentProvider(this));
		viewer.setLabelProvider(new ResourceViewLabelProvider());
		viewer.setSorter(sorter);
		String rootSource = prefs.getString(SOURCE_PATH_PREF);
		viewer.setInput(rootSource);

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem()
				.setHelp(viewer.getControl(), "movieresolver.ui.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		supportClick();

		if (cacheDisplay == null) {
			cacheDisplay = viewer.getControl().getDisplay();
		}

	}

	/**
	 * add double click support for view table.
	 */
	private void supportClick() {
		final Table table = viewer.getTable();
		table.addListener(SWT.MouseDoubleClick, new Listener() {
			public void handleEvent(Event event) {
				// find clicked on which item
				Point pt = new Point(event.x, event.y);
				TableItem item = table.getItem(pt);
				if (item == null)
					return;
				// find the column
				for (int i = 0; i < COLUMN_COUNT; i++) {
					Rectangle rect = item.getBounds(i);
					if (rect.contains(pt)) {
						// int index = table.indexOf(item);
						Object[] obj = (Object[]) item.getData();
						if (obj[i] instanceof LinkData) {
							Program.launch(obj[i].toString());
						} else if (obj[i] instanceof LongTextData) {
							LongTextData longText = (LongTextData) obj[i];
							MessageDialog.openConfirm(null, "Details",
									longText.getDisplayText());
						}
					}
				}
			}
		});
	}

	private void createTableColumns(TableViewer view) {
		Table table = view.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		int colIndexCounter = 0;
		List<IColumnItemComparator> cps = new ArrayList<IColumnItemComparator>();
		for (ResourceResolverType type : TYPES) {
			ResourceResolverColumn[] resolverCols = type.getResolver()
					.getResolveColumnNames();
			TableColumn[] columns = new TableColumn[resolverCols.length];
			resolverColumns.put(type, columns);

			for (int i = 0; i < resolverCols.length; i++) {
				final TableColumn column = new TableColumn(table, SWT.LEFT);
				column.setText(resolverCols[i].getColumnName());
				cps.add(resolverCols[i].getComparator());
				column.setResizable(true);
				column.setMoveable(true);
				column.setWidth(resolverCols[i].getColumnWidth());
				column.setImage(resolverCols[i].getImage());
				column.addSelectionListener(getTableColumnSorterAdapter(column,
						colIndexCounter));
				colIndexCounter++;
			}
		}
		comparators = cps.toArray(new IColumnItemComparator[0]);
	}

	private SelectionAdapter getTableColumnSorterAdapter(
			final TableColumn column, final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sorter.setColumn(index);
				System.out.println(index);
				int dir = viewer.getTable().getSortDirection();
				if (viewer.getTable().getSortColumn() == column) {
					dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
				} else {

					dir = SWT.DOWN;
				}
				viewer.getTable().setSortDirection(dir);
				viewer.getTable().setSortColumn(column);
				viewer.refresh();
			}
		};
		return selectionAdapter;
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ResourceDetailsView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(changeRootAction);
		manager.add(new Separator());
		manager.add(refreshAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(changeRootAction);
		manager.add(refreshAction);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(changeRootAction);
		manager.add(refreshAction);
	}

	private void makeActions() {
		changeRootAction = new Action() {
			public void run() {
				changeResourceRoot();
			}
		};
		changeRootAction.setText("Change Root");
		changeRootAction.setToolTipText("Change Resource Root");
		changeRootAction.setImageDescriptor(changeRootDirImage);

		refreshAction = new Action() {
			public void run() {
				String resourceRoot = prefs.getString(SOURCE_PATH_PREF);
				viewer.setInput(resourceRoot);
			}
		};
		refreshAction.setText("Refresh");
		refreshAction.setToolTipText("Refresh Root");
		refreshAction.setImageDescriptor(refreshImage);
		doubleClickAction = new Action() {
			public void run() {
				// ISelection selection = viewer.getSelection();
				// Object obj = ((IStructuredSelection) selection)
				// .getFirstElement();
				// showMessage("Double-click detected on " + obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	public void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(),
				"Movie Details View", message);
	}

	private void changeResourceRoot() {
		Shell currShell = viewer.getControl().getShell();
		InputDialog inputDialog = new InputDialog(currShell, "Resource Root",
				"Please Specify the resource root:",
				prefs.getString(SOURCE_PATH_PREF),
				FilePathValidator.getInstance());
		int retCode = inputDialog.open();
		if (retCode == InputDialog.OK) {
			String newPath = inputDialog.getValue();
			prefs.setValue(SOURCE_PATH_PREF, newPath);
			viewer.setInput(newPath);
		}
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */

	public void setFocus() {
		viewer.getControl().setFocus();
	}

}
