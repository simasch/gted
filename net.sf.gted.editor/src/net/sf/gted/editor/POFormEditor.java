package net.sf.gted.editor;

import net.sf.gted.editor.entry.POMasterDetailsPage;
import net.sf.gted.editor.header.HeaderForm;
import net.sf.gted.editor.preferences.PreferenceConstants;
import net.sf.gted.editor.source.POSourceEditor;
import net.sf.gted.editor.source.outline.POOutlinePage;
import net.sf.gted.model.POFile;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class POFormEditor extends FormEditor implements IResourceChangeListener {

	private static final int UI_VIEW = 0;

	/** The Constant SOURCE_VIEW. */
	private static final int SOURCE_VIEW = 2;

	/** The changed. */
	private boolean changed = false;

	/** The file. */
	private POFile file;

	/** The text editor used in page 2. */
	public POSourceEditor poEditor;

	private POMasterDetailsPage masterDetailsPage;

	private HeaderForm headerForm;

	private POOutlinePage outlinePage;

	public POFormEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
		this.file = new POFile();
	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	@Override
	protected void addPages() {
		try {
			this.masterDetailsPage = new POMasterDetailsPage(this);
			int index = addPage(this.masterDetailsPage);
			this.setPageText(index, "Entries");

			this.headerForm = new HeaderForm(this);
			index = addPage(this.headerForm);
			this.setPageText(index, "Header");

			this.poEditor = new POSourceEditor(this);
			index = addPage(this.poEditor, this.getEditorInput());
			this.setPageText(index, "Source");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		this.setPartName(this.poEditor.getIFile().getName());

		this.choseActiveView();
	}

	private void choseActiveView() {
		final String initalView = POFileEditorPlugin.getDefault()
				.getPreferenceStore().getString(
						PreferenceConstants.P_EDITOR_VIEW);
		if (initalView.equals(PreferenceConstants.P_EDITOR_VIEW_UI)) {
			this.setActivePage(UI_VIEW);
		} else if (initalView.equals(PreferenceConstants.P_EDITOR_VIEW_SOURCE)) {
			this.setActivePage(SOURCE_VIEW);
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if (this.changed && this.getActivePage() != SOURCE_VIEW) {
			this.updateEditor();
		}
		this.poEditor.doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		if (this.changed && this.getActivePage() != SOURCE_VIEW) {
			this.updateEditor();
		}
		final IEditorPart editor = this.poEditor;
		editor.doSaveAs();
		this.setPageText(0, editor.getTitle());
		this.setInput(editor.getEditorInput());
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
		} else if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
			Display.getDefault().asyncExec(new Runnable() {

				public void run() {
					final IWorkbenchPage[] pages = POFormEditor.this.getSite()
							.getWorkbenchWindow().getPages();
					for (final IWorkbenchPage element : pages) {
						if (((FileEditorInput) POFormEditor.this.poEditor
								.getEditorInput()).getFile().getProject()
								.equals(event.getResource())) {
							final IEditorPart editorPart = element
									.findEditor(POFormEditor.this.poEditor
											.getEditorInput());
							element.closeEditor(editorPart, true);
						}
					}
				}
			});
		}
	}

	/**
	 * Goto marker.
	 * 
	 * @param marker
	 *            the marker
	 */
	public void gotoMarker(final IMarker marker) {
		this.setActivePage(POFormEditor.SOURCE_VIEW);
		IDE.gotoMarker(this.getEditor(SOURCE_VIEW), marker);
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput editorInput)
			throws PartInitException {
		// if (!(editorInput instanceof IFileEditorInput)) {
		// throw new
		// PartInitException("Invalid Input: Must be IFileEditorInput");
		// }
		super.init(site, editorInput);
	}

	/**
	 * Update editor.
	 */
	public void updateEditor() {
		if (this.changed) {
			final IDocument doc = this.poEditor.getDocumentProvider()
					.getDocument(this.poEditor.getEditorInput());
			doc.set(this.poEditor.getFile().toString());
			this.changed = false;
		}
	}

	/**
	 * Data changed.
	 */
	public void dataChanged() {
		this.changed = true;
		this.updateEditor();
	}

	/**
	 * Gets the file.
	 * 
	 * @return the file
	 */
	public POFile getFile() {
		return this.file;
	}

	/**
	 * @param message
	 */
	public void showWarningMessageBox(final String message) {
		final MessageBox box = new MessageBox(Display.getDefault()
				.getActiveShell(), SWT.ICON_WARNING | SWT.OK
				| SWT.PRIMARY_MODAL);
		box.setText("Warning");
		box.setMessage(message);
		box.open();

	}

	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
	}

	public POSourceEditor getPoEditor() {
		return poEditor;
	}

	public void refresh() {
		this.headerForm.refresh();
		this.masterDetailsPage.refresh();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class required) {
		if (IContentOutlinePage.class.equals(required)) {
			if (outlinePage == null) {
				outlinePage = new POOutlinePage(this);
				if (getEditorInput() != null) {
					outlinePage.setInput(getEditorInput());
				}
			}
			return outlinePage;
		}
		return super.getAdapter(required);

	}

	public void showSourceEditor() {
		this.setActivePage(SOURCE_VIEW);
	}
}
