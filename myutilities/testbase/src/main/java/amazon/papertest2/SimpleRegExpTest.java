package amazon.papertest2;

import org.junit.Assert;
import org.junit.Test;

public class SimpleRegExpTest {

	@Test
	public void test() {
		Assert.assertTrue(SimpleRegularExpParser.parse("abc", "abc"));
		Assert.assertTrue(SimpleRegularExpParser.parse("*", "abc"));
		Assert.assertTrue(SimpleRegularExpParser.parse("*abc", "abc"));
		Assert.assertTrue(SimpleRegularExpParser.parse("*abc", "aaabbbabc"));
		Assert.assertTrue(SimpleRegularExpParser.parse("a*bc", "aaabbbabc"));
		Assert.assertTrue(SimpleRegularExpParser.parse("a*bc", "abc"));

		Assert.assertTrue(SimpleRegularExpParser.parse("a*", "abc"));
		Assert.assertTrue(SimpleRegularExpParser.parse("a*", "a"));
		Assert.assertTrue(SimpleRegularExpParser.parse("a*", "aa"));
		Assert.assertTrue(SimpleRegularExpParser.parse("a*", "abcdef"));

		Assert.assertTrue(SimpleRegularExpParser.parse("*abc*", "abc"));

		Assert.assertTrue(SimpleRegularExpParser.parse("*****", "abc"));
		Assert.assertTrue(SimpleRegularExpParser.parse("...", "abc"));

		Assert.assertTrue(SimpleRegularExpParser.parse(".*", "abc"));

		Assert.assertTrue(SimpleRegularExpParser.parse(".bc*", "abc"));

		Assert.assertTrue(SimpleRegularExpParser.parse(".b*c*a", "abca"));

		Assert.assertTrue(SimpleRegularExpParser.parse("*", ""));

		Assert.assertFalse(SimpleRegularExpParser.parse("abc", "abcd"));
		Assert.assertFalse(SimpleRegularExpParser.parse("*a", "abcd"));
		Assert.assertFalse(SimpleRegularExpParser.parse("A", ""));
		Assert.assertFalse(SimpleRegularExpParser.parse(".a*c", "abc"));
		Assert.assertFalse(SimpleRegularExpParser.parse("a.*b", "abc"));
		Assert.assertFalse(SimpleRegularExpParser.parse("..", "abc"));
		Assert.assertFalse(SimpleRegularExpParser.parse("", ""));
		Assert.assertFalse(SimpleRegularExpParser.parse("", "abc"));

	}

}
