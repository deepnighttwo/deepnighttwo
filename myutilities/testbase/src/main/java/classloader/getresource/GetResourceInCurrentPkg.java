package classloader.getresource;

import java.io.IOException;
import java.util.Properties;

public class GetResourceInCurrentPkg {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// using Class.getResourceAsStream, the default location is the package
		// of current class. It is an instance method, so every instance know
		// that where is the class, and which package it is, and which class
		// loader to
		// use. It is a relative path.
		Properties props = new Properties();

		props.load(GetResourceInCurrentPkg.class
				.getResourceAsStream("../getresource/test.properties"));
		System.out.println(props.get("test"));
		
		props = new Properties();

		props.load(GetResourceInCurrentPkg.class
				.getResourceAsStream("test.properties"));
		System.out.println(props.get("test"));

		props = new Properties();
		props.load(GetResourceInCurrentPkg.class
				.getResourceAsStream("deeper/test2.properties"));
		System.out.println(props.get("test2"));

		// if the path start with /, then it means the path is absolute path.
		props = new Properties();
		props.load(GetResourceInCurrentPkg.class
				.getResourceAsStream("/classloader/getresource/deeper/test2.properties"));
		System.out.println(props.get("test2"));

		// Using class loader, it is always the absolute path.
		props = new Properties();
		props.load(GetResourceInCurrentPkg.class.getClassLoader()
				.getResourceAsStream(
						"classloader/getresource/deeper/test2.properties"));
		System.out.println(props.get("test2"));

	}

}
