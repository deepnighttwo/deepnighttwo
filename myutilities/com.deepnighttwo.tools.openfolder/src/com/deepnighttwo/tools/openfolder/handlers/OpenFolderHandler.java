package com.deepnighttwo.tools.openfolder.handlers;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.core.Openable;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.swt.program.Program;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.deepnighttwo.tools.openfolder.Activator;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
@SuppressWarnings("restriction")
public class OpenFolderHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public OpenFolderHandler() {

	}

	private final static IHandler[] EXTRA_HANDLERS;

	// private final static IWorkspaceRoot root = ResourcesPlugin.getWorkspace()
	// .getRoot();

	static {
		IExtension[] extensions = Platform
				.getExtensionRegistry()
				.getExtensionPoint(
						com.deepnighttwo.tools.openfolder.Activator.PLUGIN_ID,
						"openFolderExtraType").getExtensions();

		List<IHandler> handlers = new ArrayList<IHandler>();
		for (IExtension extension : extensions) {
			IConfigurationElement[] configElements = extension
					.getConfigurationElements();
			for (IConfigurationElement element : configElements) {

				if (element.getName().equals("openFolderHandler") == false) {
					continue;
				}
				try {
					IHandler handler = (IHandler) element
							.createExecutableExtension("handlerName");
					handlers.add(handler);
				} catch (CoreException e) {
					e.printStackTrace();
				}

			}
		}
		EXTRA_HANDLERS = handlers.toArray(new IHandler[0]);
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Object obj = event.getApplicationContext();
		if (obj instanceof IEvaluationContext == false) {
			return null;
		}
		IEvaluationContext context = (IEvaluationContext) obj;
		Object value = context.getDefaultVariable();

		if (value instanceof Collection == false) {
			return null;
		}

		Collection<?> items = (Collection<?>) value;

		if (items.size() == 0) {
			Object file = getFileFromActiveEditor();
			if (file != null) {
				handleFileObject(file);
			}
			return null;
		}

		Object selection = items.toArray()[0];

		if (selection instanceof IProject) {
			IProject project = (IProject) selection;
			handleIProject(project);
		} else if (selection instanceof IFolder) {
			IFolder folder = (IFolder) selection;
			handleIFolder(folder);
		} else if (selection instanceof IFile) {
			IFile file = (IFile) selection;
			handleIFile(file);
		} else if (selection instanceof Openable) {
			Openable openable = (Openable) selection;
			File file = getFileFromOpenable(openable);
			if (file.exists() == false) {
				IJavaElement parent = openable.getParent();
				while ((parent != null)
						&& ((parent instanceof IProject == false) && (parent instanceof JavaProject == false))) {
					parent = parent.getParent();
				}
				IProject project = null;
				if (parent instanceof JavaProject) {
					project = ((JavaProject) parent).getProject();
				} else if (parent instanceof IProject) {
					project = (IProject) parent;
				}

				if (parent != null) {
					IFile projectFile = project.getFile(openable.getPath());
					file = projectFile.getLocation().toFile();
				}
			}

			handleFile(file);
		} else if (selection instanceof TextSelection) {
			Object file = getFileFromActiveEditor();
			if (file != null) {
				handleFileObject(file);
			}
		} else {
			System.out.println(selection.getClass());
			org.eclipse.osgi.internal.baseadaptor.DefaultClassLoader cl = (org.eclipse.osgi.internal.baseadaptor.DefaultClassLoader) selection
					.getClass().getClassLoader();
			System.out.println(cl.getBundle());

			for (IHandler handler : EXTRA_HANDLERS) {
				Object ret = handler.execute(event);
				if (Boolean.TRUE.equals(ret) == true) {
					return null;
				}
			}
		}
		return null;
	}

	public void handleFileObject(Object file) {
		if (file instanceof IFile) {
			handleIFile((IFile) file);
		} else if (file instanceof File) {
			handleFile((File) file);
		}
	}

	public void handleIProject(IProject project) {
		File localFile = project.getLocation().toFile();
		handleFile(localFile);
	}

	public void handleIFolder(IFolder folder) {
		File localFile = folder.getLocation().toFile();
		handleFile(localFile);
	}

	public void handleIFile(IFile file) {
		File localfile = file.getLocation().toFile();
		handleFile(localfile);
	}

	public void handleFile(File file) {
		if (file.isDirectory() == true) {
			Program.launch(file.toString());
		} else if (file.isFile() == true) {
			Program.launch(file.getParent());
		}
	}

	private Object getFileFromActiveEditor() {
		IWorkbench workbench = Activator.getDefault().getWorkbench();
		IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();
		IEditorPart activeEditor = page.getActiveEditor();

		Object file = null;
		if (activeEditor instanceof MultiPageEditorPart) {
			MultiPageEditorPart multiPageEditor = (MultiPageEditorPart) (activeEditor);
			Integer currPageID = multiPageEditor.getActivePage();
			try {
				Method m = MultiPageEditorPart.class.getDeclaredMethod(
						"getEditor", int.class);
				m.setAccessible(true);
				Object epObj = m.invoke(multiPageEditor, currPageID);
				if (epObj instanceof IEditorPart) {
					IEditorPart ep = (IEditorPart) epObj;
					file = getFileFromEditor(ep);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		if (file == null) {
			file = getFileFromEditor(activeEditor);
		}
		return file;
	}

	private Object getFileFromEditor(IEditorPart ep) {
		IAdaptable currEditor = ep.getEditorInput();
		IFile input = null;
		input = (IFile) currEditor.getAdapter(IFile.class);
		if (input != null) {
			return input;
		}

		{
			IJavaElement javaEle = (IJavaElement) ep.getEditorInput()
					.getAdapter(IJavaElement.class);
			if (javaEle instanceof Openable) {
				return getFileFromOpenable((Openable) javaEle);
			}
		}

		return null;
	}

	private File getFileFromOpenable(Openable openable) {
		try {
			File file = openable.getResource().getLocation().toFile();
			if (file.exists() == true) {
				return file;
			}
		} catch (Throwable e) {
		}
		try {
			File file = openable.getPath().toFile();
			if (file.exists() == true) {
				return file;
			}
		} catch (Throwable e) {
		}
		return null;
	}

}
