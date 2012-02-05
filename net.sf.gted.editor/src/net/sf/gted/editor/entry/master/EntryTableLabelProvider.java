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
package net.sf.gted.editor.entry.master;

import net.sf.gted.editor.POFileEditorPlugin;
import net.sf.gted.model.POEntry;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * LabelProvider.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.3 $, $Date: 2008/08/12 11:10:58 $
 */
public class EntryTableLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	/** The Constant CHECKED_IMAGE. */
	public static final String CHECKED_IMAGE = "checked";

	/** The Constant UNCHECKED_IMAGE. */
	public static final String UNCHECKED_IMAGE = "unchecked";

	/** The Constant imageRegistry. */
	private static final ImageRegistry imageRegistry = new ImageRegistry();

	/**
	 * Note: An image registry owns all of the image objects registered with it,
	 * and automatically disposes of them the SWT Display is disposed.
	 */
	static {
		final String iconPath = "icons/";
		EntryTableLabelProvider.imageRegistry.put(
				EntryTableLabelProvider.CHECKED_IMAGE, POFileEditorPlugin
						.getImageDescriptor(iconPath
								+ EntryTableLabelProvider.CHECKED_IMAGE
								+ ".gif"));
		EntryTableLabelProvider.imageRegistry.put(
				EntryTableLabelProvider.UNCHECKED_IMAGE, POFileEditorPlugin
						.getImageDescriptor(iconPath
								+ EntryTableLabelProvider.UNCHECKED_IMAGE
								+ ".gif"));
		;
	}

	/**
	 * Gets the column text.
	 * 
	 * @param index
	 *            the index
	 * @param obj
	 *            the obj
	 * @return the column text
	 */
	public String getColumnText(final Object obj, final int index) {
		String result = "";

		if (obj instanceof POEntry) {
			POEntry entry = (POEntry) obj;

			if (index == 1) {
				result = entry.getMsgId();
			} else if (index == 2) {
				result = entry.getMsgStr();
			}
		}
		return result;
	}

	/**
	 * Gets the column image.
	 * 
	 * @param index
	 *            the index
	 * @param obj
	 *            the obj
	 * @return the column image
	 */
	public Image getColumnImage(final Object obj, final int index) {
		final POEntry entry = (POEntry) obj;
		return index == 0 ? this.getImage(entry.isFuzzy()) : null;
	}

	/**
	 * Gets the image.
	 * 
	 * @param obj
	 *            the obj
	 * @return the image
	 */
	@Override
	public Image getImage(final Object obj) {
		return null;
	}

	/**
	 * Returns the image with the given key, or <code>null</code> if not found.
	 * 
	 * @param isSelected
	 *            the is selected
	 * @return the image
	 */
	private Image getImage(final boolean isSelected) {
		final String key = isSelected ? EntryTableLabelProvider.CHECKED_IMAGE
				: EntryTableLabelProvider.UNCHECKED_IMAGE;
		return EntryTableLabelProvider.imageRegistry.get(key);
	}

}
