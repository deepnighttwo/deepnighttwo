package classloader;

//$Id: Foo.java 11 2010-01-12 02:19:28Z deepnighttwo $

public class Foo {
    static public void main(String args[]) throws Exception {
        System.out.println("fooasdfasdfasdf! " + args[0] + " " + args[1]);
        new Bar(args[0], args[1]);
    }
}
