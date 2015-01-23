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

import java.util.List;

import net.sf.gted.model.POEntry;
import net.sf.gted.model.POFile;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * ContentProvider.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.2 $, $Date: 2008/08/12 11:19:12 $
 */
public class EntryTableContentProvider implements IStructuredContentProvider {

	/**
	 * Input changed.
	 * 
	 * @param newInput
	 *            the new input
	 * @param oldInput
	 *            the old input
	 * @param v
	 *            the v
	 */
	public void inputChanged(final Viewer v, final Object oldInput,
			final Object newInput) {
	}

	/**
	 * Dispose.
	 */
	public void dispose() {
	}

	/**
	 * Gets the elements.
	 * 
	 * @param parent
	 *            the parent
	 * @return the elements
	 */
	public Object[] getElements(final Object parent) {

		final POFile file = (POFile) parent;
		final List<POEntry> list = file.getEntries();
		if (list.size() == 0) {
			return new Object[0];
		} else {
			return list.toArray();
		}
	}
}
