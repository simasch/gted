package net.sf.gted.editor.header;

import java.util.Map;

import net.sf.gted.editor.POFormEditor;
import net.sf.gted.model.POFile;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class HeaderForm extends FormPage {

	private static final String MIME_VERSION = "MIME-Version";

	private static final String PLURAL_FORMS = "Plural-Forms";

	private static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";

	private static final String CONTENT_TYPE = "Content-Type";

	private static final String LANGUAGE_TEAM = "Language-Team";

	private static final String REPORT_MSGID_BUGS_TO = "Report-Msgid-Bugs-To";

	private static final String LAST_TRANSLATOR = "Last-Translator";

	private static final String PO_REVISION_DATE = "PO-Revision-Date";

	private static final String POT_CREATION_DATE = "POT-Creation-Date";

	private static String PROJECT_ID_VERSION = "Project-Id-Version";

	private Text pORevisionDateText;

	private Text pOTCreationDateText;

	private Text pluralFormsText;

	private Text contentTransferEncodingText;

	private Text contentTypeText;

	private Text mIMEVersionText;

	private Text languageTeamText;

	private Text lastTranslatorText;

	private Text reportMsgidBugsToText;

	private Text projectIdVersionText;

	private POFormEditor editor;

	private boolean dirty = false;

	private boolean filled;

	// private boolean filled = false;

	public HeaderForm(POFormEditor editor) {
		super(editor, "net.sf.gted.editor.forms.POMasterDetailsPage", "Entries");
		this.editor = editor;
	}

	/**
	 * Create contents of the form
	 * 
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText("Header Editor");
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		final Section section = toolkit.createSection(body, Section.EXPANDED
				| Section.TITLE_BAR);
		section.marginWidth = 5;
		section.marginHeight = 5;
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite composite = toolkit.createComposite(section, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);
		section.setClient(composite);
		toolkit.paintBordersFor(composite);

		toolkit.createLabel(composite, PROJECT_ID_VERSION, SWT.NONE);

		projectIdVersionText = toolkit.createText(composite, null, SWT.NONE);
		projectIdVersionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));
		projectIdVersionText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				if (filled) {
					updateModel(HeaderForm.PROJECT_ID_VERSION,
							projectIdVersionText.getText());
				}
			}

		});

		toolkit.createLabel(composite, POT_CREATION_DATE, SWT.NONE);

		pOTCreationDateText = toolkit.createText(composite, null, SWT.NONE);
		pOTCreationDateText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));
		pOTCreationDateText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				if (filled) {
					updateModel(HeaderForm.POT_CREATION_DATE,
							pOTCreationDateText.getText());
				}
			}

		});

		toolkit.createLabel(composite, PO_REVISION_DATE, SWT.NONE);

		pORevisionDateText = toolkit.createText(composite, null, SWT.NONE);
		pORevisionDateText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));
		pORevisionDateText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				if (filled) {
					updateModel(HeaderForm.PO_REVISION_DATE, pORevisionDateText
							.getText());
				}
			}

		});

		final Label label = toolkit.createLabel(composite, LAST_TRANSLATOR,
				SWT.NONE);
		label.setLayoutData(new GridData());

		lastTranslatorText = toolkit.createText(composite, null, SWT.NONE);
		lastTranslatorText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));
		lastTranslatorText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				if (filled) {
					updateModel(HeaderForm.LAST_TRANSLATOR, lastTranslatorText
							.getText());
				}
			}

		});

		toolkit.createLabel(composite, REPORT_MSGID_BUGS_TO, SWT.NONE);

		reportMsgidBugsToText = toolkit.createText(composite, null, SWT.NONE);
		reportMsgidBugsToText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));
		reportMsgidBugsToText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				if (filled) {
					updateModel(HeaderForm.REPORT_MSGID_BUGS_TO,
							reportMsgidBugsToText.getText());
				}
			}

		});

		toolkit.createLabel(composite, LANGUAGE_TEAM, SWT.NONE);

		languageTeamText = toolkit.createText(composite, null, SWT.NONE);
		languageTeamText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		languageTeamText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				if (filled) {
					updateModel(HeaderForm.LANGUAGE_TEAM, languageTeamText
							.getText());
				}
			}

		});

		toolkit.createLabel(composite, CONTENT_TYPE, SWT.NONE);

		contentTypeText = toolkit.createText(composite, null, SWT.NONE);
		contentTypeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		contentTypeText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				if (filled) {
					updateModel(HeaderForm.CONTENT_TYPE, contentTypeText
							.getText());
				}
			}

		});

		toolkit.createLabel(composite, CONTENT_TRANSFER_ENCODING, SWT.NONE);

		contentTransferEncodingText = toolkit.createText(composite, null,
				SWT.NONE);
		contentTransferEncodingText.setLayoutData(new GridData(SWT.FILL,
				SWT.CENTER, true, false));
		contentTransferEncodingText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				if (filled) {
					updateModel(HeaderForm.CONTENT_TRANSFER_ENCODING,
							contentTransferEncodingText.getText());
				}
			}

		});

		toolkit.createLabel(composite, PLURAL_FORMS, SWT.NONE);

		pluralFormsText = toolkit.createText(composite, null, SWT.NONE);
		pluralFormsText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		pluralFormsText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				if (filled) {
					updateModel(HeaderForm.PLURAL_FORMS, pluralFormsText
							.getText());
				}
			}

		});

		final Label label_1 = toolkit.createLabel(composite, MIME_VERSION,
				SWT.NONE);
		label_1.setLayoutData(new GridData());

		mIMEVersionText = toolkit.createText(composite, null, SWT.NONE);
		mIMEVersionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		mIMEVersionText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				if (filled) {
					updateModel(HeaderForm.MIME_VERSION, mIMEVersionText
							.getText());
				}
			}

		});

		this.update();
	}

	/**
	 * Refresh.
	 */
	public void refresh() {
		this.update();
	}

	/**
   * 
   */
	private void update() {
		if (projectIdVersionText != null) {
			POFile file = this.editor.getFile();

			if (file != null) {
				filled = false;
				Map<String, String> headers = file.getHeaders();

				if (headers.containsKey(PROJECT_ID_VERSION)) {
					projectIdVersionText.setText(headers
							.get(PROJECT_ID_VERSION));
				}
				if (headers.containsKey(POT_CREATION_DATE)) {
					pOTCreationDateText.setText(headers.get(POT_CREATION_DATE));
				}
				if (headers.containsKey(PO_REVISION_DATE)) {
					pORevisionDateText.setText(headers.get(PO_REVISION_DATE));
				}
				if (headers.containsKey(LAST_TRANSLATOR)) {
					lastTranslatorText.setText(headers.get(LAST_TRANSLATOR));
				}
				if (headers.containsKey(REPORT_MSGID_BUGS_TO)) {
					reportMsgidBugsToText.setText(headers
							.get(REPORT_MSGID_BUGS_TO));
				}
				if (headers.containsKey(LANGUAGE_TEAM)) {
					languageTeamText.setText(headers.get(LANGUAGE_TEAM));
				}
				if (headers.containsKey(CONTENT_TYPE)) {
					contentTypeText.setText(headers.get(CONTENT_TYPE));
				}
				if (headers.containsKey(CONTENT_TRANSFER_ENCODING)) {
					contentTransferEncodingText.setText(headers
							.get(CONTENT_TRANSFER_ENCODING));
				}
				if (headers.containsKey(PLURAL_FORMS)) {
					pluralFormsText.setText(headers.get(PLURAL_FORMS));
				}
				if (headers.containsKey(MIME_VERSION)) {
					mIMEVersionText.setText(headers.get(MIME_VERSION));
				}
				filled = true;
			}
		}
		dirty = false;
		HeaderForm.this.editor.editorDirtyStateChanged();
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	private void updateModel(String key, String value) {
		if (filled) {
			HeaderForm.this.editor.getFile().getHeaders().put(key, value);
			HeaderForm.this.dirty = true;
			HeaderForm.this.editor.editorDirtyStateChanged();
			HeaderForm.this.editor.dataChanged();
		}
	}

}
