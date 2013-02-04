package com.deepnighttwo.resourceresolver.basic.resolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.json.JSONException;
import org.json.JSONObject;

import com.deepnighttwo.resourceresolver.basic.Activator;
import com.deepnighttwo.resourceresolver.ui.resolver.IResourceResolver;
import com.deepnighttwo.resourceresolver.ui.resolver.ResourceResolverColumn;
import com.deepnighttwo.resourceresolver.ui.resolver.data.DateData;
import com.deepnighttwo.resourceresolver.ui.resolver.data.IResourceDetailsData;
import com.deepnighttwo.resourceresolver.ui.resolver.data.IntegerData;
import com.deepnighttwo.resourceresolver.ui.resolver.data.LinkData;
import com.deepnighttwo.resourceresolver.ui.resolver.data.TextData;
import com.deepnighttwo.resourceresolver.ui.views.utils.FileNameSeparater;

public class TestResolver implements IResourceResolver {

	public TestResolver() {
	}

	@Override
	public ResourceResolverColumn[] getResolveColumnNames() {
		return new ResourceResolverColumn[] {
				new ResourceResolverColumn("Local Location", 90, (Image) null),
				new ResourceResolverColumn("Modified Time", 90, (Image) null),
				new ResourceResolverColumn("Movie Name", 90, null),
				new ResourceResolverColumn("Year", 90, null),
				new ResourceResolverColumn("Search On VeryCD", 90, Activator
						.getImageDescriptor("icons/verycd.png").createImage()),
				new ResourceResolverColumn("Translated", 90, (Image) null) };
	}

	@Override
	public IResourceDetailsData[] getResolvedData(final File resourcePath) {
		List<IResourceDetailsData> testData = new ArrayList<IResourceDetailsData>();
		String resourceName = resourcePath.getName();

		testData.add(new LinkData(resourcePath.toString()));
		testData.add(new DateData(resourcePath.lastModified()));
		String[] nameAndYear = null;
		try {
			nameAndYear = FileNameSeparater.getMovieNameAndYear(resourceName);
		} catch (Throwable ex) {
			ex.printStackTrace();
			nameAndYear = new String[] { "NA", "NA" };
		}

		testData.add(new TextData(nameAndYear[0]));

		testData.add(new IntegerData(nameAndYear[1]));

		testData.add(new LinkData("http://www.verycd.com/search/folders/"
				+ nameAndYear[0]));

		String result;
		try {
			result = translateToChinese(nameAndYear[0]);
		} catch (Exception e) {
			result = "Error Occured!";
			e.printStackTrace();
		}
		testData.add(new TextData(result));
		return testData.toArray(new IResourceDetailsData[0]);
	}

	private static final String API_KEY = "AIzaSyCzkxaoqCGSMLQ2aoR5smmX39bksGs7l7E";

	public static String translateToChinese(String content) throws IOException,
			JSONException {
		String contentEncoded = URLEncoder.encode(content, "UTF-8");

		URL url = new URL(
				"https://ajax.googleapis.com/ajax/services/language/translate?"
						+ "v=1.0&q=" + contentEncoded
						+ "&langpair=en%7Czh-CN&key=" + API_KEY);

		URLConnection connection = url.openConnection();

		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "UTF-8"));
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		String jsonContent = builder.toString();
		// System.out.println(jsonContent);
		JSONObject json = new JSONObject(jsonContent);
		String data = json.getJSONObject("responseData").getString(
				"translatedText");
		return data;
	}
}
