package net.sf.gted.editor.source.outline;

import net.sf.gted.editor.POFileEditorPlugin;
import net.sf.gted.model.POEntry;
import net.sf.gted.model.POFile;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class POFileLabelProvider extends LabelProvider {

	private final Image imageEntry = POFileEditorPlugin.getImageDescriptor(
			"icons/entry.gif").createImage();

	private final Image imageEntryFuzzy = POFileEditorPlugin
			.getImageDescriptor("icons/entry_fuzzy.gif").createImage();

	private final Image imageEntryUntranslated = POFileEditorPlugin
			.getImageDescriptor("icons/entry_untranslated.gif").createImage();

	private final Image imagePoFile = POFileEditorPlugin.getImageDescriptor(
			"icons/pofile.gif").createImage();

	@Override
	public Image getImage(Object element) {
		if (element instanceof POEntry) {
			POEntry entry = (POEntry) element;
			if (entry.isUntranslated()) {
				return this.imageEntryUntranslated;
			}
			else if (entry.isFuzzy()) {
				return this.imageEntryFuzzy;
			}
			else {
				return this.imageEntry;
			}
		}
		else if (element instanceof POFile) {
			return this.imagePoFile;
		}
		else {
			return null;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof POEntry) {
			POEntry entry = (POEntry) element;
			return entry.getMsgId().replaceAll("\n", "");
		}
		else if (element instanceof POFile) {
			return "POFile";
		}
		else {
			return null;
		}
	}

}
