package net.sf.gted.editor.util;

import net.sf.gted.editor.POFileEditorPlugin;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

public class OpenEditorHelper {

	public static void openEditor(IFile file, int lineNumber) {
		try {
			IEditorPart editorPart = IDE.openEditor(POFileEditorPlugin
					.getDefault().getWorkbench().getActiveWorkbenchWindow()
					.getActivePage(), file, false);

			final ITextEditor editor = (ITextEditor) editorPart;

			final IDocumentProvider provider = editor.getDocumentProvider();
			final IDocument document = provider.getDocument(editor
					.getEditorInput());

			final int start = document.getLineOffset(lineNumber - 1);
			editor.selectAndReveal(start, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
