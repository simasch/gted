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

import net.sf.gted.editor.source.rules.NonMatchingRule;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

/**
 * The Class POPartitionScanner.
 * 
 * @author $Author: simas_ch $
 * @version $Revision: 1.2 $, $Date: 2008/08/12 11:19:07 $
 */
public class POPartitionScanner extends RuleBasedPartitionScanner {

	/** The Constant PO_DEFAULT. */
	public final static String PO_DEFAULT = "__po_default";

	/** The Constant PO_COMMENT. */
	public final static String PO_COMMENT = "__po_comment";

	/** The Constant PO_MSGID. */
	public final static String PO_MSGID = "__po_msgid";

	/** The Constant PO_MSGID_PLURAL. */
	public final static String PO_MSGID_PLURAL = "__po_msgid_plural";

	/** The Constant PO_MSGSTR. */
	public final static String PO_MSGSTR = "__po_msgstr";

	/**
	 * The Constructor.
	 */
	public POPartitionScanner() {

		final IToken poComment = new Token(POPartitionScanner.PO_COMMENT);
		final IToken poMsgid = new Token(POPartitionScanner.PO_MSGID);
		final IToken poMsgidplural = new Token(
				POPartitionScanner.PO_MSGID_PLURAL);
		final IToken poMsgstr = new Token(POPartitionScanner.PO_MSGSTR);

		final IPredicateRule[] rules = new IPredicateRule[5];

		rules[0] = new NonMatchingRule();
		rules[1] = new EndOfLineRule("#", poComment);
		rules[2] = new SingleLineRule("msgid", " ", poMsgid);
		rules[3] = new SingleLineRule("msgid_plural", " ", poMsgidplural);
		rules[4] = new SingleLineRule("msgstr", " ", poMsgstr);

		this.setPredicateRules(rules);
	}
}
