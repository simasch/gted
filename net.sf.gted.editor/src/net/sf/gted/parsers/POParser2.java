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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.gted.editor.source.IErrorHandler;
import net.sf.gted.model.POEntry;
import net.sf.gted.model.POEntryPlural;
import net.sf.gted.model.POEntrySingular;
import net.sf.gted.model.POReference;

/**
 * POParser.java
 * 
 * @author Simon Martinelli
 * @version $Revision 1.1 $ $Date: 2009/02/23 12:05:48 $
 */
public class POParser2 {

	private static final String HEADER_PLURAL_FORM = "Plural-Forms";

	private static final Pattern REGEXP_PLURAL_FORM = Pattern
			.compile("^\\s*nplurals\\s*=\\s*([0-9]+)\\s*;\\s*([^;]+);$");

	private IPOParserCallback callback;

	// private IErrorHandler errorHandler;

	private int linenumber;

	private String line;

	private POEntry entry;

	/**
	 * @param callback
	 * @param markingErrorHandler
	 */
	public POParser2(final IPOParserCallback callback,
			final IErrorHandler markingErrorHandler) {
		this.callback = callback;
		// this.errorHandler = markingErrorHandler;
	}

	/**
	 * @param reader
	 */
	public synchronized void parse(Reader reader) {
		if (this.callback == null) {
			throw new IllegalStateException(
					"No parser callback instance has been registered.");
		}

		this.callback.startDocument();

		BufferedReader br = new BufferedReader(reader);
		line = null;
		linenumber = 0;
		try {
			this.parseHeader(br);

			while (line != null) {
				entry = new POEntrySingular();
				entry.setColumn(0);

				//
				// Parse Entry Comments
				//
				while (line != null && !line.startsWith("msgid")) {
					if (line.startsWith("#:")) {
						this.extractReferences(entry, line.substring(2).trim());
					} else if (line.startsWith("# ")) {
						entry.addTranslaterComment(line.substring(2).trim());
					} else if (line.startsWith("#, fuzzy")) {
						entry.setFuzzy(true);
					} else if (line.startsWith("#, ")) {
						entry.addFlagComment(line.substring(3).trim());
					} else if (line.startsWith("#. ")) {
						entry.addAutoComment(line.substring(3).trim());
					}
					line = br.readLine();
					linenumber++;
				}
				while (line != null && !line.equals("")) {
					this.parseEntry(br);
				}
				if (entry.getMsgId() != null) {
					callback.onEntry(entry);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.callback.endDocument();

	}

	/**
	 * @param br
	 * @param line
	 * @param entry
	 * @return
	 * @throws IOException
	 */
	private void parseEntry(BufferedReader br) throws IOException {
		if (line.startsWith("msgid ")) {
			entry.setLine(linenumber);

			String msgId = line.substring(line.indexOf("\"") + 1, line
					.lastIndexOf("\""));

			line = br.readLine();
			linenumber++;

			while (line != null && line.startsWith("\"")) {
				msgId += "\n";
				msgId += line.substring(line.indexOf("\"") + 1, line
						.lastIndexOf("\""));
				line = br.readLine();
				linenumber++;
			}

			entry.setMsgId(msgId);
		} else {
			if (line.startsWith("msgid_plural")) {
				entry = new POEntryPlural((POEntrySingular) entry);
				String msgIdPlural = line.substring(line.indexOf("\"") + 1,
						line.lastIndexOf("\""));

				line = br.readLine();
				linenumber++;

				while (line != null && line.startsWith("\"")) {
					msgIdPlural += "\n";
					msgIdPlural += line.substring(line.indexOf("\"") + 1, line
							.lastIndexOf("\""));
					line = br.readLine();
					linenumber++;
				}

				POEntryPlural entryPlural = ((POEntryPlural) entry);
				entryPlural.setMsgIdPlural(msgIdPlural);
			} else if (line.startsWith("msgstr ")) {
				String msgStr = line.substring(line.indexOf("\"") + 1, line
						.lastIndexOf("\""));

				line = br.readLine();
				linenumber++;

				while (line != null && line.startsWith("\"")) {
					String msgStringLine = line.substring(
							line.indexOf("\"") + 1, line.lastIndexOf("\""));
					msgStr += "\n";
					msgStr += msgStringLine;
					line = br.readLine();
					linenumber++;
				}

				((POEntrySingular) entry).setMsgStr(msgStr);
			} else if (line.startsWith("msgstr[")) {
				POEntryPlural entryPlural = ((POEntryPlural) entry);

				entryPlural.addMsgStr(line.substring(line.indexOf("\"") + 1,
						line.lastIndexOf("\"")));
				line = br.readLine();
				linenumber++;
				while (line != null && line.startsWith("\"")) {
					entryPlural.addMsgStr(line.substring(
							line.indexOf("\"") + 1, line.lastIndexOf("\"")));
					line = br.readLine();
					linenumber++;
				}
			} else {
				line = br.readLine();
				linenumber++;
			}
		}
	}

	/**
	 * @param br
	 * @throws IOException
	 */
	private void parseHeader(BufferedReader br) throws IOException {
		//
		// Parse Header Comment
		//
		line = br.readLine();
		linenumber++;
		while (line != null && line.startsWith("#")) {
			callback.onComment(line);
			line = br.readLine();
			linenumber++;
		}
		//
		// Parse Header
		//
		if (line.startsWith("#, fuzzy")) {
			line = br.readLine(); // msgid ""
			linenumber++;
			line = br.readLine(); // msgstr ""
			linenumber++;
		} else if (line.startsWith("msgid")) {
			line = br.readLine(); // msgstr ""
			linenumber++;
		}
		line = br.readLine();
		linenumber++;
		while (line != null && line.startsWith("\"")) {
			int index = line.indexOf(":");
			if (index != -1) {
				String key = line.substring(1, index).trim();
				String value;
				int lastLf = line.lastIndexOf("\\n\"");
				if (lastLf == -1) {
					// The header field is on two lines.
					line = br.readLine();
					value = line.replace("\\n", "").replace("\"", "").trim();
				} else {
					value = line.substring(index + 1, lastLf).trim();
				}
				// Additional treatment of Plural-Form
				if (key.equalsIgnoreCase(POParser2.HEADER_PLURAL_FORM)) {
					this.parsePluralForm(value);
				}
				this.callback.onHeader(key, value);
			}
			line = br.readLine();
			linenumber++;
		}
	}

	/**
	 * Parses the plural form.
	 * 
	 * @param value
	 *            the value
	 */
	protected void parsePluralForm(final String value) {
		final Matcher matcher = POParser2.REGEXP_PLURAL_FORM.matcher(value);
		if (matcher.matches() && matcher.groupCount() > 1) {
			final int nplural = Integer.parseInt(matcher.group(1));
			final String expression = matcher.group(2).trim();

			this.callback.onHeaderPluralForm(nplural, expression);
		}
	}

	/**
	 * @param entry
	 * @param string
	 */
	private void extractReferences(final POEntry entry, final String string) {
		final String[] refs = string.split(" ");
		for (final String ref : refs) {
			if (ref.startsWith(";")) {
				final String[] multipleReferences = string.split(";");
				for (String lineNumber : multipleReferences) {
					if (!lineNumber.equals("")) {

						// get the last filename
						String filename = entry.getReferences().get(
								entry.getReferences().size() - 1).filename;

						POReference reference = new POReference(filename,
								lineNumber);
						entry.getReferences().add(reference);
					}
				}
			} else {
				final String[] parts = ref.split(":");
				if (parts.length == 2) {
					if (parts[1].contains(";")) {
						final String[] multipleReferences = parts[1].split(";");
						for (String lineNumber : multipleReferences) {
							String filename = parts[0];

							POReference reference = new POReference(filename,
									lineNumber);
							entry.getReferences().add(reference);
						}
					} else {
						String filename = parts[0];
						String line = parts[1];

						POReference reference = new POReference(filename, line);
						entry.getReferences().add(reference);
					}
				} else if (parts.length == 3) {
					if (parts[1].contains(";")) {
						final String[] multipleReferences = parts[2].split(";");
						for (String lineNumber : multipleReferences) {
							String filename = parts[0] + ":" + parts[1];

							POReference reference = new POReference(filename,
									lineNumber);
							entry.getReferences().add(reference);
						}
					} else {
						String filename = parts[0] + ":" + parts[1];
						String line = parts[2];

						POReference reference = new POReference(filename, line);
						entry.getReferences().add(reference);
					}
				}
			}
		}
	}
}
