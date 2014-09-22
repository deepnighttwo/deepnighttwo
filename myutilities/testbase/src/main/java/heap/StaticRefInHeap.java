package heap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * User: mzang
 * Date: 2014-07-30
 * Time: 15:27
 */
public class StaticRefInHeap {

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        URLClassLoader classLoader = new URLClassLoader(
                new URL[]{new URL("file:///D:/mymise/deepnighttwo/myutilities/testprj/target/testprj-1.0.0.jar")},
                StaticRefInHeap.class.getClassLoader()
        );

        Class clazz = classLoader.loadClass("com.deepnighttwo.StTest");
        Method method = clazz.getMethod("go", new Class[0]);
        method.invoke(null,new Object[0]);

    }

}
