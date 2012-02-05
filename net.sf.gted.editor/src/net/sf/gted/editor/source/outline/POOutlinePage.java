package net.sf.gted.editor.source.outline;

import java.util.Arrays;
import java.util.List;

import net.sf.gted.editor.POFileEditorPlugin;
import net.sf.gted.editor.POFormEditor;
import net.sf.gted.editor.filter.FuzzyFilter;
import net.sf.gted.editor.filter.UntranslatedFilter;
import net.sf.gted.model.POEntry;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class POOutlinePage extends ContentOutlinePage {

	private POFormEditor editor;
	private Action nameSortAction;
	private NameSorter nameSorter;
	private Action fuzzyFilterAction;
	private Action untranslatedFilterAction;
	private UntranslatedFilter untranslatedFilter;
	private FuzzyFilter fuzzyFilter;

	private final ImageDescriptor imageSort = POFileEditorPlugin
			.getImageDescriptor("icons/alphab_sort_co.gif");
	private final ImageDescriptor imageFuzzy = POFileEditorPlugin
			.getImageDescriptor("icons/entry_fuzzy.gif");
	private final ImageDescriptor imageUntranslated = POFileEditorPlugin
			.getImageDescriptor("icons/entry_untranslated.gif");

	public POOutlinePage(POFormEditor formEditor) {
		this.editor = formEditor;
	}

	public void createControl(Composite parent) {
		super.createControl(parent);

		TreeViewer treeViewer = getTreeViewer();
		treeViewer.setLabelProvider(new POFileLabelProvider());
		treeViewer.setContentProvider(new POFileContentProvider());
		treeViewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
		treeViewer.setInput(editor.getFile());

		this.nameSorter = new NameSorter();
		this.nameSortAction = new Action("Sort") {

			public void run() {
				if (getTreeViewer().getSorter() == null) {
					getTreeViewer().setSorter(nameSorter);
					nameSortAction.setChecked(true);
				} else {
					getTreeViewer().setSorter(null);
					nameSortAction.setChecked(false);
				}
			}
		};
		this.nameSortAction.setDescription("Sort entries alphabetically");
		this.nameSortAction.setToolTipText("Sort entries alphabetically");
		this.nameSortAction.setChecked(false);
		this.nameSortAction.setImageDescriptor(this.imageSort);
		this.getSite().getActionBars().getToolBarManager().add(nameSortAction);

		this.untranslatedFilter = new UntranslatedFilter();
		this.untranslatedFilterAction = new Action("Untranslated") {

			public void run() {
				ViewerFilter[] filters = getTreeViewer().getFilters();
				List<ViewerFilter> list = Arrays.asList(filters);
				if (list.contains(untranslatedFilter)) {
					getTreeViewer().removeFilter(untranslatedFilter);
					untranslatedFilterAction.setChecked(false);
				} else {
					getTreeViewer().addFilter(untranslatedFilter);
					untranslatedFilterAction.setChecked(true);
				}
			}
		};
		this.untranslatedFilterAction
				.setDescription("Show only untranslated entries");
		this.untranslatedFilterAction
				.setToolTipText("Show only untranslated entries");
		this.untranslatedFilterAction.setChecked(false);
		this.untranslatedFilterAction
				.setImageDescriptor(this.imageUntranslated);
		this.getSite().getActionBars().getToolBarManager().add(
				untranslatedFilterAction);

		this.fuzzyFilter = new FuzzyFilter();
		this.fuzzyFilterAction = new Action("Fuzzy") {

			public void run() {
				ViewerFilter[] filters = getTreeViewer().getFilters();
				List<ViewerFilter> list = Arrays.asList(filters);
				if (list.contains(fuzzyFilter)) {
					getTreeViewer().removeFilter(fuzzyFilter);
					fuzzyFilterAction.setChecked(false);
				} else {
					getTreeViewer().addFilter(fuzzyFilter);
					fuzzyFilterAction.setChecked(true);
				}
			}
		};
		this.fuzzyFilterAction.setDescription("Show only fuzzy entries");
		this.fuzzyFilterAction.setToolTipText("Show only fuzzy entries");
		this.fuzzyFilterAction.setChecked(false);
		this.fuzzyFilterAction.setImageDescriptor(this.imageFuzzy);
		this.getSite().getActionBars().getToolBarManager().add(
				fuzzyFilterAction);
	}

	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);

		ISelection selection = event.getSelection();
		if (!selection.isEmpty()) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			POEntry entry = (POEntry) structuredSelection.getFirstElement();
			int lineOffset;
			try {
				lineOffset = this.editor.getPoEditor().getInputDocument()
						.getLineOffset(entry.getLine() - 1);
				this.editor.getPoEditor().setHighlightRange(lineOffset,
						entry.getMsgId().length(), true);
				this.editor.showSourceEditor();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	public void setInput(IEditorInput editorInput) {
		if (this.getTreeViewer() != null) {
			this.getTreeViewer().setInput(editorInput);
		}
	}
}
