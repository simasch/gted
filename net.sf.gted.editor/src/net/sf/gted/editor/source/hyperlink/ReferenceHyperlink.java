package net.sf.gted.editor.source.hyperlink;

import net.sf.gted.editor.util.OpenEditorHelper;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;

public class ReferenceHyperlink implements IHyperlink {

	private IRegion region;
	private IFile file;
	private int lineNumber;

	public ReferenceHyperlink(IRegion region, IFile file, int lineNumber) {
		this.region = region;
		this.file = file;
		this.lineNumber = lineNumber;
	}

	public IRegion getHyperlinkRegion() {
		return region;
	}

	public String getHyperlinkText() {
		return file.getFullPath().toOSString();
	}

	public String getTypeLabel() {
		return null;
	}

	public void open() {
		OpenEditorHelper.openEditor(file, lineNumber);
	}
}
