package privateinnerclass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CreateInstanceOfPrivateInnerClass {

	/**
	 * @param args
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, SecurityException, NoSuchMethodException,
			IllegalArgumentException, InvocationTargetException {
		Class clz = Outter.class;
		Class[] clzes = clz.getDeclaredClasses();
		for (Class inner : clzes) {
			if (inner.getName().equals("privateinnerclass.Outter$Inner")) {
				System.out.println(inner);
				Constructor ccc = inner.getDeclaredConstructor(new Class[0]);
				ccc.setAccessible(true);
				Object privateInner = ccc.newInstance();
				Method privateMethod = inner.getDeclaredMethod("printsth",
						new Class[0]);
				privateMethod.setAccessible(true);
				privateMethod.invoke(privateInner, new Object[0]);
			}
		}
	}

}
