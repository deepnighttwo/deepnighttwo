package kaoqin;


import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * User: mzang
 * Date: 2014-11-10
 * Time: 12:06
 */
public class KaoqinAuto {


    public static void main(String[] args) throws IOException, InterruptedException {
        String cookie1 = kaoqinLoginPage();
        Thread.sleep(100000);
        String cookie2 = kaoqinLoginMiddle(cookie1);
        String reqCookie = cookie1 + "; " + cookie2;
//        String reqCookie = "NSC_WT_Isjou_80=ffffffffc3a02fab45525d5f4f58455e445a4a423660; ASP.NET_SessionId=xe0zr1das51btycbhcyhtzjy; defaultLoginName=mzang";
        System.out.println(reqCookie);
        Thread.sleep(100000);

        String content = getKaoqinContent(reqCookie);
    }


    private static String getKaoqinContent(String cookie) throws IOException {

        HttpClient httpclient = new DefaultHttpClient();

        HttpPost req = new HttpPost("http://hrint.sh.ctriptravel.com/hr.ehrms.site/TimeRecordPage.aspx");

        String headerStr = "Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
                "Accept-Encoding:gzip,deflate\n" +
                "Accept-Language:zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4\n" +
                "Cache-Control:no-cache\n" +
                "Connection:keep-alive\n" +
                "Content-Type:application/x-www-form-urlencoded\n" +
                "Host:hrint.sh.ctriptravel.com\n" +
                "Origin:http://hrint.sh.ctriptravel.com\n" +
                "Pragma:no-cache\n" +
                "Referer:http://hrint.sh.ctriptravel.com/hr.ehrms.site/TimeRecordPage.aspx\n" +
                "User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36";

        HttpRequestUtil.addHeaders(req, headerStr);
        cookie = cookie.trim();
        if (cookie != null && cookie.length() > 0) {
            req.setHeader("Cookie", cookie);
        }

        String formStr = "__VIEWSTATE:/wEPDwUKMjA4MTAwNDM0MA8WBB4Jc3RhcnREYXRlBQoyMDE0LTEwLTExHgdlbmREYXRlBQoyMDE0LTExLTExZGSxMBOWgTbIwDAXbNd/csgCzvuW3tU1EExfi7nTigs/BQ==\n" +
                "__EVENTTARGET:\n" +
                "__EVENTARGUMENT:\n" +
                "txtBeginDate:2014-11-01\n" +
                "txtEndDate:2014-11-11\n" +
                "btnSearch: 查 询 \n" +
                "tblUser_Filter_1:\n" +
                "tblUser_Filter_2:\n" +
                "tblUser_Filter_3:\n" +
                "tblUser_Filter_4:\n" +
                "tblUser_Filter_5:\n" +
                "tblUser_Filter_6:\n" +
                "tblUser_Filter_7:\n" +
                "tblUser_ToolBar_CurrentPage:-30\n" +
                "tblUser_ToolBar_TotalPages:-30\n" +
                "tblUser_ToolBar_StepCount:1\n" +
                "tblUser_ToolBar_RecordCount:32\n" +
                "tblUser_ReturnColumns:,1,\n" +
                "tblUser_Filter:\n" +
                "tblUser_SortOrder:ASC\n" +
                "tblUser_SortIndex:0\n" +
                "tblUser_SelectedValues:\n" +
                "tblUser_TableStyle:1";

        HttpRequestUtil.addFormData(req, formStr);

        HttpResponse response = httpclient.execute(req);

        Header encoding = response.getEntity().getContentEncoding();
        Header type = response.getEntity().getContentType();

        System.out.println("encoding:" + encoding);
        System.out.println("type" + type);

        String content = getAllContent(new GZIPInputStream(response.getEntity().getContent()));

        System.out.println(content);

        Document doc = Jsoup.parse(content);
        Element element = doc.getElementById("tblUser");

        Elements rows = element.getElementsByTag("tr");
        for (Element row : rows) {

            String date = row.getElementsByTag("td").get(1).text();
            String come = row.getElementsByTag("td").get(2).text();
            String go = row.getElementsByTag("td").get(3).text();

            System.out.println(date);
            System.out.println(come);
            System.out.println(go);
        }
        return null;
    }

    private static String kaoqinLoginMiddle(String cookie) throws IOException {

        HttpClient httpclient = new DefaultHttpClient();

        HttpPost req = new HttpPost("http://hrint.sh.ctriptravel.com/hr.ehrms.site/WebParts/LoginMiddle.aspx");

        String headerStr = "Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
                "Accept-Encoding:gzip,deflate\n" +
                "Accept-Language:zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4\n" +
                "Cache-Control:no-cache\n" +
                "Connection:keep-alive\n" +
                "Content-Type:application/x-www-form-urlencoded\n" +
                "Host:hrint.sh.ctriptravel.com\n" +
                "Origin:http://hrint.sh.ctriptravel.com\n" +
                "Pragma:no-cache\n" +
                "Referer:http://hrint.sh.ctriptravel.com/hr.ehrms.site/WebParts/LoginMiddle.aspx\n" +
                "User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36";

        HttpRequestUtil.addHeaders(req, headerStr);
        cookie = cookie.trim();
        if (cookie != null && cookie.length() > 0) {
            req.setHeader("Cookie", cookie);
        }

        String formStr = "__LASTFOCUS:\n" +
                "__VIEWSTATE:/wEPDwULLTEzMjMwNjcyNTdkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQtpbWdidG5Mb2dpbhPd5r8gIu3q6jJDorNQyFXGajF0Vrb6H+Pe3OpooMPx\n" +
                "__EVENTTARGET:\n" +
                "__EVENTARGUMENT:\n" +
                "txtEid:mzang\n" +
                "imgbtnLogin.x:15\n" +
                "imgbtnLogin.y:23\n" +
                "txtPWD:a6Sdfghjkl;\n" +
                "ddlDomain:cn1";

        HttpRequestUtil.addFormData(req, formStr);


        HttpResponse response = httpclient.execute(req);


        try {
            String content = getAllContent(response.getEntity().getContent());
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        Header[] headers = response.getHeaders("Set-Cookie");
        String cookieRet = HttpRequestUtil.handleSetCookieHeaders(headers);
        return cookieRet;
    }

    private static String kaoqinLoginPage() throws IOException {

        HttpClient httpclient = new DefaultHttpClient();

        HttpGet req = new HttpGet("http://hrint.sh.ctriptravel.com/hr.ehrms.site/Login.aspx");

        String headerStr = "Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
                "Accept-Encoding:gzip,deflate,sdch\n" +
                "Accept-Language:zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4\n" +
                "Cache-Control:no-cache\n" +
                "Connection:keep-alive\n" +
                "Host:hrint.sh.ctriptravel.com\n" +
                "Pragma:no-cache\n" +
                "User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36";
        HttpRequestUtil.addHeaders(req, headerStr);

        HttpResponse response = httpclient.execute(req);

        Header[] headers = response.getHeaders("Set-Cookie");
        String cookieRet = HttpRequestUtil.handleSetCookieHeaders(headers);

        return cookieRet;
    }


    public static String getAllContent(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
        StringBuilder content = new StringBuilder();

        String line = null;
        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append("\r\n");
        }

        return content.toString();
    }

}
