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
package net.sf.gted.tools.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Thread to STDERR of a process.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.3 $, $Date: 2008/08/12 11:20:26 $
 */
public class ErrorRedirectionThread extends Thread {

	/** The proc. */
	private Process proc;

	private StringBuffer error;

	/**
	 * The Constructor.
	 * 
	 * @param proc
	 *            the proc
	 * @param usage
	 *            the usage
	 */
	public ErrorRedirectionThread(final Process proc, final StringBuffer error) {
		this.proc = proc;
		this.error = error;
	}

	/**
	 * Run.
	 */
	@Override
	public void run() {
		try {
			final InputStream is = this.proc.getErrorStream();

			final BufferedReader br = new BufferedReader(new InputStreamReader(
					is));
			String msg = br.readLine();
			while (msg != null) {
				this.error.append(msg);
				msg = br.readLine();
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
