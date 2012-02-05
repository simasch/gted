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
package net.sf.gted.editor.source.markers;

import java.util.HashMap;
import java.util.Map;

import net.sf.gted.editor.preferences.PreferenceConstants;
import net.sf.gted.editor.source.IErrorHandler;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.ui.texteditor.MarkerUtilities;

/**
 * MarkingErrorHandler.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.4 $, $Date: 2008/08/12 11:19:15 $
 */
public class MarkingHandler implements IErrorHandler {

	/** The Constant ERROR_MARKER_ID. */
	public static final String ERROR_MARKER_ID = "net.sf.gted.editor.parseerror";

	/** The file. */
	private IFile file;

	/** The document. */
	private IDocument document;

	/**
	 * The Constructor.
	 * 
	 * @param file
	 *            the file
	 * @param document
	 *            the document
	 */
	public MarkingHandler(final IFile file, final IDocument document) {
		super();
		this.file = file;
		this.document = document;
	}

	/**
	 * Removes the existing markers.
	 */
	public void removeExistingMarkers() {
		try {
			this.file.deleteMarkers(MarkingHandler.ERROR_MARKER_ID, true,
					IResource.DEPTH_INFINITE);
		} catch (final CoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the error.
	 * 
	 * @param columnNumber
	 *            the column number
	 * @param error
	 *            the error
	 * @param lineNumber
	 *            the line number
	 */
	public void addError(final String error, final int lineNumber,
			final int columnNumber) {
	}

	public void addMessage(final String message, final int lineNumber,
			String severity) {

		final Map<String, Integer> map = new HashMap<String, Integer>();

		if (lineNumber != 0) {
			this.setLocation(lineNumber, map);
		}

		MarkerUtilities.setMessage(map, message);

		if (severity.equals(PreferenceConstants.P_ERROR)) {
			map.put(IMarker.SEVERITY, Integer.valueOf(IMarker.SEVERITY_ERROR));
		} else if (severity.equals(PreferenceConstants.P_WARN)) {
			map
					.put(IMarker.SEVERITY, Integer
							.valueOf(IMarker.SEVERITY_WARNING));
		} else if (severity.equals(PreferenceConstants.P_INFO)) {
			map.put(IMarker.SEVERITY, Integer.valueOf(IMarker.SEVERITY_INFO));
		}

		try {
			MarkerUtilities.createMarker(this.file, map,
					MarkingHandler.ERROR_MARKER_ID);
		} catch (final CoreException ee) {
			ee.printStackTrace();
		}

	}

	/**
	 * @param lineNumber
	 * @param map
	 */
	private void setLocation(final int lineNumber,
			final Map<String, Integer> map) {
		MarkerUtilities.setLineNumber(map, lineNumber);
		map.put(IMarker.LOCATION, lineNumber);

		final Integer charStart = this.getCharStart(lineNumber, 1);
		if (charStart != null) {
			map.put(IMarker.CHAR_START, charStart);
		}
		final Integer charEnd = this.getCharEnd(lineNumber, 1);
		if (charEnd != null) {
			map.put(IMarker.CHAR_END, charEnd);
		}
	}

	/**
	 * Gets the char end.
	 * 
	 * @param columnNumber
	 *            the column number
	 * @param lineNumber
	 *            the line number
	 * @return the char end
	 */
	private Integer getCharEnd(final int lineNumber, final int columnNumber) {
		try {
			final int lineOffset = this.document.getLineOffset(lineNumber - 1);
			final ITypedRegion typedRegion = this.document
					.getPartition(lineOffset + columnNumber);
			if (typedRegion == null) {
				return Integer.valueOf(lineOffset);
			} else {
				return Integer.valueOf(typedRegion.getOffset()
						+ typedRegion.getLength());
			}
		} catch (final BadLocationException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets the char start.
	 * 
	 * @param columnNumber
	 *            the column number
	 * @param lineNumber
	 *            the line number
	 * @return the char start
	 */
	private Integer getCharStart(final int lineNumber, final int columnNumber) {

		try {
			final int lineOffset = this.document.getLineOffset(lineNumber - 1);
			final ITypedRegion typedRegion = this.document
					.getPartition(lineOffset + columnNumber);
			if (typedRegion == null) {
				return new Integer(lineOffset);
			} else {
				return new Integer(typedRegion.getOffset());
			}
		} catch (final BadLocationException e) {
			e.printStackTrace();
			return null;
		}
	}

}