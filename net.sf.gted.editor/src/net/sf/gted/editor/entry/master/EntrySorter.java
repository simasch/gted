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

import net.sf.gted.model.POEntry;
import net.sf.gted.model.POEntrySingular;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * Sorter.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.4 $, $Date: 2008/08/12 13:32:38 $
 */
class EntrySorter extends ViewerSorter {

	/** The Constant SORT_FUZZY. */
	public static final int SORT_FUZZY = 0;

	/** The Constant SORT_MSG_ID. */
	public static final int SORT_MSG_ID = 1;

	/** The Constant SORT_MSG_STR. */
	public static final int SORT_MSG_STR = 2;

	/** The column. */
	private int column;

	/** The up. */
	private boolean up;

	/**
	 * The Constructor.
	 * 
	 * @param column
	 *            the column
	 * @param up
	 *            the up
	 */
	public EntrySorter(final boolean up, final int column) {
		this.column = column;
		this.up = up;
	}

	/**
	 * Compare.
	 * 
	 * @param viewer
	 *            the viewer
	 * @param e2
	 *            the e2
	 * @param e1
	 *            the e1
	 * @return the int
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int compare(final Viewer viewer, final Object e1, final Object e2) {
		final POEntry entry1 = (POEntry) e1;
		final POEntry entry2 = (POEntry) e2;

		int sort;
		switch (this.column) {
		case 0:
			sort = Boolean.valueOf(entry1.isFuzzy())
					.compareTo(entry2.isFuzzy());
			return this.getSort(sort);
		case 2:
			if (entry1 instanceof POEntrySingular
					&& entry2 instanceof POEntrySingular) {
				sort = this.getComparator().compare(
						((POEntrySingular) entry1).getMsgStr(),
						((POEntrySingular) entry2).getMsgStr());
				return this.getSort(sort);
			}
			// TODO compare correct
			return this.getSort(0);
		default:
			sort = this.getComparator().compare(entry1.getMsgId(),
					entry2.getMsgId());
			return this.getSort(sort);
		}

	}

	/**
	 * Gets the sort.
	 * 
	 * @param sort
	 *            the sort
	 * @return the sort
	 */
	private int getSort(final int sort) {
		if (this.up) {
			return sort * -1;
		} else {
			return sort;
		}
	}
}
