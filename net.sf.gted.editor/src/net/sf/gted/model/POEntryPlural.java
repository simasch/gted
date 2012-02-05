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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * POEntryPlural
 * 
 * @author Xavier Cho
 * @version $Revision 1.1 $ $Date: 2008/08/12 11:19:14 $
 */
public class POEntryPlural extends POEntry implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8018191711268420561L;

	/** The msg id plural. */
	private String msgIdPlural;

	/** The msg strs. */
	private List<String> msgStrs = new ArrayList<String>();

	public POEntryPlural(final POEntrySingular entrySingular) {
		super(entrySingular);
	}

	/**
	 * Returns true if there are any messages untranslated
	 * 
	 * @see net.sf.gted.model.POEntry#isUntranslated()
	 */
	@Override
	public boolean isUntranslated() {
		if (getFile().getNplural() != msgStrs.size()) {
			return true;
		}

		for (final String msgStrPlural : this.msgStrs) {
			if (msgStrPlural == null || "".equals(msgStrPlural.trim())) {
				return true;
			}
		}

		return false;
	}

	public void setMsgStr(int i, String msgStr) {
		msgStrs.set(i, this.escapeString(msgStr));
	}

	public String getMsgStr(int i) {
		return msgStrs.get(i);
	}

	public List<String> getMsgStrs() {
		return msgStrs;
	}

	public String getMsgStr() {
		StringBuffer allMsgStrs = new StringBuffer();

		int c = 0;
		for (String msgStr : msgStrs) {
			allMsgStrs.append('[');
			allMsgStrs.append(c++);
			allMsgStrs.append("] ");
			allMsgStrs.append(msgStr);
			allMsgStrs.append('\n');
		}

		return allMsgStrs.toString();
	}

	public String getMsgIdPlural() {
		return msgIdPlural;
	}

	public void setMsgIdPlural(String msgIdPlural) {
		this.msgIdPlural = msgIdPlural;
	}

	public void addMsgStr(String msgStr) {
		msgStrs.add(this.escapeString(msgStr));
	}

}
