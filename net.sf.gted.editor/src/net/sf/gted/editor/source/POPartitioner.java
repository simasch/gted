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
package net.sf.gted.editor.source;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;

/**
 * The Class POPartitioner.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.2 $, $Date: 2008/08/12 11:19:05 $
 */
public class POPartitioner extends FastPartitioner {

	/**
	 * The Constructor.
	 * 
	 * @param legalContentTypes
	 *            the legal content types
	 * @param scanner
	 *            the scanner
	 */
	public POPartitioner(final IPartitionTokenScanner scanner,
			final String[] legalContentTypes) {
		super(scanner, legalContentTypes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.text.rules.FastPartitioner#computePartitioning(int,
	 * int, boolean)
	 */
	@Override
	public ITypedRegion[] computePartitioning(final int offset,
			final int length, final boolean includeZeroLengthPartitions) {
		return super.computePartitioning(offset, length,
				includeZeroLengthPartitions);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.text.rules.FastPartitioner#connect(org.eclipse.jface
	 * .text.IDocument, boolean)
	 */
	@Override
	public void connect(final IDocument document,
			final boolean delayInitialization) {
		super.connect(document, delayInitialization);
		// printPartitions(document);
	}

	/**
	 * Prints the partitions.
	 * 
	 * @param document
	 *            the document
	 */
	public void printPartitions(final IDocument document) {
		final StringBuffer buffer = new StringBuffer();

		final ITypedRegion[] partitions = this.computePartitioning(0, document
				.getLength());
		for (final ITypedRegion element : partitions) {
			try {
				buffer.append("Partition type: " + element.getType()
						+ ", offset: " + element.getOffset() + ", length: "
						+ element.getLength());
				buffer.append("\n");
				buffer.append("Text:\n");
				buffer.append(document.get(element.getOffset(), element
						.getLength()));
				buffer.append("\n---------------------------\n\n\n");
			} catch (final BadLocationException e) {
				e.printStackTrace();
			}
		}
		System.out.print(buffer);
	}
}