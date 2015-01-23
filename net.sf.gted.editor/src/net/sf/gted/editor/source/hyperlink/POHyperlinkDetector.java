package net.sf.gted.editor.source.hyperlink;

import net.sf.gted.editor.source.POSourceEditor;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;

public class POHyperlinkDetector extends AbstractHyperlinkDetector {

	private static final int PREFIX_LENGTH = 3;
	private POSourceEditor editor;

	public POHyperlinkDetector(POSourceEditor sourceEditor) {
		this.editor = sourceEditor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.text.hyperlink.IHyperlinkDetector#detectHyperlinks(
	 * org.eclipse.jface.text.ITextViewer, org.eclipse.jface.text.IRegion,
	 * boolean)
	 */
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
			IRegion region, boolean canShowMultipleHyperlinks) {

		if (region == null || textViewer == null) {
			return null;
		}

		IDocument document = textViewer.getDocument();
		int offset = region.getOffset();

		if (document == null) {
			return null;
		}

		IRegion lineInfo;
		String line;
		try {
			lineInfo = document.getLineInformationOfOffset(offset);
			line = document.get(lineInfo.getOffset(), lineInfo.getLength());
		} catch (BadLocationException ex) {
			return null;
		}
		int separatorOffset = line.indexOf("#: ");
		if (separatorOffset >= 0) {
			String[] references = null;
			if (line.substring(PREFIX_LENGTH).contains(" ")) {
				references = line.substring(PREFIX_LENGTH).split(" ");
			} else {
				references = new String[] { line.substring(PREFIX_LENGTH) };
			}
			int urlOffsetInLine = PREFIX_LENGTH;
			for (String reference : references) {
				String[] tokens = reference.split(":");
				String filename = tokens[0];

				int offsetForRegion = lineInfo.getOffset() + urlOffsetInLine;
				int lenghtForRegion = filename.length();
				if (offset >= offsetForRegion
						&& offset <= (offsetForRegion + lenghtForRegion)) {
					IFile file = this.editor.getIFile().getProject().getFile(
							filename);
					if (file == null) {
						continue;
					}
					IRegion urlRegion = new Region(offsetForRegion,
							lenghtForRegion);
					int lineNumber = 0;
					try {
						lineNumber = Integer.parseInt(tokens[1]);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					return new IHyperlink[] { new ReferenceHyperlink(urlRegion,
							file, lineNumber) };
				}
				urlOffsetInLine += reference.length() + 1;
			}
		}
		return null;
	}
}
