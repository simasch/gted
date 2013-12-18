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
package net.sf.gted.tools.popup.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.gted.tools.properties.ProjectPropertyPage;
import net.sf.gted.tools.util.FolderHelper;
import net.sf.gted.tools.util.ProcessHelper;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * The Class MakeMOFilesAction.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.17 $, $Date: 2009/04/28 10:51:43 $
 */
public class MakeMOFilesAction implements IObjectActionDelegate {

	private IProject project;

	/**
	 * Constructor for Action1.
	 */
	public MakeMOFilesAction() {
		super();
	}

	/**
	 * Sets the active part.
	 * 
	 * @param action
	 *            the action
	 * @param targetPart
	 *            the target part
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(final IAction action,
			final IWorkbenchPart targetPart) {
	}

	/**
	 * Run.
	 * 
	 * @param action
	 *            the action
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(final IAction action) {
		String error = null;
		try {
			error = this.makeMsgfmt();
			this.project.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (final Exception e) {
			e.printStackTrace();
			error = e.getMessage();
		}
		if (error != null) {
			MessageDialog.openError(new Shell(), "Make MO Files failed", error);
		} else {
			MessageDialog.openInformation(new Shell(), "Make MO Files",
					"Execution sucessfull");
		}
	}

	/**
	 * @return
	 * @throws CoreException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private String makeMsgfmt() throws CoreException, IOException,
			InterruptedException {
		final String extension = this.project
				.getPersistentProperty(new QualifiedName(
						ProjectPropertyPage.QUALIFIER,
						ProjectPropertyPage.XGETTEXT_EXTENSION_PROPERTY));
		final String language = this.project
				.getPersistentProperty(new QualifiedName(
						ProjectPropertyPage.QUALIFIER,
						ProjectPropertyPage.LANGUAGE_PROPERTY));

		boolean java = false;
		if (language != null && language.equals("Java") || language.equals("")
				&& extension.equals("java")) {
			java = true;
		}

		final String inputfolder = this.project
				.getPersistentProperty(new QualifiedName(
						ProjectPropertyPage.QUALIFIER,
						ProjectPropertyPage.XGETTEXT_OUTPUT_PROPERTY));
		final IFolder ifolder = this.project.getFolder(inputfolder);

		final String outputfolder = this.project
				.getPersistentProperty(new QualifiedName(
						ProjectPropertyPage.QUALIFIER,
						ProjectPropertyPage.MSGFMT_OUTPUT_PROPERTY));
		final IFolder ofolder = this.project.getFolder(outputfolder);

		final String domainname = this.project
				.getPersistentProperty(new QualifiedName(
						ProjectPropertyPage.QUALIFIER,
						ProjectPropertyPage.XGETTEXT_DOMAIN_NAME_PROPERTY));

		for (final IResource resource : ifolder.members()) {
			if (resource instanceof IFolder) {
				final IFolder trFolder = (IFolder) resource;
				

				final IFolder lcFolder = trFolder.getFolder("LC_MESSAGES");
				final IFile file = lcFolder.exists() ? lcFolder.getFile(domainname + ".po") : null;
				
				if (lcFolder.exists() && file.exists()) {
					// Create a new folder
					final IPath path = ofolder.getFullPath().append(
							resource.getName());					
					final IFolder newfolder = ResourcesPlugin.getWorkspace()
							.getRoot().getFolder(path);
					if (!newfolder.exists()) {
						FolderHelper.createNewFolder(newfolder);
					}
					final List<String> command = this.prepareMsgfmt(resource
							.getName(), java);

					final String error = ProcessHelper.executeCommand(
							this.project, true, command);
					if (error != null) {
						if (error.startsWith("Note:")) {
							// Warning from Java compiler - ignore
						} else {
							return error;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * @param string
	 * @return
	 * @throws CoreException
	 */
	private List<String> prepareMsgfmt(final String language, final boolean java)
			throws CoreException {
		List<String> command = new ArrayList<String>();

		final String inputfolder = this.project
				.getPersistentProperty(new QualifiedName(
						ProjectPropertyPage.QUALIFIER,
						ProjectPropertyPage.XGETTEXT_OUTPUT_PROPERTY));

		final String outputfolder = this.project
				.getPersistentProperty(new QualifiedName(
						ProjectPropertyPage.QUALIFIER,
						ProjectPropertyPage.MSGFMT_OUTPUT_PROPERTY));

		final String resource = this.project
				.getPersistentProperty(new QualifiedName(
						ProjectPropertyPage.QUALIFIER,
						ProjectPropertyPage.MSGFMT_RESOURCE_PROPERTY));

		final String domainname = this.project
				.getPersistentProperty(new QualifiedName(
						ProjectPropertyPage.QUALIFIER,
						ProjectPropertyPage.XGETTEXT_DOMAIN_NAME_PROPERTY));

		command.add("msgfmt");
		if (java) {
			command.add("--java2");
			command.add("-d" + outputfolder);
			command.add("-r" + resource);
			command.add("-l" + language);
		} else {
			command.add("-o" + outputfolder + "/" + language + "/LC_MESSAGES/" + domainname
					+ ".mo");
		}
		command.add(inputfolder + "/" + language + "/LC_MESSAGES/" + domainname + ".po");
		return command;
	}

	/**
	 * Selection changed.
	 * 
	 * @param action
	 *            the action
	 * @param selection
	 *            the selection
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(final IAction action,
			final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.getFirstElement() instanceof IProject) {
				this.project = (IProject) structuredSelection.getFirstElement();
			}
		}
	}
}
