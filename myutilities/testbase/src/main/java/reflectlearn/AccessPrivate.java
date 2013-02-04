package reflectlearn;

import java.lang.reflect.Field;

public class AccessPrivate {
    public static void main(String[] args) throws ClassNotFoundException,
            SecurityException, NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Class<?> clazz = Class.forName("reflectlearn.PrivateProps");
        Field strField = clazz.getDeclaredField("strVal");
        strField.setAccessible(true);
        PrivateProps pp = new PrivateProps();
        strField.set(pp, "asdfasdf");
        System.out.println("Done");
    }
}
