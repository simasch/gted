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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import junit.framework.TestCase;
import net.sf.gted.model.POFile;
import net.sf.gted.parsers.POParser2;

/**
 * The Class POParserTest.
 */
public class BugSearchPOParser2Test extends TestCase {

	/**
	 * Test from file.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testFromFile() throws Exception {
		final InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(
						"net/sf/gted/po/parser/test/po/messages.po");
		final Reader reader = new InputStreamReader(inputStream);

		final POFile callback = new POFile();

		final POParser2 parser = new POParser2(callback, new TestErrorHandler());
		parser.parse(reader);

		System.out.println(callback.toString());
	}
}