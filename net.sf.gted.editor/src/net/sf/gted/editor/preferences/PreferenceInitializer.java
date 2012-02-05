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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.8 $, $Date: 2009/09/22 11:24:26 $
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		final IPreferenceStore store = POFileEditorPlugin.getDefault()
				.getPreferenceStore();

		store.setDefault(PreferenceConstants.P_UNTRANSLATED,
				PreferenceConstants.P_IGNORE);

		store.setDefault(PreferenceConstants.P_FUZZY,
				PreferenceConstants.P_IGNORE);

		store.setDefault(PreferenceConstants.P_ORIENTATION,
				PreferenceConstants.P_ORIENTATION_HORIZONTAL);

		store.setDefault(PreferenceConstants.P_EDITOR_VIEW,
				PreferenceConstants.P_EDITOR_VIEW_UI);

		store.setDefault(PreferenceConstants.P_SOURCE_REFERENCE_PREFIX, "");
	}

}
