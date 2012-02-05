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
package net.sf.gted.tools.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.gted.tools.ToolsPlugin;
import net.sf.gted.tools.preferences.PreferenceConstants;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

/**
 * @author $Author: simas_ch $
 * @version $Revision: 1.6 $, $Date: 2008/08/12 11:03:02 $
 */
public class ProcessHelper {

	/**
	 * @param command
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws CoreException
	 */
	public static String executeCommand(final IProject project,
			final boolean redirect, List<String> command) throws IOException,
			InterruptedException, CoreException {
		// Check if the path to the tools is set
		String path = ToolsPlugin.getDefault().getPreferenceStore().getString(
				PreferenceConstants.P_GETTEXT_PATH);
		if (path != null && !path.equals("")) {
			command.set(0, path + File.separator + command.get(0));
		}
		System.out.println("Command: " + command);

		final File workdir = new File(project.getLocation().toOSString());

		ProcessBuilder pb = new ProcessBuilder(command);
		Map<String, String> env = pb.environment();
		env.put("TMPDIR", workdir.toString());
		pb.directory(workdir);

		Process proc = pb.start();

		final StringBuffer error = new StringBuffer();
		ErrorRedirectionThread err = null;
		if (redirect) {
			err = new ErrorRedirectionThread(proc, error);
			err.start();
		}

		final int returncode = proc.waitFor();

		if (redirect && err != null) {
			err.join();
		}
		if (returncode > 0 || error.length() > 0) {
			return error.toString();
		} else {
			return null;
		}
	}

}
