package classloader;

public class ShowClassLoaders {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Get classpath values

		String bootClassPath = System.getProperty("sun.boot.class.path");

		String extClassPath = System.getProperty("java.ext.dirs");

		String appClassPath = System.getProperty("java.class.path");

		// Print them out

		System.out.println("Bootstrap classpath =" + bootClassPath + "\n");

		System.out.println("Extensions classpath =" + extClassPath + "\n");

		System.out.println("Application classpath=" + appClassPath + "\n");

		// Load classes

		new sun.security.pkcs.EncodingException();
		System.out.println("String\t" + String.class.getClassLoader());
		System.out.println("Object\t" + Object.class.getClassLoader());
		System.out.println("sun.security.pkcs.EncodingException\t"
				+ sun.security.pkcs.EncodingException.class.getClassLoader());
		System.out.println("System \t" + ClassLoader.getSystemClassLoader());
		ClassLoader cl = ShowClassLoaders.class.getClassLoader();
		while (cl != null) {
			System.out.println("Current\t" + cl);
			cl = cl.getParent();
		}

		System.out.println("Context \t"
				+ Thread.currentThread().getContextClassLoader());
	}
}
