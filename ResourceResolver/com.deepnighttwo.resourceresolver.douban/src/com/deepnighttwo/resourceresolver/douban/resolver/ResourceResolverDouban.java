package com.deepnighttwo.resourceresolver.douban.resolver;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.swt.graphics.Image;

import com.deepnighttwo.resourceresolver.douban.Activator;
import com.deepnighttwo.resourceresolver.douban.resolver.utils.DoubanSearchParser;
import com.deepnighttwo.resourceresolver.douban.resolver.utils.XMLSearchUnit;
import com.deepnighttwo.resourceresolver.ui.resolver.IResourceResolver;
import com.deepnighttwo.resourceresolver.ui.resolver.ResourceResolverColumn;
import com.deepnighttwo.resourceresolver.ui.resolver.data.DoubleData;
import com.deepnighttwo.resourceresolver.ui.resolver.data.IResourceDetailsData;
import com.deepnighttwo.resourceresolver.ui.resolver.data.IntegerData;
import com.deepnighttwo.resourceresolver.ui.resolver.data.LinkData;
import com.deepnighttwo.resourceresolver.ui.resolver.data.LongTextData;
import com.deepnighttwo.resourceresolver.ui.resolver.data.TextData;
import com.deepnighttwo.resourceresolver.ui.views.utils.FileNameSeparater;

enum Douban {
	Search, DetailLink, Name, CNName, Rating, RaterCount, Details
}

public class ResourceResolverDouban implements IResourceResolver {

	private static String API_KEY = "0da7011cc827219828f50356587564c8";

	private static String DOUBAN_WEB_SEARCH_URL = "http://movie.douban.com/subject_search?search_text={0}&cat=1002";

	private static String DOUBAN_API_SEARCH_URL = "http://api.douban.com/movie/subjects?q={0}&start-index=1&max-results=1&apikey={1}";

	private static int CALL_COUNTER = 0;

	private static Image DOUBAN_IMAGE = Activator.getImageDescriptor(
			"icons/douban.gif").createImage();

	@Override
	public ResourceResolverColumn[] getResolveColumnNames() {
		return new ResourceResolverColumn[] {
				new ResourceResolverColumn("Search On Douban", 90, DOUBAN_IMAGE),
				new ResourceResolverColumn("View Details from Douban", 90,
						DOUBAN_IMAGE),
				new ResourceResolverColumn("Name from Douban", 90, DOUBAN_IMAGE),
				new ResourceResolverColumn("Chinese Name from Douban", 90,
						DOUBAN_IMAGE),
				new ResourceResolverColumn("Rating", 90, DOUBAN_IMAGE),

				new ResourceResolverColumn("Rater Count", 90, DOUBAN_IMAGE),

				new ResourceResolverColumn("Details from Douban", 90,
						DOUBAN_IMAGE) };
	}

	@Override
	public IResourceDetailsData[] getResolvedData(File resourcePath) {

		CALL_COUNTER += 2;
		if (CALL_COUNTER >= 40) {
			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			CALL_COUNTER = 0;
		}
		IResourceDetailsData[] results = new IResourceDetailsData[7];
		String fileName = resourcePath.getName();
		fileName = FileNameSeparater.getMovieNameAndYear(fileName)[0];
		String contentEncoded = "";
		try {
			contentEncoded = URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// should not happen
		}
		results[0] = new LinkData(MessageFormat.format(DOUBAN_WEB_SEARCH_URL,
				contentEncoded));
		try {

			URL urlSearch = new URL(MessageFormat.format(DOUBAN_API_SEARCH_URL,
					contentEncoded, API_KEY));

			URLConnection connSearch = urlSearch.openConnection();

			DoubanSearchParser parserSearch = new DoubanSearchParser();
			Map<XMLSearchUnit, String> linkMap = parserSearch.parseResults(
					connSearch.getInputStream(),
					DoubanSearchParser.DETAILS_LINK_API_PATH,
					DoubanSearchParser.DETAILS_LINK_URL_PATH);
			String apiLinkDetails = linkMap
					.get(DoubanSearchParser.DETAILS_LINK_API_PATH);
			String urlLinkDetails = linkMap
					.get(DoubanSearchParser.DETAILS_LINK_URL_PATH);
			results[1] = new LinkData(urlLinkDetails);
			if (apiLinkDetails == null) {
				results[2] = new TextData("No title found!");
			} else {
				String urlWithAPIKey = MessageFormat.format(apiLinkDetails
						+ "?apikey={0}", API_KEY);
				log("Processing:\t" + urlWithAPIKey);
				URL urlGetDetails = new URL(urlWithAPIKey);

				URLConnection connGetDetails = urlGetDetails.openConnection();

				DoubanSearchParser parserDetails = new DoubanSearchParser();
				Map<XMLSearchUnit, String> detailsMap = parserDetails
						.parseResults(
								connGetDetails.getInputStream(),
								DoubanSearchParser.DETAILS_TITLE_PATH,
								DoubanSearchParser.DETAILS_CHINESE_NAME_PATH,
								DoubanSearchParser.DETAILS_RATINGE_PATH,
								DoubanSearchParser.DETAILS_RATINGE_RATER_COUNT_PATH,
								DoubanSearchParser.DETAILS_CONTENT_PATH);
				results[2] = new TextData(
						detailsMap.get(DoubanSearchParser.DETAILS_TITLE_PATH));

				results[3] = new TextData(
						detailsMap
								.get(DoubanSearchParser.DETAILS_CHINESE_NAME_PATH));

				results[4] = new DoubleData(
						detailsMap.get(DoubanSearchParser.DETAILS_RATINGE_PATH));

				results[5] = new IntegerData(
						detailsMap
								.get(DoubanSearchParser.DETAILS_RATINGE_RATER_COUNT_PATH));

				results[6] = new LongTextData(
						detailsMap.get(DoubanSearchParser.DETAILS_CONTENT_PATH));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < results.length; i++) {
			if (results[i] == null) {
				if (i == 4) {
					results[i] = new DoubleData(-1.0);
				}
				if (i == 5) {
					results[i] = new IntegerData(-1);
				} else {
					results[i] = new TextData("N/A");
				}
			}
		}

		return results;
	}

	private static void log(String msg) {
		System.out.println(msg);
	}

}
