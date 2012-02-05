package net.sf.gted.editor.filter;

import net.sf.gted.model.POEntry;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class FuzzyFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof POEntry) {
			POEntry entry = (POEntry) element;
			if (entry.isFuzzy()) {
				return true;
			}
		}
		return false;
	}

}
