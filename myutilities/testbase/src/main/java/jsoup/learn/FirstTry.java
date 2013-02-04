package jsoup.learn;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FirstTry {

	public static void main(String[] args) throws IOException {
		Document doc = Jsoup
				.connect("http://www.envir.gov.cn/airnews/index.asp")
				.data("Fdate", "2000-6-1").data("Tdate", "2000-6-8")
				.userAgent("I'm jsoup")

				.timeout(3000).post();
		// System.out.println(doc);

		Elements eles = doc.select("table[bordercolor] > tr");

		eles.remove(0);

		for (Element ele : eles) {
			Elements rows = ele.select("td");
			for (Element row : rows) {
				System.out.println(row.ownText());
			}
		}

		// Element content = doc.getElementById("content");
		// Elements links = content.getElementsByTag("a");
		// for (Element link : links) {
		// String linkHref = link.attr("href");
		// String linkText = link.text();
		// System.out.println(linkHref);
		// System.out.println(linkText);
		// }
	}

}
