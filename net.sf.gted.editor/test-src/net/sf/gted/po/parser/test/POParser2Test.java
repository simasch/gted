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
package net.sf.gted.po.parser.test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.sf.gted.model.POEntry;
import net.sf.gted.model.POEntryPlural;
import net.sf.gted.model.POEntrySingular;
import net.sf.gted.model.POFile;
import net.sf.gted.parsers.POParser2;

/**
 * The Class POParserTest.
 */
public class POParser2Test extends TestCase {

	/**
	 * Test parse comment.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testParseComment() throws Exception {
		final StringWriter writer = new StringWriter();
		final PrintWriter out = new PrintWriter(writer);

		out.println("# SOME DESCRIPTIVE TITLE.");
		out.println("# Copyright (C) YEAR Free Software Foundation, Inc.");
		out.println("# FIRST AUTHOR <EMAIL@ADDRESS>, YEAR.");
		out.println("#");
		out.println("#, fuzzy");

		out.println("msgid \"\"");
		out.println("msgstr \"\"");
		out.println("\"Plural-Forms: nplurals=0; null;\\n\"");

		writer.flush();

		final Reader reader = new StringReader(writer.toString());
		final POFile callback = new POFile();

		final POParser2 parser = new POParser2(callback, new TestErrorHandler());
		parser.parse(reader);

		final String one = writer.toString();
		final String two = callback.toString();

		System.out.println("----");
		System.out.println(one.trim());
		System.out.println("----");
		System.out.println(two.trim());

		Assert.assertEquals(one.trim(), two.trim());
	}

	/**
	 * Test parse header.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testParseHeader() throws Exception {
		final StringWriter writer = new StringWriter();
		final PrintWriter out = new PrintWriter(writer);

		out.println("#");
		out.println("msgid \"\"");
		out.println("msgstr \"\"");
		out.println("\"Project-Id-Version: PACKAGE VERSION\\n\"");
		out.println("\"POT-Creation-Date: 2001-02-09 01:25+0100\\n\"");

		writer.flush();

		final Reader reader = new StringReader(writer.toString());

		final POFile callback = new POFile();
		final POParser2 parser = new POParser2(callback, new TestErrorHandler());
		parser.parse(reader);

		Assert.assertEquals(2, callback.getHeaders().size());

		Assert.assertTrue(callback.getHeaders().containsKey(
				"Project-Id-Version"));
		Assert.assertEquals("PACKAGE VERSION", callback.getHeaders().get(
				"Project-Id-Version"));

		Assert.assertTrue(callback.getHeaders()
				.containsKey("POT-Creation-Date"));
		Assert.assertEquals("2001-02-09 01:25+0100", callback.getHeaders().get(
				"POT-Creation-Date"));
	}

	/**
	 * Test parse entry.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testParseEntry() throws Exception {
		final StringWriter writer = new StringWriter();
		final PrintWriter out = new PrintWriter(writer);

		out.println("#");
		out.println("msgid \"\"");
		out.println("msgstr \"\"");
		out.println("\"Project-Id-Version: PACKAGE VERSION\\n\"");
		out.println("\"POT-Creation-Date: 2001-02-09 01:25+0100\\n\"");
		out.println();
		out.println("#: gpl.xml:11");
		out.println("#: gpl.xml:8");
		out.println("#, no-c-format");
		out.println("#. Tag: title");
		out.println("msgid \"GNU General Public License\"");
		out.println("msgstr \"test\"");
		out.println();
		out.println("#: gpl.xml:15;20");
		out.println("#, no-c-format");
		out.println("#, fuzzy");
		out.println("msgid \"Free Software Foundation, Inc.\"");
		out.println("msgstr \"test2\"");
		out.println();
		out.println("#: gpl.xml:50");
		out.println("#: ;80");
		out.println("#, no-c-format");
		out.println("#, fuzzy");
		out.println("msgid \"Free Software Foundation, Inc.\"");
		out.println("msgstr \"test3\"");
		out.println();
		out.println("#: gpl.xml:-");
		out.println("#: ;80");
		out.println("#, no-c-format");
		out.println("#, fuzzy");
		out.println("msgid \"Free Software Foundation, Inc.\"");
		out.println("msgstr \"test4\"");

		writer.flush();

		final Reader reader = new StringReader(writer.toString());

		final POFile callback = new POFile();
		final POParser2 parser = new POParser2(callback, new TestErrorHandler());
		parser.parse(reader);

		Assert.assertEquals(4, callback.getEntries().size());

		POEntry entry = callback.getEntries().get(0);
		Assert.assertFalse(entry.isFuzzy());

		Assert.assertEquals("gpl.xml:11", entry.getReferences().get(0)
				.toString());
		Assert.assertEquals("gpl.xml:8", entry.getReferences().get(1)
				.toString());
		Assert.assertEquals("GNU General Public License", entry.getMsgId());
		Assert.assertEquals("test", ((POEntrySingular) entry).getMsgStr());

		entry = callback.getEntries().get(1);
		Assert.assertTrue(entry.isFuzzy());
		Assert.assertEquals("gpl.xml:15", entry.getReferences().get(0)
				.toString());
		Assert.assertEquals("gpl.xml:20", entry.getReferences().get(1)
				.toString());
		Assert.assertEquals("Free Software Foundation, Inc.", entry.getMsgId());
		Assert.assertEquals("test2", ((POEntrySingular) entry).getMsgStr());

		entry = callback.getEntries().get(2);
		Assert.assertTrue(entry.isFuzzy());
		Assert.assertEquals("gpl.xml:50", entry.getReferences().get(0)
				.toString());
		Assert.assertEquals("gpl.xml:80", entry.getReferences().get(1)
				.toString());
		Assert.assertEquals("Free Software Foundation, Inc.", entry.getMsgId());
		Assert.assertEquals("test3", ((POEntrySingular) entry).getMsgStr());

		entry = callback.getEntries().get(3);
		Assert.assertTrue(entry.isFuzzy());
		Assert.assertEquals("gpl.xml:-", entry.getReferences().get(0)
				.toString());
		Assert.assertEquals("gpl.xml:80", entry.getReferences().get(1)
				.toString());
		Assert.assertEquals("Free Software Foundation, Inc.", entry.getMsgId());
		Assert.assertEquals("test4", ((POEntrySingular) entry).getMsgStr());
	}

	/**
	 * Test parse plural entry.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testParsePluralEntry() throws Exception {
		final StringWriter writer = new StringWriter();
		final PrintWriter out = new PrintWriter(writer);

		out.println("#");
		out.println("msgid \"\"");
		out.println("msgstr \"\"");
		out.println("\"Project-Id-Version: PACKAGE VERSION\\n\"");
		out.println("\"POT-Creation-Date: 2001-02-09 01:25+0100\\n\"");
		out.println("\"Plural-Forms: nplurals=2; plural=(n != 1);\\n\"");
		out.println();
		out.println("#: gpl.xml:11 gpl.xml:30");
		out.println("#, no-c-format");
		out.println("#. Tag: title");
		out.println("msgid \"GNU General Public License\"");
		out.println("msgid_plural \"GNU General Public Licenses\"");
		out.println("msgstr[0] \"test1\"");
		out.println("msgstr[1] \"test2\"");

		writer.flush();

		final Reader reader = new StringReader(writer.toString());
		final POFile callback = new POFile();

		final POParser2 parser = new POParser2(callback, new TestErrorHandler());
		parser.parse(reader);

		Assert.assertEquals(2, callback.getNplural());
		Assert.assertEquals("plural=(n != 1)", callback.getPluralExpression());
		Assert.assertEquals(1, callback.getEntries().size());

		final POEntry entry = callback.getEntries().get(0);
		Assert.assertEquals("GNU General Public Licenses",
				((POEntryPlural) entry).getMsgIdPlural());
		Assert.assertNotNull(((POEntryPlural) entry).getMsgStrs());
		Assert.assertEquals(2, ((POEntryPlural) entry).getMsgStrs().size());
		Assert.assertEquals("test1", ((POEntryPlural) entry).getMsgStrs()
				.get(0));
		Assert.assertEquals("test2", ((POEntryPlural) entry).getMsgStrs()
				.get(1));
	}

	/**
	 * Test string normalization.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testStringNormalization() throws Exception {
		final StringWriter writer = new StringWriter();
		final PrintWriter out = new PrintWriter(writer);

		out.println("#");
		out.println("msgid \"\"");
		out.println("msgstr \"\"");
		out.println("\"Project-Id-Version: PACKAGE VERSION\\n\"");
		out.println();
		out.println("#: gpl.xml:11 gpl.xml:30");
		out.println("msgid \"GNU \\nGeneral \\tPublic\\n \"License\"\"");
		out.println("msgstr \"test\"");

		writer.flush();

		final Reader reader = new StringReader(writer.toString());

		final POFile callback = new POFile();
		final POParser2 parser = new POParser2(callback, new TestErrorHandler());
		parser.parse(reader);

		Assert.assertFalse(callback.getEntries().isEmpty());

		final POEntry entry = callback.getEntries().get(0);
		Assert.assertEquals("GNU \\nGeneral \\tPublic\\n \"License\"", entry
				.getMsgId());
	}

	/**
	 * Test to string.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testToString() throws Exception {
		final StringWriter writer = new StringWriter();
		final PrintWriter out = new PrintWriter(writer);

		out.println("#, fuzzy");
		out.println("msgid \"\"");
		out.println("msgstr \"\"");
		out.println("\"Project-Id-Version: PACKAGE VERSION\\n\"");
		out.println("\"POT-Creation-Date: 2001-02-09 01:25+0100\\n\"");
		out.println("\"Plural-Forms: nplurals=2; plural=0;\\n\"");
		out.println();
		out.println("#: gpl.xml:11");
		out.println("#. Tag: title");
		out.println("# Comment");
		out.println("#, no-c-format");
		out.println("msgid \"GNU General Public License\"");
		out.println("msgstr \"test1\"");
		out.println("");
		out.println("#: gpl.xml:11");
		out.println("#: gpl.xml:30");
		out.println("#. Tag: title");
		out.println("# Comment");
		out.println("#, no-c-format");
		out.println("msgid \"Multiline\"");
		// Not supported yet :-(
		// out.println("msgstr \"\"");
		out
				.println("msgstr \"Here is an example of how one might continue a very long string\\n\"");
		out
				.println("\"for the common case the string represents multi-line output.\\n\"");

		writer.flush();

		final Reader reader = new StringReader(writer.toString());
		final POFile callback = new POFile();

		final POParser2 parser = new POParser2(callback, new TestErrorHandler());
		parser.parse(reader);

		final String one = writer.toString();
		final String two = callback.toString();

		System.out.println("----");
		System.out.println(one);
		System.out.println("----");
		System.out.println(two);

		Assert.assertEquals(one.trim(), two.trim());
	}

	/**
	 * Test to string plural.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testToStringPlural() throws Exception {
		final StringWriter writer = new StringWriter();
		final PrintWriter out = new PrintWriter(writer);

		out.println("#, fuzzy");
		out.println("msgid \"\"");
		out.println("msgstr \"\"");
		out.println("\"Project-Id-Version: PACKAGE VERSION\\n\"");
		out.println("\"POT-Creation-Date: 2001-02-09 01:25+0100\\n\"");
		out.println("\"Plural-Forms: nplurals=2; plural=(n != 1);\\n\"");
		out.println();
		out.println("#: gpl.xml:11");
		out.println("#. Tag: title");
		out.println("# Comment");
		out.println("#, no-c-format");
		out.println("msgid \"GNU General Public License\"");
		out.println("msgid_plural \"GNU General Public Licenses\"");
		out.println("msgstr[0] \"test1\"");
		out.println("msgstr[1] \"test2\"");
		out.println("");

		writer.flush();

		final Reader reader = new StringReader(writer.toString());
		final POFile callback = new POFile();

		final POParser2 parser = new POParser2(callback, new TestErrorHandler());
		parser.parse(reader);

		final String one = writer.toString();
		final String two = callback.toString();

		System.out.println("----");
		System.out.println(one);
		System.out.println("----");
		System.out.println(two);

		Assert.assertEquals(one, two);
	}

	/**
	 * Test from file.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testFromFile() throws Exception {
		final InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("net/sf/gted/po/parser/test/po/test.po");
		final Reader reader = new InputStreamReader(inputStream);

		final POFile callback = new POFile();

		final POParser2 parser = new POParser2(callback, new TestErrorHandler());
		parser.parse(reader);

		Assert.assertEquals(2, callback.getNplural());
		Assert.assertEquals("plural=(n != 1)", callback.getPluralExpression());
		Assert.assertEquals(3, callback.getEntries().size());

		final POEntry entry = callback.getEntries().get(2);
		Assert.assertEquals("GNU General Public Licenses",
				((POEntryPlural) entry).getMsgIdPlural());
		Assert.assertNotNull(((POEntryPlural) entry).getMsgStrs());
		Assert.assertEquals(2, ((POEntryPlural) entry).getMsgStrs().size());
		Assert.assertEquals("test1", ((POEntryPlural) entry).getMsgStrs()
				.get(0));
		Assert.assertEquals("test2", ((POEntryPlural) entry).getMsgStrs()
				.get(1));
	}
}
