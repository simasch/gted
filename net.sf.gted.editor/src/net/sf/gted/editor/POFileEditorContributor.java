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
package net.sf.gted.editor;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;

/**
 * Manages the installation/deinstallation of global actions for multi-page
 * editors. Responsible for the redirection of global actions to the active
 * editor. Multi-page contributor replaces the contributors for the individual
 * editors in the multi-page editor.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.4 $, $Date: 2008/08/12 11:19:13 $
 */
public class POFileEditorContributor extends
		MultiPageEditorActionBarContributor {

	/** The active editor part. */
	private IEditorPart activeEditorPart;

	/** The sample action. */
	private Action sampleAction;

	/**
	 * Creates a multi-page contributor.
	 */
	public POFileEditorContributor() {
		super();
		this.createActions();
	}

	/**
	 * Returns the action registed with the given text editor.
	 * 
	 * @param actionID
	 *            the action ID
	 * @param editor
	 *            the editor
	 * @return IAction or null if editor is null.
	 */
	protected IAction getAction(final ITextEditor editor, final String actionID) {
		return editor == null ? null : editor.getAction(actionID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.MultiPageEditorActionBarContributor#setActivePage
	 * (org.eclipse.ui.IEditorPart)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.MultiPageEditorActionBarContributor#setActivePage
	 * (org.eclipse.ui.IEditorPart)
	 */
	@Override
	public void setActivePage(final IEditorPart part) {
		if (this.activeEditorPart == part) {
			return;
		}

		this.activeEditorPart = part;

		final IActionBars actionBars = this.getActionBars();
		if (actionBars != null) {

			final ITextEditor editor = part instanceof ITextEditor ? (ITextEditor) part
					: null;

			actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
					this.getAction(editor, ITextEditorActionConstants.DELETE));
			actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), this
					.getAction(editor, ITextEditorActionConstants.UNDO));
			actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), this
					.getAction(editor, ITextEditorActionConstants.REDO));
			actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), this
					.getAction(editor, ITextEditorActionConstants.CUT));
			actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), this
					.getAction(editor, ITextEditorActionConstants.COPY));
			actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), this
					.getAction(editor, ITextEditorActionConstants.PASTE));
			actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),
					this.getAction(editor,
							ITextEditorActionConstants.SELECT_ALL));
			actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(), this
					.getAction(editor, ITextEditorActionConstants.FIND));
			actionBars.setGlobalActionHandler(
					IDEActionFactory.BOOKMARK.getId(), this.getAction(editor,
							IDEActionFactory.BOOKMARK.getId()));
			actionBars.updateActionBars();
		}
	}

	/**
	 * Creates the actions.
	 */
	private void createActions() {
		this.sampleAction = new Action() {

			@Override
			public void run() {
				MessageDialog.openInformation(null, "gted PO File Editor",
						"Generate xy");
			}
		};
		this.sampleAction.setText("Sample Action");
		this.sampleAction.setToolTipText("Sample Action tool tip");
		this.sampleAction.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						IDE.SharedImages.IMG_OBJS_TASK_TSK));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.EditorActionBarContributor#contributeToMenu(org.eclipse
	 * .jface.action.IMenuManager)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.EditorActionBarContributor#contributeToMenu(org.eclipse
	 * .jface.action.IMenuManager)
	 */
	@Override
	public void contributeToMenu(final IMenuManager manager) {
		// final IMenuManager menu = new MenuManager("Editor &Menu");
		// manager.prependToGroup(IWorkbenchActionConstants.MB_ADDITIONS, menu);
		// menu.add(this.sampleAction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.EditorActionBarContributor#contributeToToolBar(org
	 * .eclipse.jface.action.IToolBarManager)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.EditorActionBarContributor#contributeToToolBar(org
	 * .eclipse.jface.action.IToolBarManager)
	 */
	@Override
	public void contributeToToolBar(final IToolBarManager manager) {
		// manager.add(new Separator());
		// manager.add(this.sampleAction);
	}
}
