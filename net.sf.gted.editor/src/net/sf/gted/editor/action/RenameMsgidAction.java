package net.sf.gted.editor.action;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.gted.editor.dialog.RenameMsgidDialog;
import net.sf.gted.editor.entry.master.POMasterDetailsBlock;
import net.sf.gted.model.POEntry;
import net.sf.gted.model.POReference;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class RenameMsgidAction extends Action implements IObjectActionDelegate {

	private POMasterDetailsBlock masterDetailsBlock;
	private IProject project;

	public RenameMsgidAction(POMasterDetailsBlock masterDetailsBlock,
			IProject project) {
		this.setId("net.sf.gted.editor.action.RenameMsgidAction");
		this.setText("Rename msgid");
		this.masterDetailsBlock = masterDetailsBlock;
		this.project = project;
	}

	public void run(final IAction action) {
		this.run();
	}

	public void run() {
		POEntry entry = this.masterDetailsBlock.getSelectedEntry();

		final RenameMsgidDialog std = new RenameMsgidDialog(Display
				.getDefault().getActiveShell(), "Rename msgid",
				"Enter new msgid", entry.getMsgId(), null);
		std.open();

		String value = std.getValue();
		if (value != null && !value.equals("")) {
			try {
				for (POReference reference : entry.getReferences()) {
					final IFile sourcefile = this.project
							.getFile(reference.filename);
					InputStream is = sourcefile.getContents();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is));
					int i = 1;
					StringBuffer sb = new StringBuffer();
					String line = br.readLine();
					boolean updated = false;
					while (line != null) {
						if (reference.lineAsInteger().intValue() == i) {
							line = line.replace(entry.getMsgId(), value);
							updated = true;
						}
						sb.append(line + "\r\n");
						line = br.readLine();
						i++;
					}
					if (updated) {
						InputStream newIs = new ByteArrayInputStream(sb
								.toString().getBytes());
						sourcefile.setContents(newIs, IFile.KEEP_HISTORY,
								new NullProgressMonitor());
					}
				}
				entry.setMsgId(value);
				entry.setDirty(true);
				this.masterDetailsBlock.refresh();
			} catch (Exception e) {
				MessageDialog.openError(null, "Error refactoring msgid",
						"There was an error while refactoring the msgid: "
								+ e.getMessage());
			}
		}
	}

	public void setActivePart(final IAction action,
			final IWorkbenchPart targetPart) {
	}

	public void selectionChanged(final IAction action,
			final ISelection selection) {
	}

}
