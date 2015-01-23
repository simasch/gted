package net.sf.gted.editor.dialog;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;

public class RenameMsgidDialog extends InputDialog {

	public RenameMsgidDialog(final Shell parentShell, final String dialogTitle,
			final String dialogMessage, final String initialValue,
			final IInputValidator validator) {
		super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
	}

	@Override
	protected void okPressed() {

		super.okPressed();
	}

}
