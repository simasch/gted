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
package net.sf.gted.editor.entry;

import net.sf.gted.editor.POFormEditor;
import net.sf.gted.editor.entry.master.POMasterDetailsBlock;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * POMasterDetailsPage.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.5 $, $Date: 2008/08/12 11:19:15 $
 */
public class POMasterDetailsPage extends FormPage {

	private POMasterDetailsBlock block;

	private POFormEditor editor;

	boolean dirty = false;

	/**
	 * @param editor
	 */
	public POMasterDetailsPage(POFormEditor editor) {
		super(editor, "net.sf.gted.editor.forms.POMasterDetailsPage", "Entries");
		this.editor = editor;
		block = new POMasterDetailsBlock(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui
	 * .forms.IManagedForm)
	 */
	protected void createFormContent(final IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		form.setText("Entries Editor");
		block.createContent(managedForm);
	}

	public POFormEditor getEditor() {
		return editor;
	}

	public void refresh() {
		this.block.refresh();
	}
}
