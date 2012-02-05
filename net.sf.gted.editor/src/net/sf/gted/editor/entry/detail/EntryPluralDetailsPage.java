/*
 gted - gted.sourceforge.net
 Copyright (C) 2007 by Simon Martinelli, Gampelen, Switzerland

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
import net.sf.gted.model.POEntryPlural;
import net.sf.gted.model.POFile;

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
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * EntrypluralDetailsPage.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.10 $, $Date: 2008/08/12 11:19:07 $
 */
public class EntryPluralDetailsPage extends EntryDetailsPage {

	/** The column names. */
	private final String[] columnNames = { "File", "Line" };

	private IManagedForm managedForm;

	private TableViewer viewer;

	private Table table;

	private POMasterDetailsBlock block;

	private POEntryPlural entry;

	private Text msgIdText;

	private Text msgStr0Text;

	private Text msgStr1Text;

	private Text msgStr2Text;

	private Text msgStr3Text;

	private Button fuzzyButton;

	private Text commentsText;

	/**
	 * Create the details page
	 * 
	 * @param masterDetailsBlock2
	 */
	public EntryPluralDetailsPage(POMasterDetailsBlock masterDetailsBlock2) {
		this.block = masterDetailsBlock2;
	}

	/**
	 * Initialize the details page
	 * 
	 * @param form
	 */
	public void initialize(IManagedForm form) {
		managedForm = form;
	}

	/**
	 * Create contents of the details page
	 * 
	 * @param parent
	 */
	public void createContents(Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		final TableWrapLayout tableWrapLayout = new TableWrapLayout();
		tableWrapLayout.verticalSpacing = 2;
		tableWrapLayout.topMargin = 2;
		tableWrapLayout.rightMargin = 2;
		tableWrapLayout.leftMargin = 2;
		tableWrapLayout.horizontalSpacing = 2;
		tableWrapLayout.bottomMargin = 2;
		parent.setLayout(tableWrapLayout);

		final Section entrypluralFormSection = toolkit.createSection(parent,
				Section.EXPANDED | Section.TITLE_BAR);
		final TableWrapData twd_entrypluralFormSection = new TableWrapData(
				TableWrapData.FILL, TableWrapData.FILL);
		twd_entrypluralFormSection.grabVertical = true;
		twd_entrypluralFormSection.grabHorizontal = true;
		entrypluralFormSection.setLayoutData(twd_entrypluralFormSection);
		entrypluralFormSection.marginWidth = 5;
		entrypluralFormSection.marginHeight = 5;
		entrypluralFormSection.setText("Details");

		final Composite composite = toolkit.createComposite(
				entrypluralFormSection, SWT.NONE);
		toolkit.adapt(composite);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		composite.setLayout(gridLayout_1);

		final Label msgidLabel = toolkit.createLabel(composite, "msgId",
				SWT.NONE);
		msgidLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));

		msgIdText = toolkit.createText(composite, null, SWT.V_SCROLL
				| SWT.MULTI);
		final GridData gridData_2 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData_2.minimumHeight = 50;
		msgIdText.setLayoutData(gridData_2);
		msgIdText.setEditable(false);

		final Label msgstr0Label = toolkit.createLabel(composite, "msgStr[0]",
				SWT.NONE);
		msgstr0Label
				.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true));

		msgStr0Text = toolkit.createText(composite, null, SWT.V_SCROLL
				| SWT.MULTI);
		msgStr0Text.setVisible(false);
		final GridData gridData_7 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData_7.minimumHeight = 50;
		msgStr0Text.setLayoutData(gridData_7);
		msgStr0Text.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent modifyEvent) {
				modifyEntry(0, msgStr0Text.getText());
			}
		});

		final Label msgstr1Label = toolkit.createLabel(composite, "msgStr[1]",
				SWT.NONE);
		msgstr1Label
				.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true));

		msgStr1Text = toolkit.createText(composite, null, SWT.V_SCROLL
				| SWT.MULTI);
		msgStr1Text.setVisible(false);
		final GridData gridData_6 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData_6.minimumHeight = 50;
		msgStr1Text.setLayoutData(gridData_6);
		msgStr1Text.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				modifyEntry(1, msgStr1Text.getText());
			}
		});

		final Label msgstr2Label = toolkit.createLabel(composite, "msgStr[2]",
				SWT.NONE);
		msgstr2Label
				.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true));

		msgStr2Text = toolkit.createText(composite, null, SWT.V_SCROLL
				| SWT.MULTI);
		msgStr2Text.setVisible(false);
		final GridData gridData_5 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData_5.minimumHeight = 50;
		msgStr2Text.setLayoutData(gridData_5);
		msgStr2Text.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				modifyEntry(2, msgStr2Text.getText());
			}
		});

		final Label msgstr3Label = toolkit.createLabel(composite, "msgStr[3]",
				SWT.NONE);
		msgstr3Label
				.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));

		final GridData gridData_3 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData_3.minimumHeight = 50;
		msgStr3Text = toolkit.createText(composite, null, SWT.V_SCROLL
				| SWT.MULTI);
		msgStr3Text.setVisible(false);
		msgStr3Text.setLayoutData(gridData_3);
		msgStr3Text.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				modifyEntry(3, msgStr3Text.getText());
			}
		});

		final Label fuzzyLabel = toolkit.createLabel(composite, "Fuzzy",
				SWT.NONE);
		fuzzyLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true));

		fuzzyButton = new Button(composite, SWT.CHECK);
		fuzzyButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				true));
		fuzzyButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent arg0) {
				boolean isFuzzy = fuzzyButton.getSelection();
				entry.setFuzzy(isFuzzy);

				if (filled) {
					changeDirty(true);
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
		gridData_1.minimumHeight = 50;
		commentsText.setLayoutData(gridData_1);
		commentsText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				final StringTokenizer st = new StringTokenizer(commentsText
						.getText(), "\n");
				final List<String> comments = new ArrayList<String>();
				while (st.hasMoreTokens()) {
					comments.add(st.nextToken().trim());
				}
				if (filled) {
					entry.setTranslaterComments(comments);

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
		gridData_4.minimumHeight = 50;
		table.setLayoutData(gridData_4);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		final TableColumn fileColumn = new TableColumn(table, SWT.NONE);
		fileColumn.setWidth(250);
		fileColumn.setText("File");

		final TableColumn lineColumn = new TableColumn(table, SWT.NONE);
		lineColumn.setWidth(100);
		lineColumn.setText("Line");

		toolkit.paintBordersFor(entrypluralFormSection);
		entrypluralFormSection.setClient(composite);
	}

	/**
   * 
   */
	protected void update() {
		if (this.msgIdText != null) {
			filled = false;

			String msgId = entry.getMsgId();
			this.msgIdText.setText(msgId);

			this.msgStr3Text.setVisible(false);
			this.msgStr2Text.setVisible(false);
			this.msgStr1Text.setVisible(false);
			this.msgStr0Text.setVisible(false);

			POFile file = entry.getFile();
			if (file.getNplural() > 3) {
				this.msgStr3Text.setText(entry.getMsgStr(3));
				this.msgStr3Text.setVisible(true);
			}
			if (file.getNplural() > 2) {
				this.msgStr2Text.setText(entry.getMsgStr(2));
				this.msgStr2Text.setVisible(true);
			}
			if (file.getNplural() > 1) {
				this.msgStr1Text.setText(entry.getMsgStr(1));
				this.msgStr1Text.setVisible(true);
			}
			if (file.getNplural() > 0) {
				this.msgStr0Text.setText(entry.getMsgStr(0));
				this.msgStr0Text.setVisible(true);
			}

			this.fuzzyButton.setSelection(entry.isFuzzy());

			this.commentsText.setText(entry.getTranslaterCommentsAsString());

			IFile iFile = block.getPage().getEditor().getPoEditor().getIFile();
			createReferencesViewer(viewer, entry, table, columnNames, iFile);

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
		entry = (POEntryPlural) structuredSelection.getFirstElement();
		update();
		changeDirty(false);
	}

	private void modifyEntry(int pluralNumber, String text) {
		POFile file = entry.getFile();

		if (file.getNplural() > pluralNumber) {
			entry.setMsgStr(pluralNumber, text);

			if (filled) {
				changeDirty(true);
				block.getPage().getEditor().dataChanged();
			}
		}
	}
}
