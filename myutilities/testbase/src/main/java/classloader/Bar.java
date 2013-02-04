package classloader;

import classloader.Baz.Baz;

//$Id: Bar.java 24 2010-05-24 06:34:01Z deepnighttwo $

public class Bar {
    public Bar(String a, String b) {
        System.out.println("bar!asdfsd" + a + " " + b);
        new Baz(a, b);
        Thread.currentThread().getContextClassLoader();
        try {
            Class<?> booClass = Class.forName("classloader.Boo");
            Object boo = booClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
