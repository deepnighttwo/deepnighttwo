package utils;

import java.net.URL;

public class ClassLoaderUtils {

    public static String getClassRealLocation(Class<?> clz) {
        try {
            ClassLoader cl = clz.getClassLoader();
            if (cl == null) {
                cl = ClassLoader.getSystemClassLoader();
            }
            URL url = cl.getResource(clz.getName().replace('.', '/') + ".class");
            System.out.println(url.toString());
            return url.toString();
        } catch (Throwable t) {
            return null;
        }
    }

}
