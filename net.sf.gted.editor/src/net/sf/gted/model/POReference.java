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

/**
 * POReference
 * 
 * @author Xavier Cho
 * @version $Revision 1.1 $ $Date: 2008/08/12 11:19:14 $
 */
public class POReference {

	public String filename;

	public String line;

	public POReference(String filename, String line) {
		this.filename = filename;
		this.line = line;
	}

	public Integer lineAsInteger() {
		if (line == null || "".equals(line.trim())) {
			return null;
		}

		try {
			return Integer.parseInt(line);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	@Override
	public String toString() {
		return this.filename + ":" + this.line;
	}

}
