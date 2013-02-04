package amazon.papertest1;

import junit.framework.Assert;

import org.junit.Test;

public class MaxCommSumCounterFasterHashTest {
	@Test
	public void test() {
		MaxCommSumCounterFasterHash faster = new MaxCommSumCounterFasterHash();
		int ret = faster.countSum(new int[] { 2, 4, 5, 6, 4 });
		Assert.assertEquals(ret, 8);
		ret = faster.countSum(new int[] { 1, 2, 1, 3, 1 });
		Assert.assertEquals(ret, 3);
	}
}
