package test.util;

import java.util.ResourceBundle;

public class Util {

	private static final ResourceBundle catalog = ResourceBundle
			.getBundle("Messages");

	public static String _(final String s) {
		return Util.catalog.getString(s);
	}
}