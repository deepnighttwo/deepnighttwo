package annotationlearn;

import java.lang.reflect.Method;

public class TestAnnotationIntercepter {
	public void checkIncerecpter(Class<?> cl) {
		Method[] all = cl.getMethods();
		for (Method method : all) {
			MyTestAnnotion annotation = method
					.getAnnotation(MyTestAnnotion.class);
			if (annotation == null) {
				System.out.println("No annotation found for method "
						+ method.getName() + " for "
						+ MyTestAnnotion.class.getName());
			} else {
				System.out.println(annotation.checkIt());
			}
		}
	}

	public static void main(String[] args) {
		TestAnnotationIntercepter adi = new TestAnnotationIntercepter();
		adi.checkIncerecpter(TestAnnotationIntercepter.class);
	}

	@MyTestAnnotion(checkIt = "hello, shuofenglxy")
	public static void doSomething() {
		System.out.println("This is an annotation example!");
	}

	@MyTestAnnotion()
	public static void doHello() {
		System.out.println("This is an annotation example!");
	}

	public static void doOtherthing() {
		System.out.println("This is normal method!");
	}

}
