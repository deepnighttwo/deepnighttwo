/**
 * 
 * @author  Moon Zang
 * 
 */

package test;

public class TestC {
    String str = "asdf";

    public void testMethod() {
        testMethod1(new Object());
        String v = str;
    }

    public void testMethod1(Object asdf) {
        String v = str;
        asdf.hashCode();
    }
}
