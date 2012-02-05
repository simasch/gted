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
package net.sf.gted.tools.properties;

import net.sf.gted.tools.ToolsPlugin;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * @author $Author: simas_ch $
 * @version $Revision: 1.23 $, $Date: 2009/01/12 19:17:27 $
 */
public class ProjectPropertyPage extends PropertyPage {

	public static final String QUALIFIER = "net.sf.gted";

	private static final String GENERAL_TITLE = "General";

	private static final String LANGUAGE_TITLE = "Programming Language: ";

	public static final String LANGUAGE_PROPERTY = "LANGUAGE";

	private static final String XGETTEXT_TITLE = "xgettext";

	private static final String MSGFMT_TITLE = "msgfmt";

	private static final String XGETTEXT_EXTENSION_TITLE = "File &Extensions (use , as Separator):";

	public static final String XGETTEXT_EXTENSION_PROPERTY = "EXTENSION";

	private static final String XGETTEXT_EXTENSION_DEFAULT = "java";

	private static final String XGETTEXT_KEYWORD_TITLE = "&Keywords (use , as Separator):";

	public static final String XGETTEXT_KEYWORD_PROPERTY = "KEYWORD";

	private static final String XGETTEXT_OUTPUT_TITLE = "&Output Folder:";

	public static final String XGETTEXT_OUTPUT_PROPERTY = "XOUTPUT";

	private static final String XGETTEXT_OUTPUT_DEFAULT = "po";

	public static final String XGETTEXT_FROMCODE_PROPERTY = "from-code";

	private static final String XGETTEXT_FROMCODE_TITLE = "Input File Interpretation (--from-code):";

	public static final String XGETTEXT_DOMAIN_NAME_PROPERTY = "domainname";

	private static final String XGETTEXT_DOMAIN_NAME = "Domain Name (--default-domain):";

	private static final String XGETTEXT_DOMAIN_NAME_DEFAULT = "messages";

	private static final String MSGFMT_OUTPUT_TITLE = "&Output Folder:";

	public static final String MSGFMT_OUTPUT_PROPERTY = "MOUTPUT";

	private static final String MSGFMT_OUTPUT_DEFAULT = "";

	private static final int TEXT_FIELD_WIDTH = 50;

	private static final String[] languages = { "", "awk", "C", "C++", "C#",
			"EmacsLisp", "GCC-source", "Glade", "Java", "JavaProperties",
			"librep", "Lisp", "NXStringTable", "ObjectiveC", "Perl", "PHP",
			"PO", "Python", "RST", "Scheme", "Smalltalk", "Tcl", "YCP" };

	private static final String MSGFMT_RESOURCE_TITLE = "Resource Name for Java (-r)";

	public static final String MSGFMT_RESOURCE_PROPERTY = "GETTEXT_RUNTIME_BASENAME";

	private static final String MSGFMT_RESOURCE_DEFAULT = "";

	private Text extensionText;

	private Text keywordText;

	private Text xoutputText;

	private Button xoutputBrowseButton;

	private Text moutputText;

	private Button moutputBrowseButton;

	private IProject project;

	private Combo languageCombo;

	private Text fromcodeText;

	private Text domainnameText;

	/**
	 * Holds path to gettext runtime property file. This file is searched for a
	 * <em>basename</em> key and fills with this value the {@link #messagesText}
	 */
	private Text i18nText;

	private Button i18nBrowseButton;

	/** Holds path to gettext runtime properties class. */
	private Text messagesText;

	/**
	 * Constructor for SamplePropertyPage.
	 */
	public ProjectPropertyPage() {
		super();
	}

	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	@Override
	protected Control createContents(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		final GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		this.addHeaderSection(composite);
		this.addSpace(composite);

		this.addXgettextSection(composite);
		this.addSpace(composite);
		this.addMsgmftSection(composite);

		return composite;
	}

	/**
	 * @param parent
	 */
	private void addHeaderSection(final Composite parent) {
		final Composite composite = this.createGroup(parent,
				ProjectPropertyPage.GENERAL_TITLE);

		// Label for language field
		final Label pathLabel = new Label(composite, SWT.NONE);
		pathLabel.setText(ProjectPropertyPage.LANGUAGE_TITLE);

		// Languages
		this.languageCombo = new Combo(composite, SWT.DROP_DOWN);
		try {
			final String language = ((IResource) this.getElement())
					.getPersistentProperty(new QualifiedName(
							ProjectPropertyPage.QUALIFIER,
							ProjectPropertyPage.LANGUAGE_PROPERTY));
			int i = 0;
			for (final String lang : ProjectPropertyPage.languages) {
				this.languageCombo.add(lang);
				if (lang.equals(language)) {
					this.languageCombo.select(i);
				}
				i++;
			}
		} catch (final CoreException e) {
			this.languageCombo.select(0);
		}

	}

	/**
	 * @param parent
	 */
	private void addSpace(final Composite parent) {
		final Label separator = new Label(parent, SWT.NONE);
		final GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}

	/**
	 * @param parent
	 */
	private void addXgettextSection(final Composite parent) {

		final Composite composite = this.createGroup(parent,
				ProjectPropertyPage.XGETTEXT_TITLE);

		// Label for extension field
		final Label extensionLabel = new Label(composite, SWT.NONE);
		extensionLabel.setText(ProjectPropertyPage.XGETTEXT_EXTENSION_TITLE);

		// Extension text field
		this.extensionText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData();
		gd.widthHint = this
				.convertWidthInCharsToPixels(ProjectPropertyPage.TEXT_FIELD_WIDTH);
		gd.horizontalSpan = 2;
		this.extensionText.setLayoutData(gd);

		// Populate extension text field
		try {
			final String extension = ((IResource) this.getElement())
					.getPersistentProperty(new QualifiedName(
							ProjectPropertyPage.QUALIFIER,
							ProjectPropertyPage.XGETTEXT_EXTENSION_PROPERTY));
			this.extensionText.setText(extension != null ? extension
					: ProjectPropertyPage.XGETTEXT_EXTENSION_DEFAULT);
		} catch (final CoreException e) {
			this.extensionText
					.setText(ProjectPropertyPage.XGETTEXT_EXTENSION_DEFAULT);
		}

		// Label for keyword field
		final Label keywordLabel = new Label(composite, SWT.NONE);
		keywordLabel.setText(ProjectPropertyPage.XGETTEXT_KEYWORD_TITLE);

		// Keyword text field
		this.keywordText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.widthHint = this
				.convertWidthInCharsToPixels(ProjectPropertyPage.TEXT_FIELD_WIDTH);
		gd.horizontalSpan = 2;
		this.keywordText.setLayoutData(gd);

		// Populate keyword text field
		try {
			final String keyword = ((IResource) this.getElement())
					.getPersistentProperty(new QualifiedName(
							ProjectPropertyPage.QUALIFIER,
							ProjectPropertyPage.XGETTEXT_KEYWORD_PROPERTY));
			this.keywordText.setText(keyword != null ? keyword : "");
		} catch (final CoreException e) {
			this.keywordText.setText("");
		}

		// Label for fromcode field
		final Label fromcodeLabel = new Label(composite, SWT.NONE);
		fromcodeLabel.setText(ProjectPropertyPage.XGETTEXT_FROMCODE_TITLE);

		// fromcode text field
		this.fromcodeText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.widthHint = this
				.convertWidthInCharsToPixels(ProjectPropertyPage.TEXT_FIELD_WIDTH);
		gd.horizontalSpan = 2;
		this.fromcodeText.setLayoutData(gd);

		// Populate fromcode text field
		try {
			final String fromcode = ((IResource) this.getElement())
					.getPersistentProperty(new QualifiedName(
							ProjectPropertyPage.QUALIFIER,
							ProjectPropertyPage.XGETTEXT_FROMCODE_PROPERTY));
			this.fromcodeText.setText(fromcode != null ? fromcode : "");
		} catch (final CoreException e) {
			this.fromcodeText.setText("");
		}

		// Label for domain name field
		final Label domainnameLabel = new Label(composite, SWT.NONE);
		domainnameLabel.setText(ProjectPropertyPage.XGETTEXT_DOMAIN_NAME);

		// domain name text field
		this.domainnameText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.widthHint = this
				.convertWidthInCharsToPixels(ProjectPropertyPage.TEXT_FIELD_WIDTH);
		gd.horizontalSpan = 2;
		this.domainnameText.setLayoutData(gd);

		// Populate domain name text field
		try {
			final String fromcode = ((IResource) this.getElement())
					.getPersistentProperty(new QualifiedName(
							ProjectPropertyPage.QUALIFIER,
							ProjectPropertyPage.XGETTEXT_DOMAIN_NAME_PROPERTY));
			this.domainnameText.setText(fromcode != null ? fromcode
					: ProjectPropertyPage.XGETTEXT_DOMAIN_NAME_DEFAULT);
		} catch (final CoreException e) {
			this.domainnameText
					.setText(ProjectPropertyPage.XGETTEXT_DOMAIN_NAME_DEFAULT);
		}

		// Label for output field
		final Label outputLabel = new Label(composite, SWT.NONE);
		outputLabel.setText(ProjectPropertyPage.XGETTEXT_OUTPUT_TITLE);

		// Output text field
		this.xoutputText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.widthHint = this
				.convertWidthInCharsToPixels(ProjectPropertyPage.TEXT_FIELD_WIDTH);
		this.xoutputText.setLayoutData(gd);

		// Populate output text field
		try {
			final String output = ((IResource) this.getElement())
					.getPersistentProperty(new QualifiedName(
							ProjectPropertyPage.QUALIFIER,
							ProjectPropertyPage.XGETTEXT_OUTPUT_PROPERTY));
			this.xoutputText.setText(output != null ? output
					: ProjectPropertyPage.XGETTEXT_OUTPUT_DEFAULT);
		} catch (final CoreException e) {
			this.xoutputText
					.setText(ProjectPropertyPage.XGETTEXT_OUTPUT_DEFAULT);
		}

		this.xoutputBrowseButton = new Button(composite, SWT.PUSH);
		this.xoutputBrowseButton.setText("Browse");
		this.xoutputBrowseButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(final SelectionEvent arg0) {
			}

			public void widgetSelected(final SelectionEvent arg0) {
				final IFolder folder = ProjectPropertyPage.this
						.openSelectFolderDialog();
				if (folder != null) {
					ProjectPropertyPage.this.xoutputText.setText(folder
							.getProjectRelativePath().toString());
				}
			}
		});
	}

	/**
	 * @param parent
	 */
	private void addMsgmftSection(final Composite parent) {

		final Composite composite = this.createGroup(parent,
				ProjectPropertyPage.MSGFMT_TITLE);

		// Label for output field
		final Label outputLabel = new Label(composite, SWT.NONE);
		outputLabel.setText(ProjectPropertyPage.MSGFMT_OUTPUT_TITLE);

		// Output text field
		this.moutputText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData();
		gd.widthHint = this
				.convertWidthInCharsToPixels(ProjectPropertyPage.TEXT_FIELD_WIDTH);
		this.moutputText.setLayoutData(gd);

		// Populate output text field
		try {
			final String owner = ((IResource) this.getElement())
					.getPersistentProperty(new QualifiedName(
							ProjectPropertyPage.QUALIFIER,
							ProjectPropertyPage.MSGFMT_OUTPUT_PROPERTY));
			this.moutputText.setText(owner != null ? owner
					: ProjectPropertyPage.MSGFMT_OUTPUT_DEFAULT);
		} catch (final CoreException e) {
			this.moutputText.setText(ProjectPropertyPage.MSGFMT_OUTPUT_DEFAULT);
		}

		this.moutputBrowseButton = new Button(composite, SWT.PUSH);
		this.moutputBrowseButton.setText("Browse");
		this.moutputBrowseButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(final SelectionEvent arg0) {
			}

			public void widgetSelected(final SelectionEvent arg0) {
				final IFolder folder = ProjectPropertyPage.this
						.openSelectFolderDialog();
				if (folder != null) {
					ProjectPropertyPage.this.moutputText.setText(folder
							.getProjectRelativePath().toString());
				}
			}
		});

		// Label for messages field
		final Label messagesLabel = new Label(composite, SWT.NONE);
		messagesLabel.setText(ProjectPropertyPage.MSGFMT_RESOURCE_TITLE);
		// messages text field
		this.messagesText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd = new GridData();
		gd.widthHint = this
				.convertWidthInCharsToPixels(ProjectPropertyPage.TEXT_FIELD_WIDTH);
		this.messagesText.setLayoutData(gd);
		// populate messages text field
		try {
			final String owner = ((IResource) this.getElement())
					.getPersistentProperty(new QualifiedName(
							ProjectPropertyPage.QUALIFIER,
							ProjectPropertyPage.MSGFMT_RESOURCE_PROPERTY));
			this.messagesText.setText(owner != null ? owner
					: ProjectPropertyPage.MSGFMT_RESOURCE_DEFAULT);
		} catch (final CoreException e) {
			this.messagesText
					.setText(ProjectPropertyPage.MSGFMT_RESOURCE_DEFAULT);
		}

	}

	/**
	 * @param parent
	 * @param title
	 * @return
	 */
	private Composite createGroup(final Composite parent, final String title) {
		final Group group = new Group(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		group.setLayout(layout);
		group.setText(title);

		final GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		group.setLayoutData(data);

		return group;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	@Override
	protected void performDefaults() {
		// Populate the owner text field with the default value
		this.extensionText
				.setText(ProjectPropertyPage.XGETTEXT_EXTENSION_DEFAULT);
		this.keywordText.setText("");
		this.fromcodeText.setText("");
		this.domainnameText
				.setText(ProjectPropertyPage.XGETTEXT_DOMAIN_NAME_DEFAULT);
		this.xoutputText.setText(ProjectPropertyPage.XGETTEXT_OUTPUT_DEFAULT);
		this.moutputText.setText(ProjectPropertyPage.MSGFMT_OUTPUT_DEFAULT);
		this.languageCombo.select(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		// store the value in the owner text field
		try {
			((IResource) this.getElement()).setPersistentProperty(
					new QualifiedName(ProjectPropertyPage.QUALIFIER,
							ProjectPropertyPage.XGETTEXT_EXTENSION_PROPERTY),
					this.extensionText.getText());

			((IResource) this.getElement()).setPersistentProperty(
					new QualifiedName(ProjectPropertyPage.QUALIFIER,
							ProjectPropertyPage.XGETTEXT_KEYWORD_PROPERTY),
					this.keywordText.getText());

			((IResource) this.getElement()).setPersistentProperty(
					new QualifiedName(ProjectPropertyPage.QUALIFIER,
							ProjectPropertyPage.XGETTEXT_FROMCODE_PROPERTY),
					this.fromcodeText.getText());

			((IResource) this.getElement()).setPersistentProperty(
					new QualifiedName(ProjectPropertyPage.QUALIFIER,
							ProjectPropertyPage.XGETTEXT_DOMAIN_NAME_PROPERTY),
					this.domainnameText.getText());

			((IResource) this.getElement()).setPersistentProperty(
					new QualifiedName(ProjectPropertyPage.QUALIFIER,
							ProjectPropertyPage.XGETTEXT_OUTPUT_PROPERTY),
					this.xoutputText.getText());

			((IResource) this.getElement()).setPersistentProperty(
					new QualifiedName(ProjectPropertyPage.QUALIFIER,
							ProjectPropertyPage.MSGFMT_OUTPUT_PROPERTY),
					this.moutputText.getText());

			((IResource) this.getElement()).setPersistentProperty(
					new QualifiedName(ProjectPropertyPage.QUALIFIER,
							ProjectPropertyPage.MSGFMT_RESOURCE_PROPERTY),
					this.messagesText.getText());

			int index = this.languageCombo.getSelectionIndex();
			if (index > -1) {
				((IResource) this.getElement()).setPersistentProperty(
						new QualifiedName(ProjectPropertyPage.QUALIFIER,
								ProjectPropertyPage.LANGUAGE_PROPERTY),
						ProjectPropertyPage.languages[index]);
			}
		} catch (final CoreException e) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	private IFolder openSelectFolderDialog() {
		this.project = ((IResource) this.getElement()).getProject();

		final FolderSelectionDialog dialog = new FolderSelectionDialog(this
				.getShell(), new WorkbenchLabelProvider(),
				new WorkbenchContentProvider() {
				});

		dialog.setInput(this.project.getWorkspace());
		dialog.addFilter(new ViewerFilter() {

			@Override
			public boolean select(final Viewer viewer,
					final Object parentElement, final Object element) {
				if (element instanceof IProject) {
					return ((IProject) element)
							.equals(ProjectPropertyPage.this.project);
				} else {
					return element instanceof IFolder;
				}
			}
		});
		dialog.setAllowMultiple(false);
		dialog.setTitle("Folder Selection");
		dialog.setMessage("Choose Output Folder");

		dialog.setValidator(new ISelectionStatusValidator() {

			public IStatus validate(final Object[] selection) {
				final String id = ToolsPlugin.getPluginId();
				if (selection == null || selection.length != 1
						|| !(selection[0] instanceof IFolder)) {
					return new Status(IStatus.ERROR, id, IStatus.ERROR, "",
							null);
				}
				return new Status(IStatus.OK, id, IStatus.OK, "", null);
			}
		});

		if (dialog.open() == Window.OK) {
			return (IFolder) dialog.getFirstResult();
		}
		return null;
	}

}