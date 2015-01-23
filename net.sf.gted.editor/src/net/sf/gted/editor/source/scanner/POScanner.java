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
package net.sf.gted.editor.source.scanner;

import net.sf.gted.editor.source.ColorManager;
import net.sf.gted.editor.source.IPOColorConstants;
import net.sf.gted.editor.source.POWhitespaceDetector;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;

/**
 * The Class POScanner.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.2 $, $Date: 2008/08/12 11:19:07 $
 */
public class POScanner extends RuleBasedScanner {

	/**
	 * The Constructor.
	 * 
	 * @param manager
	 *            the manager
	 */
	public POScanner(final ColorManager manager) {

		final IToken string = new Token(new TextAttribute(manager
				.getColor(IPOColorConstants.PO_STRING)));

		final IRule[] rules = new IRule[2];
		rules[0] = new SingleLineRule("\"", "\"", string, '\\');
		rules[1] = new WhitespaceRule(new POWhitespaceDetector());

		this.setRules(rules);
	}
}
