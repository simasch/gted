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
package net.sf.gted.tools.properties;

import org.eclipse.core.resources.IContainer;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.NewFolderDialog;

/**
 * @author $Author: simas_ch $
 * @version $Revision: 1.5 $, $Date: 2008/08/12 11:20:28 $
 */
public class FolderSelectionDialog extends ElementTreeSelectionDialog implements
		ISelectionChangedListener {

	private Button fNewFolderButton;

	private IContainer fSelectedContainer;

	/**
	 * @param parent
	 * @param labelProvider
	 * @param contentProvider
	 */
	public FolderSelectionDialog(final Shell parent,
			final ILabelProvider labelProvider,
			final ITreeContentProvider contentProvider) {
		super(parent, labelProvider, contentProvider);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite result = (Composite) super.createDialogArea(parent);

		this.getTreeViewer().addSelectionChangedListener(this);
		this.getTreeViewer().expandToLevel(2);
		this.fNewFolderButton = new Button(result, SWT.PUSH);
		this.fNewFolderButton.setText("Create New Folder...");
		this.fNewFolderButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent event) {
				FolderSelectionDialog.this.newFolderButtonPressed();
			}
		});
		this.fNewFolderButton.setFont(parent.getFont());
		this.fNewFolderButton.setLayoutData(new GridData());

		Dialog.applyDialogFont(result);
		return result;
	}

	private void updateNewFolderButtonState() {
		final IStructuredSelection selection = (IStructuredSelection) this
				.getTreeViewer().getSelection();
		this.fSelectedContainer = null;
		if (selection.size() == 1) {
			final Object first = selection.getFirstElement();
			if (first instanceof IContainer) {
				this.fSelectedContainer = (IContainer) first;
			}
		}
		this.fNewFolderButton.setEnabled(this.fSelectedContainer != null);
	}

	/**
   * 
   */
	protected void newFolderButtonPressed() {
		final NewFolderDialog dialog = new NewFolderDialog(this.getShell(),
				this.fSelectedContainer);
		if (dialog.open() == Window.OK) {
			final TreeViewer treeViewer = this.getTreeViewer();
			treeViewer.refresh(this.fSelectedContainer);
			Object createdFolder;
			if (dialog.getResult() != null) {
				createdFolder = dialog.getResult()[0];
				treeViewer.reveal(createdFolder);
				treeViewer.setSelection(new StructuredSelection(createdFolder));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(
	 * org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	public void selectionChanged(final SelectionChangedEvent event) {
		this.updateNewFolderButtonState();
	}

}
