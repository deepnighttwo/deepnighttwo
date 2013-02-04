package webcl;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class AppMainTest1 {

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws ClassNotFoundException,
			MalformedURLException {
		URLClassLoader cl = new URLClassLoader(new URL[] { new URL(
				"file:\\C:\\Users\\Mark Zang\\Desktop\\JuliaSet.jar") });
		cl.loadClass("com.sun.awt.AWTUtilities");
		cl.loadClass("com.sun.awt.AWTUtilities");

	}

}
