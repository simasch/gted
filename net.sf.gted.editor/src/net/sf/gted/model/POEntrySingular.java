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

import java.io.Serializable;

/**
 * POEntrySingular
 * 
 * @author Xavier Cho
 * @version $Revision 1.1 $ $Date: 2008/08/12 11:19:14 $
 */
public class POEntrySingular extends POEntry implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8018191711268420561L;

	/** The msg str. */
	private String msgStr;

	/**
	 * Returns true if the entry is not translated
	 * 
	 * @see net.sf.gted.model.POEntry#isUntranslated()
	 */
	@Override
	public boolean isUntranslated() {
		return msgStr == null || "".equals(msgStr.trim());
	}

	public String getMsgStr() {
		return msgStr;
	}

	public void setMsgStr(String msgStr) {
		this.msgStr = this.escapeString(msgStr);
	}
}
