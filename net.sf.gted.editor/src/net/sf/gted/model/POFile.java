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
package net.sf.gted.model;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import net.sf.gted.parsers.IPOParserCallback;

/**
 * POFile
 * 
 * @author Xavier Cho
 * @version $Revision 1.1 $ $Date: 2009/02/24 07:56:46 $
 */
public class POFile implements IPOParserCallback {

	/**
	 * The nplural.
	 * 
	 * @uml.property name="nplural"
	 */
	private int nplural;

	/**
	 * The plural expression.
	 * 
	 * @uml.property name="pluralExpression"
	 */
	private String pluralExpression;

	/**
	 * The comment.
	 * 
	 * @uml.property name="comment"
	 */
	private List<String> comments;

	/**
	 * The headers.
	 * 
	 * @uml.property name="headers"
	 * @uml.associationEnd qualifier="key:java.lang.Object java.lang.String"
	 */
	private Map<String, String> headers;

	/** The entries. */
	private List<POEntry> entries;

	/**
	 * The Constructor.
	 */
	public POFile() {
		this.init();
	}

	/**
	 * Init.
	 */
	public void init() {
		this.comments = new ArrayList<String>();
		this.headers = new LinkedHashMap<String, String>();
		this.entries = new ArrayList<POEntry>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.gted.parsers.IPOParserCallback#startDocument()
	 */
	public void startDocument() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.gted.parsers.IPOParserCallback#onComment(java.lang.String)
	 */
	public void onComment(final String str) {
		this.comments.add(str);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.gted.parsers.IPOParserCallback#onHeader(java.lang.String,
	 * java.lang.String)
	 */
	public void onHeader(final String name, final String value) {
		this.headers.put(name, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.gted.parsers.IPOParserCallback#onHeaderPluralForm(int,
	 * java.lang.String)
	 */
	public void onHeaderPluralForm(final int nplural, final String expression) {
		this.nplural = nplural;
		this.pluralExpression = expression;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seenet.sf.gted.parsers.IPOParserCallback#onEntry(net.sf.gted.parsers.
	 * POParserEntry)
	 */
	public void onEntry(final POEntry entry) {
		entry.setFile(this);
		this.entries.add(entry);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.gted.parsers.IPOParserCallback#endDocument()
	 */
	public void endDocument() {
	}

	/**
	 * Gets the entries.
	 * 
	 * @return the entries
	 * @uml.property name="entries"
	 */
	public List<POEntry> getEntries() {
		return this.entries;
	}

	/**
	 * Gets the headers.
	 * 
	 * @return the headers
	 * @uml.property name="headers"
	 */
	public Map<String, String> getHeaders() {
		return this.headers;
	}

	/**
	 * Gets the comment.
	 * 
	 * @return the comment
	 * @uml.property name="comment"
	 */
	public List<String> getComments() {
		return this.comments;
	}

	/**
	 * Gets the nplural.
	 * 
	 * @return the nplural
	 * @uml.property name="nplural"
	 */
	public int getNplural() {
		return this.nplural;
	}

	/**
	 * Gets the plural expression.
	 * 
	 * @return the plural expression
	 * @uml.property name="pluralExpression"
	 */
	public String getPluralExpression() {
		return this.pluralExpression;
	}

	public boolean hasFuzzyEntries() {
		for (final POEntry entry : this.entries) {
			if (entry.isFuzzy()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasUntranslatedEntries() {
		for (final POEntry entry : this.entries) {
			if (entry.isUntranslated()) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringWriter writer = new StringWriter();
		final PrintWriter out = new PrintWriter(writer);

		// Comment
		for (String comment : comments) {
			out.println(comment);
		}

		// Header
		out.println("msgid \"\"");
		out.println("msgstr \"\"");
		for (final Entry<String, String> headerEntry : this.headers.entrySet()) {
			out.println("\"" + headerEntry.getKey() + ": "
					+ headerEntry.getValue() + "\\n\"");
		}
		out.println();

		// Entries
		for (final POEntry parserEntry : this.entries) {
			for (final POReference reference : parserEntry.getReferences()) {
				out.println("#: " + reference.filename + ":" + reference.line
						+ "");
			}
			for (final String acomment : parserEntry.getAutoComments()) {
				if (!acomment.equals("")) {
					out.println("#. " + acomment);
				}
			}
			for (final String tcomment : parserEntry.getTranslaterComments()) {
				if (!tcomment.equals("")) {
					out.println("# " + tcomment);
				}
			}
			for (final String fcomment : parserEntry.getFlagComments()) {
				if (!fcomment.equals("")) {
					out.println("#, " + fcomment);
				}
			}

			if (parserEntry.isFuzzy()) {
				out.println("#, fuzzy");
			}

			if (parserEntry.getMsgId().contains("\n")) {
				boolean firstline = true;
				final StringTokenizer stMsgId = new StringTokenizer(parserEntry
						.getMsgId(), "\n");
				while (stMsgId.hasMoreTokens()) {
					String msgId = stMsgId.nextToken();
					if (firstline) {
						if (parserEntry.getMsgId().startsWith("\n")) {
							out.println("msgid \"\"\n\"" + msgId + "\"");
						} else {
							out.println("msgid \"" + msgId + "\"");
						}
						firstline = false;
					} else {
						out.println("\"" + msgId + "\"");
					}
				}
			} else {
				out.println("msgid \"" + parserEntry.getMsgId() + "\"");
			}

			if (parserEntry instanceof POEntrySingular) {
				final POEntrySingular singular = (POEntrySingular) parserEntry;
				if (singular.getMsgStr().contains("\n")) {
					boolean firstline = true;
					final StringTokenizer stMsgStr = new StringTokenizer(
							singular.getMsgStr(), "\n");
					while (stMsgStr.hasMoreTokens()) {
						String msgStr = stMsgStr.nextToken();
						if (firstline) {
							out.println("msgstr \"" + msgStr + "\"");
							firstline = false;
						} else {
							out.println("\"" + msgStr + "\"");
						}
					}
				} else {
					out.println("msgstr \"" + singular.getMsgStr() + "\"");
				}
			}

			// Only if we have plural
			if (parserEntry instanceof POEntryPlural) {
				final POEntryPlural plural = (POEntryPlural) parserEntry;
				out.println("msgid_plural \"" + plural.getMsgIdPlural() + "\"");
				int i = 0;
				for (final String msgStr : plural.getMsgStrs()) {
					out.println("msgstr[" + i + "] \"" + msgStr + "\"");
					i++;
				}
			}
			out.println();
		}

		writer.flush();
		return writer.toString();
	}
}