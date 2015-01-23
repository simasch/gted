/*
 gted - gted.sourceforge.net
 Copyright (C) 2013 by Simon Martinelli, Erlach, Switzerland

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
package net.sf.gted.editor.source;

import net.sf.gted.editor.source.hyperlink.POHyperlinkDetector;
import net.sf.gted.editor.source.scanner.POKeyWordScanner;
import net.sf.gted.editor.source.scanner.POPartitionScanner;
import net.sf.gted.editor.source.scanner.POScanner;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

/**
 * The Class POConfiguration.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.4 $, $Date: 2008/08/12 11:19:04 $
 */
public class POConfiguration extends SourceViewerConfiguration {

	/** The scanner. */
	private POScanner poScanner;

	/** The scanner. */
	private POKeyWordScanner poKeyWordScanner;

	/** The color manager. */
	private ColorManager colorManager;

	private POSourceEditor editor;

	/**
	 * The Constructor.
	 * 
	 * @param colorManager
	 *            the color manager
	 */
	public POConfiguration(final ColorManager colorManager,
			POSourceEditor sourceEditor) {
		this.colorManager = colorManager;
		this.editor = sourceEditor;
	}

	/**
	 * Gets the PO scanner.
	 * 
	 * @return the PO scanner
	 */
	protected POScanner getPOScanner() {
		if (this.poScanner == null) {
			this.poScanner = new POScanner(this.colorManager);
			this.poScanner.setDefaultReturnToken(new Token(new TextAttribute(
					this.colorManager.getColor(IPOColorConstants.DEFAULT))));
		}
		return this.poScanner;
	}

	/**
	 * Gets the PO key word scanner.
	 * 
	 * @return the PO key word scanner
	 */
	protected POKeyWordScanner getPOKeyWordScanner() {
		if (this.poKeyWordScanner == null) {
			this.poKeyWordScanner = new POKeyWordScanner(this.colorManager);
			this.poKeyWordScanner.setDefaultReturnToken(new Token(
					new TextAttribute(this.colorManager
							.getColor(IPOColorConstants.DEFAULT))));
		}
		return this.poKeyWordScanner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jface.text.source.SourceViewerConfiguration#
	 * getPresentationReconciler(org.eclipse.jface.text.source.ISourceViewer)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jface.text.source.SourceViewerConfiguration#
	 * getPresentationReconciler(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public IPresentationReconciler getPresentationReconciler(
			final ISourceViewer sourceViewer) {
		final PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(this
				.getPOScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		dr = new DefaultDamagerRepairer(this.getPOKeyWordScanner());
		reconciler.setDamager(dr, POPartitionScanner.PO_COMMENT);
		reconciler.setRepairer(dr, POPartitionScanner.PO_COMMENT);

		dr = new DefaultDamagerRepairer(this.getPOKeyWordScanner());
		reconciler.setDamager(dr, POPartitionScanner.PO_MSGID);
		reconciler.setRepairer(dr, POPartitionScanner.PO_MSGID);

		dr = new DefaultDamagerRepairer(this.getPOKeyWordScanner());
		reconciler.setDamager(dr, POPartitionScanner.PO_MSGID_PLURAL);
		reconciler.setRepairer(dr, POPartitionScanner.PO_MSGID_PLURAL);

		dr = new DefaultDamagerRepairer(this.getPOKeyWordScanner());
		reconciler.setDamager(dr, POPartitionScanner.PO_MSGSTR);
		reconciler.setRepairer(dr, POPartitionScanner.PO_MSGSTR);

		return reconciler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jface.text.source.SourceViewerConfiguration#
	 * getConfiguredContentTypes(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public String[] getConfiguredContentTypes(final ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
				POPartitionScanner.PO_COMMENT, POPartitionScanner.PO_MSGID,
				POPartitionScanner.PO_MSGID_PLURAL,
				POPartitionScanner.PO_MSGSTR };
	}

	@Override
	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		return new IHyperlinkDetector[] { new POHyperlinkDetector(this.editor) };
	}

}