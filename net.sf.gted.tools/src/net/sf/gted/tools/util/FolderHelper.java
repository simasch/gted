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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * @author $Author: simas_ch $
 * @version $Revision: 1.4 $, $Date: 2008/08/12 11:20:26 $
 */
public class FolderHelper {

	/**
	 * Creates a new folder with the given name and optionally linking to the
	 * specified link target.
	 * 
	 * @param folderName
	 *            name of the new folder
	 * @param linkTarget
	 *            name of the link target folder. may be null.
	 * @return IFolder the new folder
	 */
	public static IFolder createNewFolder(final IFolder folderHandle) {

		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

			@Override
			public void execute(IProgressMonitor monitor) throws CoreException {
				try {
					monitor.beginTask("Creating new folder", 2000);
					if (monitor.isCanceled()) {
						throw new OperationCanceledException();
					}

					folderHandle.create(false, true, monitor);

					if (monitor.isCanceled()) {
						throw new OperationCanceledException();
					}
				} finally {
					monitor.done();
				}
			}
		};
		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(
					operation);
		} catch (final InterruptedException exception) {
			return null;
		} catch (final InvocationTargetException exception) {
			if (exception.getTargetException() instanceof CoreException) {
				ErrorDialog.openError(new Shell(), "Creation Problems", null,
						((CoreException) exception.getTargetException())
								.getStatus());
			} else {
				// CoreExceptions are handled above, but unexpected runtime
				// exceptions and errors may still
				// occur.
				MessageDialog.openError(new Shell(), "Creation Problems",
						"Internal error: "
								+ exception.getTargetException().getMessage());
			}
			return null;
		}
		return folderHandle;
	}

}
