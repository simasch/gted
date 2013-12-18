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
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;

/**
 * The Class POKeyWordScanner.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.2 $, $Date: 2008/08/12 11:19:07 $
 */
public class POKeyWordScanner extends RuleBasedScanner {

	/**
	 * The Constructor.
	 * 
	 * @param manager
	 *            the manager
	 */
	public POKeyWordScanner(final ColorManager manager) {
		final IToken poComment = new Token(new TextAttribute(manager
				.getColor(IPOColorConstants.PO_COMMENT)));
		final IToken poMsgid = new Token(new TextAttribute(manager
				.getColor(IPOColorConstants.PO_KEYWORD)));
		final IToken poMsgidplural = new Token(new TextAttribute(manager
				.getColor(IPOColorConstants.PO_KEYWORD)));
		final IToken poMsgstr = new Token(new TextAttribute(manager
				.getColor(IPOColorConstants.PO_KEYWORD)));
		final IToken string = new Token(new TextAttribute(manager
				.getColor(IPOColorConstants.PO_STRING)));

		final IRule[] rules = new IRule[6];

		rules[0] = new EndOfLineRule("#", poComment);
		rules[1] = new SingleLineRule("msgid", " ", poMsgid);
		rules[2] = new SingleLineRule("msgid_plural", " ", poMsgidplural);
		rules[3] = new SingleLineRule("msgstr", " ", poMsgstr);
		rules[4] = new SingleLineRule("\"", "\"", string, '\\');
		rules[5] = new WhitespaceRule(new POWhitespaceDetector());

		this.setRules(rules);
	}
}
