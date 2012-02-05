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
package net.sf.gted.editor.preferences;

import net.sf.gted.editor.POFileEditorPlugin;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.11 $, $Date: 2009/09/22 11:24:26 $
 */

public class EditorPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private String[][] namesAndValues = new String[][] {
			{ "Ignore", PreferenceConstants.P_IGNORE },
			{ "Info", PreferenceConstants.P_INFO },
			{ "Warning", PreferenceConstants.P_WARN },
			{ "Error", PreferenceConstants.P_ERROR } };

	public EditorPreferencePage() {
		super(FieldEditorPreferencePage.GRID);
		this.setPreferenceStore(POFileEditorPlugin.getDefault()
				.getPreferenceStore());
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {

		RadioGroupFieldEditor orientationPref = new RadioGroupFieldEditor(
				PreferenceConstants.P_ORIENTATION, "Editor orientation", 2,
				new String[][] {
						{ "&Horizontal",
								PreferenceConstants.P_ORIENTATION_HORIZONTAL },
						{ "&Vertical",
								PreferenceConstants.P_ORIENTATION_VERTICAL } },
				this.getFieldEditorParent(), true);
		this.addField(orientationPref);

		RadioGroupFieldEditor editorPref = new RadioGroupFieldEditor(
				PreferenceConstants.P_EDITOR_VIEW, "Inital editor view", 2,
				new String[][] {
						{ "&Rich user interface",
								PreferenceConstants.P_EDITOR_VIEW_UI },
						{ "&Source editor",
								PreferenceConstants.P_EDITOR_VIEW_SOURCE } },
				this.getFieldEditorParent(), true);
		this.addField(editorPref);

		ComboFieldEditor untranslatedPref = new ComboFieldEditor(
				PreferenceConstants.P_UNTRANSLATED,
				"If PO file contains untranslated entries                       ",
				namesAndValues, this.getFieldEditorParent());
		this.addField(untranslatedPref);

		ComboFieldEditor fuzzyPref = new ComboFieldEditor(
				PreferenceConstants.P_FUZZY,
				"If PO file contains fuzzy entries   ", namesAndValues, this
						.getFieldEditorParent());
		this.addField(fuzzyPref);
		
		StringFieldEditor sourceRefPrefixPref = new StringFieldEditor(
				PreferenceConstants.P_SOURCE_REFERENCE_PREFIX,
				"Source reference prefix\ne.g. src/main, (use forward slashes)",
				this
						.getFieldEditorParent());
		sourceRefPrefixPref.setEmptyStringAllowed(true);
		this.addField(sourceRefPrefixPref);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(final IWorkbench workbench) {
	}

}