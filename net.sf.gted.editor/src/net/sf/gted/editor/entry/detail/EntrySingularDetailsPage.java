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
package net.sf.gted.editor.entry.detail;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.gted.editor.entry.master.POMasterDetailsBlock;
import net.sf.gted.model.POEntrySingular;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

/**
 * EntrySingularDetailsPage.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.12 $, $Date: 2009/04/28 10:51:41 $
 */
public class EntrySingularDetailsPage extends EntryDetailsPage {

	private TableViewer viewer;

	private Table table;

	private POMasterDetailsBlock block;

	private POEntrySingular entry;

	private Text msgIdText;

	private Text msgStrText;

	private Button fuzzyButton;

	private Text commentsText;

	/**
	 * Create the details page
	 * 
	 * @param masterDetailsBlock2
	 */
	public EntrySingularDetailsPage(POMasterDetailsBlock masterDetailsBlock) {
		this.block = masterDetailsBlock;
	}

	/**
	 * Create contents of the details page
	 * 
	 * @param parent
	 */
	public void createContents(Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 2;
		gridLayout.marginHeight = 2;
		gridLayout.horizontalSpacing = 2;
		parent.setLayout(gridLayout);

		final Section entrysingularFormSection = toolkit.createSection(parent,
				ExpandableComposite.EXPANDED | ExpandableComposite.TITLE_BAR);
		final GridData gd_entrysingularFormSection = new GridData(SWT.FILL,
				SWT.FILL, true, true);
		entrysingularFormSection.setLayoutData(gd_entrysingularFormSection);
		entrysingularFormSection.marginWidth = 5;
		entrysingularFormSection.marginHeight = 5;
		entrysingularFormSection.setText("Details");

		final Composite composite = toolkit.createComposite(
				entrysingularFormSection, SWT.NONE);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		composite.setLayout(gridLayout_1);

		final Label msgidLabel = toolkit.createLabel(composite, "msgId",
				SWT.NONE);
		msgidLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));

		msgIdText = toolkit.createText(composite, null, SWT.V_SCROLL
				| SWT.MULTI);
		final GridData gridData_2 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData_2.minimumHeight = 80;
		msgIdText.setLayoutData(gridData_2);
		msgIdText.setEditable(false);

		final Label msgstrLabel = toolkit.createLabel(composite, "msgStr",
				SWT.NONE);
		msgstrLabel
				.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));

		msgStrText = toolkit.createText(composite, null, SWT.V_SCROLL
				| SWT.MULTI);
		final GridData gridData_3 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData_3.minimumHeight = 80;
		msgStrText.setLayoutData(gridData_3);
		msgStrText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent modifyEvent) {
				String msgStr = msgStrText.getText();
				entry.setMsgStr(msgStr);

				if (filled) {
					changeDirty(true);
					// block.refresh();
					block.getPage().getEditor().dataChanged();
				}
			}

		});

		final Label fuzzyLabel = toolkit.createLabel(composite, "Fuzzy",
				SWT.NONE);
		fuzzyLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));

		fuzzyButton = new Button(composite, SWT.CHECK);
		fuzzyButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent arg0) {
				boolean isFuzzy = fuzzyButton.getSelection();

				entry.setFuzzy(isFuzzy);
				if (filled) {
					changeDirty(true);
					// block.refresh();
					block.getPage().getEditor().dataChanged();
				}
			}
		});
		toolkit.adapt(fuzzyButton, true, true);

		final Label commentsLabel = new Label(composite, SWT.NONE);
		commentsLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false,
				false));
		toolkit.adapt(commentsLabel, true, true);
		commentsLabel.setText("Comments");

		commentsText = toolkit.createText(composite, null, SWT.V_SCROLL
				| SWT.MULTI);
		final GridData gridData_1 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData_1.minimumHeight = 80;
		commentsText.setLayoutData(gridData_1);
		commentsText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				final StringTokenizer st = new StringTokenizer(commentsText
						.getText(), "\n");
				final List<String> comments = new ArrayList<String>();
				while (st.hasMoreTokens()) {
					comments.add(st.nextToken().trim());
				}

				entry.setTranslaterComments(comments);

				if (filled) {
					changeDirty(true);
					// block.refresh();
					block.getPage().getEditor().dataChanged();
				}
			}
		});

		final Label referencesLabel = toolkit.createLabel(composite,
				"References", SWT.NONE);
		referencesLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false,
				false));

		table = toolkit.createTable(composite, SWT.FULL_SELECTION
				| SWT.HIDE_SELECTION);
		final GridData gridData_4 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData_4.minimumHeight = 80;
		table.setLayoutData(gridData_4);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		final TableColumn fileColumn = new TableColumn(table, SWT.NONE);
		fileColumn.setWidth(250);
		fileColumn.setText("File");

		final TableColumn lineColumn = new TableColumn(table, SWT.NONE);
		lineColumn.setWidth(100);
		lineColumn.setText("Line");

		toolkit.paintBordersFor(entrysingularFormSection);
		entrysingularFormSection.setClient(composite);
	}

	/**
	 * Updates the view state based upon the entry state.
	 */
	protected void update() {
		if (this.msgIdText != null) {
			filled = false;
			String msgId = entry.getMsgId();
			msgIdText.setText(msgId);

			String msgStr = entry.getMsgStr();
			msgStrText.setText(msgStr);

			boolean fuzzy = entry.isFuzzy();
			fuzzyButton.setSelection(fuzzy);

			this.commentsText.setText(entry.getTranslaterCommentsAsString());

			IFile file = block.getPage().getEditor().getPoEditor().getIFile();
			createReferencesViewer(viewer, entry, table, columnNames, file);

			filled = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse
	 * .ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		entry = (POEntrySingular) structuredSelection.getFirstElement();
		update();
		changeDirty(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IFormPart#refresh()
	 */
	public void refresh() {
		if (entry != null && entry.isDirty()) {
			changeDirty(true);
			entry.setDirty(false);
			block.getPage().getEditor().dataChanged();
		}
		update();
	}

}
