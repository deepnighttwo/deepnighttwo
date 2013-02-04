package javaregexpressionlearn;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaPatternExpression {

	/**
	 * @param args
	 */

	private static final String W3C_TYPE_NS = "http://www.w3.org/[0-9]{4}/XMLSchema";

	public static void main(String[] args) {
		Pattern pattern = Pattern
				.compile(W3C_TYPE_NS, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher("http://www.w3.org/1999/XMLSchema");
		System.out.println(matcher.matches());

		System.out.println(MessageFormat.format("asdasdasdf{{0}}asdfasdf",
				"EEEEEEEEEEEEEEEEEE"));
	}
}
