package stringobjcreation;

public class Str2 {

    public void test2(String str) {
        String aaa = "abc";
        System.out.println(aaa == str);
        System.out.println(aaa == str.intern());
    }
}
