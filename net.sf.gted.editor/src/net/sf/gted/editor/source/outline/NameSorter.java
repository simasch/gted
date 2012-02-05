/*
 jTrackAndField - www.jtaf.ch
 Copyright (C) 2006 by Simon Martinelli, Gampelen, Switzerland

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
package net.sf.gted.editor.source.outline;

import net.sf.gted.model.POEntry;

import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * Sorter
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.3 $, $Date: 2008/08/12 13:32:38 $
 */
public class NameSorter extends ViewerSorter {

	@SuppressWarnings("unchecked")
	public int compare(final Viewer viewer, final Object e1, final Object e2) {

		if (e1 instanceof POEntry) {

			final int cat1 = this.category(e1);
			final int cat2 = this.category(e2);
			if (cat1 != cat2) {
				return cat1 - cat2;
			}

			String name1, name2;
			if (viewer == null || !(viewer instanceof ContentViewer)) {
				name1 = e1.toString();
				name2 = e2.toString();
			} else {
				final IBaseLabelProvider prov = ((ContentViewer) viewer)
						.getLabelProvider();
				if (prov instanceof ILabelProvider) {
					final ILabelProvider lprov = (ILabelProvider) prov;
					name1 = lprov.getText(e1);
					name2 = lprov.getText(e2);
				} else {
					name1 = e1.toString();
					name2 = e2.toString();
				}
			}
			if (name1 == null) {
				name1 = ""; //$NON-NLS-1$
			}
			if (name2 == null) {
				name2 = ""; //$NON-NLS-1$
			}
			return this.getComparator().compare(name1, name2);
		} else {
			return super.compare(viewer, e1, e2);
		}
	}

}
