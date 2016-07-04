package utils;

import java.net.URL;

public class ClassLoaderUtils {

    public static String getClassRealLocation(Class<?> clz) {
        try {
            ClassLoader cl = clz.getClassLoader();
            if (cl == null) {
                cl = ClassLoader.getSystemClassLoader();
            }
<<<<<<< HEAD
=======
            ClassLoader p = cl;
            String clPath = "";
            while (p != null) {
                clPath += p.toString();
                p = p.getParent();
            }
            System.out.println(clPath);
>>>>>>> 72ff47106c94f8f51f9c8365d5aa5cd06f47d2bd
            URL url = cl.getResource(clz.getName().replace('.', '/') + ".class");
            System.out.println(url.toString());
            return url.toString();
        } catch (Throwable t) {
            return null;
        }
    }

}
