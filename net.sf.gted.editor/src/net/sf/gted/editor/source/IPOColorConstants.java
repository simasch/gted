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

import org.eclipse.swt.graphics.RGB;

/**
 * The Interface IPOColorConstants.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.2 $, $Date: 2008/08/12 11:19:05 $
 */
public interface IPOColorConstants {

	/** The P o_ COMMENT. */
	RGB PO_COMMENT = new RGB(128, 0, 0);

	/** The P o_ STRING. */
	RGB PO_STRING = new RGB(0, 128, 0);

	/** The P o_ KEYWORD. */
	RGB PO_KEYWORD = new RGB(0, 0, 128);

	/** The DEFAULT. */
	RGB DEFAULT = new RGB(0, 0, 0);
}
