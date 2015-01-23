package net.sf.gted.editor.source.outline;

import net.sf.gted.model.POEntry;
import net.sf.gted.model.POFile;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class POFileContentProvider implements ITreeContentProvider {

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof POFile) {
			POFile file = (POFile) parentElement;
			return file.getEntries().toArray();
		} else {
			return new Object[0];
		}
	}

	public Object getParent(Object element) {
		if (element instanceof POEntry) {
			POEntry entry = (POEntry) element;
			return entry.getFile();
		} else {
			return null;
		}
	}

	public boolean hasChildren(Object element) {
		if (element instanceof POFile) {
			POFile file = (POFile) element;
			if (file.getEntries().size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Object[] getElements(Object inputElement) {
		return this.getChildren(inputElement);
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}
