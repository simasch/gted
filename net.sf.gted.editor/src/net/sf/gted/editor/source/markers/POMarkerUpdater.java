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
package net.sf.gted.editor.source.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.ui.texteditor.IMarkerUpdater;

/**
 * POMarkerUpdater.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.4 $, $Date: 2008/08/12 11:19:15 $
 */
public class POMarkerUpdater implements IMarkerUpdater {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.IMarkerUpdater#getAttribute()
	 */
	public String[] getAttribute() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.IMarkerUpdater#getMarkerType()
	 */
	public String getMarkerType() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.texteditor.IMarkerUpdater#updateMarker(org.eclipse.core
	 * .resources.IMarker, org.eclipse.jface.text.IDocument,
	 * org.eclipse.jface.text.Position)
	 */
	public boolean updateMarker(final IMarker marker, final IDocument document,
			final Position position) {
		return true;
	}

}
