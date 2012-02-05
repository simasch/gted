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
package net.sf.gted.po.parser.test;

import net.sf.gted.editor.source.IErrorHandler;

/**
 * The Class TestErrorHandler.
 */
public class TestErrorHandler implements IErrorHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.gted.editor.editors.IErrorHandler#addError(java.lang.String,
	 * int, int)
	 */
	public void addError(final String error, final int lineNumber,
			final int columnNumber) {
		System.err.println("Error: " + error + " at " + lineNumber + ":"
				+ columnNumber);
	}

}
