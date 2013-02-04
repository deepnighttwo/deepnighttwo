package xstreamlearn;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XStreamTestMain {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// write();
		read();
	}

	private static void write() throws FileNotFoundException {
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("ResponseSet", PacketResponseTest.class);
		xstream.alias("data", LinkedHashMap.class);

		PacketResponseTest resp = new PacketResponseTest();
		resp.setResult("asdfasdf");

		List<LinkedHashMap<String, Object>> data = new ArrayList<LinkedHashMap<String, Object>>();
		data.add(getMap());
		data.add(getMap());
		resp.setDatalist(data);
		resp.setParams(getMap());
		FileOutputStream fs = new FileOutputStream("c:/employeedata.txt");
		xstream.toXML(resp, fs);
	}

	private static void read() {
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("ResponseSet", PacketResponseTest.class);
		xstream.alias("data", LinkedHashMap.class);

		Object obj = xstream.fromXML(XStreamTestMain.class
				.getResourceAsStream("responsedata.xml"));
		System.out.println(obj);
	}

	private static LinkedHashMap<String, Object> getMap() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");
		return map;
	}
}
