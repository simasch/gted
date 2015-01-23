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
package net.sf.gted.parsers;

import net.sf.gted.model.POEntry;

/**
 * IPOParserCallback.java
 * 
 * @author Xavier Cho
 * @version $Revision 1.1 $ $Date: 2008/08/12 11:19:06 $
 */
public interface IPOParserCallback {

	/**
	 * Start document.
	 */
	void startDocument();

	/**
	 * On comment.
	 * 
	 * @param comment
	 *            the comment
	 */
	void onComment(String comment);

	/**
	 * On header.
	 * 
	 * @param value
	 *            the value
	 * @param name
	 *            the name
	 */
	void onHeader(String name, String value);

	/**
	 * On header plural form.
	 * 
	 * @param nplural
	 *            the nplural
	 * @param expression
	 *            the expression
	 */
	void onHeaderPluralForm(int nplural, String expression);

	/**
	 * On entry.
	 * 
	 * @param entry
	 *            the entry
	 */
	void onEntry(POEntry entry);

	/**
	 * End document.
	 */
	void endDocument();
}
