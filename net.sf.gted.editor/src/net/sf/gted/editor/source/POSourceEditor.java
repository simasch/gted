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
package net.sf.gted.editor.source;

import java.io.StringReader;

import net.sf.gted.editor.POFileEditorPlugin;
import net.sf.gted.editor.POFormEditor;
import net.sf.gted.editor.preferences.PreferenceConstants;
import net.sf.gted.editor.source.markers.MarkingHandler;
import net.sf.gted.model.POEntry;
import net.sf.gted.model.POFile;
import net.sf.gted.parsers.POParser2;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.FileStoreEditorInput;

/**
 * The Class POEditor.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.11 $, $Date: 2008/08/12 13:12:50 $
 */
public class POSourceEditor extends TextEditor {

	/** The color manager. */
	private ColorManager colorManager;

	/** The input. */
	private IEditorInput input;

	/** The host. */
	private POFormEditor host;

	/** The file. */
	private POFile file;

	/**
	 * The Constructor.
	 * 
	 * @param editor
	 *            the editor
	 */
	public POSourceEditor(final POFormEditor editor) {
		super();
		this.file = editor.getFile();
		this.colorManager = new ColorManager();
		this.host = editor;
		this.setSourceViewerConfiguration(new POConfiguration(
				this.colorManager, this));
		this.setDocumentProvider(new PODocumentProvider());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.editors.text.TextEditor#dispose()
	 */
	@Override
	public void dispose() {
		this.colorManager.dispose();
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.editors.text.TextEditor#doSetInput(org.eclipse.ui.IEditorInput
	 * )
	 */
	@Override
	protected void doSetInput(final IEditorInput newInput) throws CoreException {
		super.doSetInput(newInput);
		this.input = newInput;
		this.validateAndMark();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractTextEditor#editorSaved()
	 */
	@Override
	protected void editorSaved() {
		super.editorSaved();
		this.validateAndMark();
	}

	/**
	 * Validate and mark.
	 */
	protected void validateAndMark() {
		final String untranslated = POFileEditorPlugin.getDefault()
				.getPreferenceStore().getString(
						PreferenceConstants.P_UNTRANSLATED);
		final String fuzzy = POFileEditorPlugin.getDefault()
				.getPreferenceStore().getString(PreferenceConstants.P_FUZZY);

		final IDocument document = this.getInputDocument();

		final MarkingHandler markingHandler = new MarkingHandler(this
				.getInputFile(), document);
		markingHandler.removeExistingMarkers();

		final StringReader stringReader = new StringReader(document.get());

		this.file.init();
		final POParser2 parser = new POParser2(this.file, markingHandler);

		try {
			parser.parse(stringReader);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		if (this.getInputFile().getFileExtension().equals("pot")) {
			// do nothing if template
		} else {
			for (final POEntry entry : this.file.getEntries()) {
				if (!fuzzy.equals(PreferenceConstants.P_IGNORE)
						&& entry.isFuzzy()) {
					markingHandler.addMessage("Fuzzy entry found", entry
							.getLine(), fuzzy);
				}
				if (!untranslated.equals(PreferenceConstants.P_IGNORE)
						&& entry.isUntranslated()) {
					markingHandler.addMessage("Untranslated entry found", entry
							.getLine(), untranslated);
				}
			}
		}

		if (this.host != null) {
			this.host.refresh();
		}
	}

	/**
	 * Gets the input document.
	 * 
	 * @return the input document
	 */
	public IDocument getInputDocument() {
		final IDocument document = this.getDocumentProvider().getDocument(
				this.input);
		return document;
	}

	/**
	 * Gets the input file.
	 * 
	 * @return the input file
	 */
	protected IFile getInputFile() {
		IFile file = null;
		if (this.input instanceof IFileEditorInput) {
			final IFileEditorInput ife = (IFileEditorInput) this.input;
			file = ife.getFile();
		} else if (this.input instanceof FileStoreEditorInput) {
			final FileStoreEditorInput fsei = (FileStoreEditorInput) this.input;
			final IPath path = Path.fromOSString(fsei.getURI().getPath());
			file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		}
		return file;
	}

	/**
	 * Gets the input.
	 * 
	 * @return the input
	 */
	public IEditorInput getInput() {
		return this.input;
	}

	/**
	 * Gets the file.
	 * 
	 * @return the file
	 */
	public POFile getFile() {
		return this.file;
	}

	public IFile getIFile() {
		return this.getInputFile();
	}
}
