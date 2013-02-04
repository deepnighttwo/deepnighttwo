package avltree.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AVLTreeFunctionTest.class, AVLTreePerfTest.class,
		RBTreePerfTest.class })
public class TreeTestSuite {

}
