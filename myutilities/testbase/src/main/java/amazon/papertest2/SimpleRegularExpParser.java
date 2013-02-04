package amazon.papertest2;

import java.util.ArrayList;
import java.util.List;

/**
 * About 5.Write up an analysis on the run-time complexity of your code. 
 * 
 * This parser will look back and at the badest condition, the run-time
 * complexity is O(n*m). n stands for the length of regular expression and m
 * stands for the length of input.
 * 
 * @author Mark Zang
 * 
 */
public class SimpleRegularExpParser {

	public SimpleMatcher matcher;

	private int currIdx = 0;

	private String input;

	private static final char DOT = '.';

	private static final char STAR = '*';

	private SimpleRegularExpParser(String regularExp) {

		List<SimpleMatcher> matcherList = new ArrayList<SimpleMatcher>(regularExp.length());
		SimpleMatcher lookBack = null;
		for (int i = 0; i < regularExp.length(); i++) {
			char ch = regularExp.charAt(i);
			SimpleMatcher curr = null;

			switch (ch) {
			case STAR:
				curr = new WildCharMatcher();
				lookBack = curr;
				break;
			case DOT:
				curr = new OneCharMatcher();
				break;
			default:
				curr = new CharMatcher(ch);
				break;
			}

			matcherList.add(curr);
			if (curr != lookBack) {
				curr.setLookBack(lookBack);
			}
		}

		matcher = matcherList.get(0);

		for (int i = 0; i < matcherList.size() - 1; i++) {
			matcherList.get(i).setNext(matcherList.get(i + 1));
		}

	}

	public static boolean parse(String regularExp, String input) {

		if (regularExp == null || regularExp.length() == 0) {
			return false;
		}

		SimpleRegularExpParser parser = new SimpleRegularExpParser(regularExp);

		return parser.parse(input);

	}

	public static SimpleRegularExpParser getParser(String regularExp) {
		if (regularExp == null || regularExp.length() == 0) {
			return null;
		}

		SimpleRegularExpParser parser = new SimpleRegularExpParser(regularExp);

		return parser;
	}

	public boolean parse(String input) {
		this.input = input;
		currIdx = 0;

		SimpleMatcher currM = matcher;
		int len = input.length();

		while (currM != null) {
			// not enough input to match
			if (currIdx >= len && (currM instanceof WildCharMatcher == false)) {
				return false;
			}

			boolean ret = currM.accept();
			if (ret == true) {
				currM = currM.next;
			} else {
				if (currM.lookBack == null) {
					return false;
				} else {
					currM = currM.lookBack;
				}
			}
		}

		if (currIdx != len) {
			return false;
		}

		return true;
	}

	abstract class SimpleMatcher {

		SimpleMatcher lookBack;
		SimpleMatcher next;

		public SimpleMatcher() {
		}

		public SimpleMatcher getNext() {
			return next;
		}

		public void setNext(SimpleMatcher next) {
			this.next = next;
		}

		public SimpleMatcher getLookBack() {
			return lookBack;
		}

		public void setLookBack(SimpleMatcher lookBack) {
			this.lookBack = lookBack;
		}

		public abstract boolean accept();

	}

	class CharMatcher extends SimpleMatcher {

		char ch;

		public CharMatcher(char ch) {
			this.ch = ch;
		}

		@Override
		public boolean accept() {
			char currChar = input.charAt(currIdx);
			boolean ret = (ch == currChar);
			if (ret == true) {
				currIdx++;
			}
			return ret;
		}
	}

	class OneCharMatcher extends SimpleMatcher {

		public OneCharMatcher() {
		}

		@Override
		public boolean accept() {
			currIdx++;
			return true;
		}

	}

	class WildCharMatcher extends SimpleMatcher {
		private int savePoint = -1;

		public WildCharMatcher() {
		}

		@Override
		public boolean accept() {
			if (next == null) {
				currIdx = input.length();
				return true;
			}
			if (savePoint == -1) {
				savePoint = currIdx;
			} else {
				savePoint++;
				currIdx = savePoint;
			}
			return true;
		}

	}

}
