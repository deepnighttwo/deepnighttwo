package regexpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpression {

	/**
	 * @param args
	 */

	private static final String W3C_TYPE_NS = "http://www.w3.org/[0-9]{4}/XMLSchema";

	private static final Pattern W3C_TYPE_NS_PATTERN = Pattern.compile(
			W3C_TYPE_NS, Pattern.CASE_INSENSITIVE);

	public static void main(String[] args) {

		Matcher matcher = W3C_TYPE_NS_PATTERN.matcher("http://www.w3.org/2001/XMLSchema");
		System.out.println(matcher.matches());
	}

}
