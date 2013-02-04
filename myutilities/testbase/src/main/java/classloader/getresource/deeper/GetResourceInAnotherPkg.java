package classloader.getresource.deeper;

import java.io.IOException;
import java.util.Properties;

public class GetResourceInAnotherPkg {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Properties props = new Properties();
		props.load(GetResourceInAnotherPkg.class.getClassLoader()
				.getResourceAsStream("classloader/getresource/test.properties"));
		System.out.println(props.get("test"));
	}

}
