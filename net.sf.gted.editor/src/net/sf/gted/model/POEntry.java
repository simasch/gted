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
package net.sf.gted.model;

import java.util.ArrayList;
import java.util.List;

/**
 * POEntry
 * 
 * @author Xavier Cho
 * @version $Revision 1.1 $ $Date: 2008/08/12 11:19:14 $
 */
public abstract class POEntry {

	private int line;

	private int column;

	/** The comments. */
	private List<String> translaterComments = new ArrayList<String>();

	/** The comments. */
	private List<String> autoComments = new ArrayList<String>();

	/** The comments. */
	private List<String> flagComments = new ArrayList<String>();

	/** The fuzzy. */
	private boolean fuzzy;

	private boolean obsolete;

	/** The references. */
	private List<POReference> references = new ArrayList<POReference>();

	/** The msg id. */
	private String msgId;

	/** The file. */
	private POFile file;

	private boolean dirty;

	/**
	 * Friendly constructor for sub-classing.
	 */
	POEntry() {
	}

	public POEntry(POEntry copy) {
		autoComments = copy.autoComments;
		column = copy.column;
		file = copy.file;
		flagComments = copy.flagComments;
		fuzzy = copy.fuzzy;
		line = copy.line;
		msgId = copy.msgId;
		obsolete = copy.obsolete;
		references = copy.references;
		translaterComments = copy.translaterComments;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public List<String> getTranslaterComments() {
		return translaterComments;
	}

	public void setTranslaterComments(List<String> translaterComments) {
		this.translaterComments = translaterComments;
	}

	public String getTranslaterCommentsAsString() {
		StringBuffer allComments = new StringBuffer();
		for (final String comment : translaterComments) {
			allComments.append(comment);
			allComments.append('\n');
		}

		return allComments.toString();
	}

	public boolean isFuzzy() {
		return fuzzy;
	}

	public void setFuzzy(boolean fuzzy) {
		this.fuzzy = fuzzy;
	}

	public List<POReference> getReferences() {
		return references;
	}

	public void setReferences(List<POReference> references) {
		this.references = references;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public POFile getFile() {
		return file;
	}

	public void setFile(POFile file) {
		this.file = file;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public List<String> getAutoComments() {
		return autoComments;
	}

	public void setAutoComments(List<String> autoComments) {
		this.autoComments = autoComments;
	}

	public List<String> getFlagComments() {
		return flagComments;
	}

	public void setFlagComments(List<String> flagComments) {
		this.flagComments = flagComments;
	}

	public boolean isObsolete() {
		return obsolete;
	}

	public void setObsolete(boolean obsolete) {
		this.obsolete = obsolete;
	}

	public void addTranslaterComment(String translatedComment) {
		translaterComments.add(translatedComment);
	}

	public void addFlagComment(String flagComment) {
		flagComments.add(flagComment);
	}

	public void addAutoComment(String autoComment) {
		autoComments.add(autoComment);
	}

	protected String escapeString(String string) {
		string = string.replaceAll("\\\\\"", "\"");
		return string.replaceAll("\"", "\\\\\"");
	}

	public abstract boolean isUntranslated();

	public abstract String getMsgStr();

	/*
	 * Oh my god this is a big "dirty" hack!
	 */
	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

}
