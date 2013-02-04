package json.learn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectUseApp {

	/**
	 * @param args
	 * @throws JSONException
	 * @throws IOException
	 */
	public static void main(String[] args) throws JSONException, IOException {

		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				JSONObjectUseApp.class.getResourceAsStream("data1.json"),
				"UTF-8"));
		String line = null;

		while ((line = reader.readLine()) != null) {
			sb.append(line + "\r\n");
		}

		JSONObject json = new JSONObject(sb.toString());
		log("String from JSON\t" + json.getString("strValue"));
		log("Null from JSON\t" + json.get("nullValue"));
		log("Integer from JSON\t" + json.getInt("intvalue"));
		log("Double from JSON\t" + json.getDouble("doublevalue"));
		log("Boolean from JSON\t" + json.getBoolean("booleanValue"));

		log("String from JSON Array\t"
				+ json.getJSONArray("array").getString(0));

		log("String from inner JSON Object \t"
				+ json.getJSONObject("innerOBJ").getString("innerStr"));

		log("Int from JSONArray from JSON Object \t"
				+ json.getJSONArray("array").getJSONObject(6)
						.getInt("innerInteger"));

		log("Int from JSON Array from JSON Array \t"
				+ json.getJSONArray("array").getJSONArray(7).getString(0));
	}

	private static void log(Object obj) {
		System.out.println(obj.toString());
	}
}
