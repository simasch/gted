/*
 gted - gted.sourceforge.net
 Copyright (C) 2013 by Simon Martinelli, Erlach, Switzerland

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along
 with this program; if not, write to the Free Software Foundation, Inc.,
 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package net.sf.gted.editor.entry.master;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.sf.gted.editor.POFileEditorPlugin;
import net.sf.gted.editor.action.RenameMsgidAction;
import net.sf.gted.editor.entry.POMasterDetailsPage;
import net.sf.gted.editor.entry.detail.EntryPluralDetailsPage;
import net.sf.gted.editor.entry.detail.EntrySingularDetailsPage;
import net.sf.gted.editor.filter.FuzzyFilter;
import net.sf.gted.editor.filter.UntranslatedFilter;
import net.sf.gted.editor.preferences.PreferenceConstants;
import net.sf.gted.model.POEntry;
import net.sf.gted.model.POEntryPlural;
import net.sf.gted.model.POEntrySingular;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * POMasterDetailsBlock.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.12 $, $Date: 2008/08/12 13:12:50 $
 */
public class POMasterDetailsBlock extends MasterDetailsBlock {

	private POMasterDetailsPage page;

	private Table table;

	private TableViewer viewer;

	private FuzzyFilter fuzzyFilter;

	private UntranslatedFilter untranslatedFilter;

	/** The column names. */
	private final String[] columnNames = { "fuzzy", "msgId", "msgStr" };

	private Button filterFuzzyButton;

	private Button filterUntranslatedButton;

	private EntrySingularDetailsPage singularDetailsPage;

	private EntryPluralDetailsPage pluralDetailsPage;

	private Composite composite;

	private boolean vertical;

	private boolean horizontal;

	protected POEntry selectedEntry;

	/**
	 * Create the master details block
	 * 
	 * @param editor
	 */
	public POMasterDetailsBlock(POMasterDetailsPage page) {
		this.page = page;
	}

	/**
	 * Create contents of the master details block
	 * 
	 * @param managedForm
	 * @param parent
	 */
	@Override
	protected void createMasterPart(final IManagedForm managedForm,
			Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();

		final Section entriesSection = toolkit.createSection(parent,
				Section.EXPANDED | Section.TITLE_BAR);
		entriesSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				true));
		entriesSection.setText("Entries");
		entriesSection.marginWidth = 7;
		entriesSection.marginHeight = 7;

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 5;
		layout.marginHeight = 5;

		composite = toolkit.createComposite(entriesSection, SWT.WRAP);
		composite.setLayout(layout);

		filterFuzzyButton = toolkit.createButton(composite,
				"Show only fuzzy entries", SWT.CHECK);
		filterFuzzyButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(final SelectionEvent e) {
			}

			public void widgetSelected(final SelectionEvent e) {
				if (filterFuzzyButton.getSelection()) {
					viewer.addFilter(fuzzyFilter);
				} else {
					ViewerFilter[] filters = viewer.getFilters();
					List<ViewerFilter> list = Arrays.asList(filters);
					if (list.contains(fuzzyFilter)) {
						viewer.removeFilter(fuzzyFilter);
					}
				}
			}

		});

		filterUntranslatedButton = toolkit.createButton(composite,
				"Show only untranslated entries", SWT.CHECK);
		filterUntranslatedButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(final SelectionEvent e) {
			}

			public void widgetSelected(final SelectionEvent e) {
				if (filterUntranslatedButton.getSelection()) {
					viewer.addFilter(untranslatedFilter);
				} else {
					ViewerFilter[] filters = viewer.getFilters();
					List<ViewerFilter> list = Arrays.asList(filters);
					if (list.contains(untranslatedFilter)) {
						viewer.removeFilter(untranslatedFilter);
					}
				}
			}

		});

		table = toolkit.createTable(composite, SWT.FULL_SELECTION
				| SWT.HIDE_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd.heightHint = 100;
		table.setLayoutData(gd);

		final TableColumn columnFuzzy = new TableColumn(table, SWT.NONE);
		columnFuzzy.setWidth(20);
		columnFuzzy.setText(this.columnNames[0]);
		columnFuzzy.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				createSort(EntrySorter.SORT_FUZZY);
				table.setSortColumn(columnFuzzy);
			}
		});

		final TableColumn columnMsgId = new TableColumn(table, SWT.NONE);
		columnMsgId.setWidth(300);
		columnMsgId.setText(this.columnNames[1]);
		columnMsgId.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				createSort(EntrySorter.SORT_MSG_ID);
				table.setSortColumn(columnMsgId);
			}
		});

		final TableColumn columnMsgStr = new TableColumn(table, SWT.NONE);
		columnMsgStr.setWidth(300);
		columnMsgStr.setText(this.columnNames[2]);
		columnMsgStr.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				createSort(EntrySorter.SORT_MSG_STR);
				table.setSortColumn(columnMsgStr);
			}
		});

		toolkit.paintBordersFor(composite);
		entriesSection.setClient(composite);

		final SectionPart spart = new SectionPart(entriesSection);
		managedForm.addPart(spart);
		createViewer(managedForm, spart);

		this.fuzzyFilter = new FuzzyFilter();
		this.untranslatedFilter = new UntranslatedFilter();

		this.createPopupMenu();
	}

	private void setSashFormHorizontal(ScrolledForm form) {
		sashForm.setOrientation(SWT.HORIZONTAL);
		sashForm.setWeights(new int[] { 3, 4 });
		table.getColumn(1).setWidth(150);
		table.getColumn(2).setWidth(150);
		form.reflow(true);
	}

	private void setSashFormVertical(ScrolledForm form) {
		sashForm.setOrientation(SWT.VERTICAL);
		sashForm.setWeights(new int[] { 3, 4 });
		table.getColumn(1).setWidth(300);
		table.getColumn(2).setWidth(300);
		form.reflow(true);
	}

	/**
	 * @param managedForm
	 * @param spart
	 */
	private void createViewer(final IManagedForm managedForm,
			final SectionPart spart) {
		viewer = new TableViewer(table);
		viewer.setUseHashlookup(true);
		viewer.setColumnProperties(this.columnNames);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				for (final Iterator<?> iter = selection.iterator(); iter
						.hasNext();) {
					final Object object = iter.next();
					if (object instanceof POEntry) {
						selectedEntry = (POEntry) object;
					}
				}
				managedForm.fireSelectionChanged(spart, event.getSelection());
			}
		});
		viewer.setContentProvider(new EntryTableContentProvider());
		viewer.setLabelProvider(new EntryTableLabelProvider());
		viewer.setInput(page.getEditor().getFile());
	}

	/**
	 * Register the pages
	 * 
	 * @param part
	 */
	@Override
	protected void registerPages(DetailsPart part) {
		singularDetailsPage = new EntrySingularDetailsPage(this);
		part.registerPage(POEntrySingular.class, singularDetailsPage);

		pluralDetailsPage = new EntryPluralDetailsPage(this);
		part.registerPage(POEntryPlural.class, pluralDetailsPage);
	}

	/**
	 * Create the toolbar actions
	 * 
	 * @param managedForm
	 */
	@Override
	protected void createToolBarActions(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();

		Action vaction = new Action("ver", Action.AS_RADIO_BUTTON) {

			public void run() {
				setSashFormVertical(form);
			}
		};
		vaction.setChecked(vertical);
		vaction.setToolTipText("vertical");
		vaction.setImageDescriptor(POFileEditorPlugin
				.getImageDescriptor("icons/th_vertical.gif"));

		Action haction = new Action("hor", Action.AS_RADIO_BUTTON) {

			public void run() {
				setSashFormHorizontal(form);
			}
		};
		haction.setChecked(horizontal);
		haction.setToolTipText("horizontal");
		haction.setImageDescriptor(POFileEditorPlugin
				.getImageDescriptor("icons/th_horizontal.gif"));

		form.getToolBarManager().add(haction);
		form.getToolBarManager().add(vaction);
	}

	private void createPopupMenu() {
		MenuManager popupMenu = new MenuManager();
		IProject project = this.getPage().getEditor().getPoEditor().getIFile()
				.getProject();
		IAction newRowAction = new RenameMsgidAction(this, project);
		popupMenu.add(newRowAction);
		Menu menu = popupMenu.createContextMenu(table);
		table.setMenu(menu);
	}

	/**
	 * Creates the sort.
	 * 
	 * @param column
	 *            the column
	 */
	private void createSort(final int column) {
		boolean up;
		if (table.getSortDirection() == SWT.DOWN) {
			table.setSortDirection(SWT.UP);
			up = true;
		} else {
			table.setSortDirection(SWT.DOWN);
			up = false;
		}
		viewer.setSorter(new EntrySorter(up, column));
	}

	/**
	 * @return
	 */
	public POMasterDetailsPage getPage() {
		return page;
	}

	/**
   * 
   */
	public void refresh() {
		if (this.viewer != null) {
			this.viewer.refresh();
		}
		if (this.singularDetailsPage != null) {
			this.singularDetailsPage.refresh();
		}
		if (this.pluralDetailsPage != null) {
			this.pluralDetailsPage.refresh();
		}
	}

	@Override
	public void createContent(IManagedForm managedForm) {
		final String orientation = POFileEditorPlugin.getDefault()
				.getPreferenceStore().getString(
						PreferenceConstants.P_ORIENTATION);
		if (orientation.equals(PreferenceConstants.P_ORIENTATION_VERTICAL)) {
			vertical = true;
			horizontal = false;
		} else {
			horizontal = true;
			vertical = false;
		}
		super.createContent(managedForm);

		if (vertical) {
			this.setSashFormVertical(managedForm.getForm());
		} else {
			this.setSashFormHorizontal(managedForm.getForm());
		}
	}

	public POEntry getSelectedEntry() {
		return selectedEntry;
	}
}
