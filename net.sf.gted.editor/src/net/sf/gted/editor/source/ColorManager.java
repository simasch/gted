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
package net.sf.gted.editor.source;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * The Class ColorManager.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.3 $, $Date: 2008/08/12 11:19:04 $
 */
public class ColorManager {

	/** The f color table. */
	protected Map<RGB, Color> fColorTable = new HashMap<RGB, Color>(10);

	/**
	 * Dispose.
	 */
	public void dispose() {
		for (Color color : this.fColorTable.values()) {
			color.dispose();
		}
	}

	/**
	 * Gets the color.
	 * 
	 * @param rgb
	 *            the rgb
	 * @return the color
	 */
	public Color getColor(final RGB rgb) {
		Color color = (Color) this.fColorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			this.fColorTable.put(rgb, color);
		}
		return color;
	}
}
