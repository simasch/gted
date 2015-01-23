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
package net.sf.gted.editor.entry.detail.preferences;

import net.sf.gted.model.POReference;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * LabelProvider.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.2 $, $Date: 2008/08/12 11:19:14 $
 */
public class ReferenceTableLabelProvider extends LabelProvider implements
		ITableLabelProvider {

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

		if (obj instanceof POReference) {
			final POReference reference = (POReference) obj;
			switch (index) {
			case 0:
				result = reference.filename;
				break;
			case 1:
				result = "" + reference.line;
				break;
			default:
				break;
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
		return null;
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
}
