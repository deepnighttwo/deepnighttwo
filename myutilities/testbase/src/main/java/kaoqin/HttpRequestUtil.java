package kaoqin;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: mzang
 * Date: 2014-11-10
 * Time: 16:26
 */
public class HttpRequestUtil {

    public static void main(String[] args) {
    }

    public static void addHeaders(HttpRequestBase req, String headerStr) {
        String[] headers = headerStr.split("\\n");

        for (String header : headers) {
            int index = header.indexOf(':');
            String key = header.substring(0, index).trim();
            if ("Content-Length".equals(key)) {
                continue;
            }
            String value = header.substring(index + 1).trim();
            req.setHeader(key, value);
        }
    }

    public static void addFormData(HttpEntityEnclosingRequestBase req, String formStr) throws UnsupportedEncodingException {
        String[] formDatas = formStr.split("\\n");

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        for (String formData : formDatas) {
            int index = formData.indexOf(':');
            String key = formData.substring(0, index).trim();
            String value = formData.substring(index + 1).trim();
            params.add(new BasicNameValuePair(key, value));
        }

        req.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
    }

    public static String handleSetCookie(String cookie) {

        String[] cookieEntities = cookie.split(";");

        StringBuilder cookieRet = new StringBuilder();
        for (String ck : cookieEntities) {
            ck = ck.trim();
            if (ck.equalsIgnoreCase("httponly")) {
                continue;
            }
            if (ck.startsWith("path=")) {
                continue;
            }
            if (ck.startsWith("Path=")) {
                continue;
            }
            if (ck.startsWith("expires=")) {
                continue;
            }
            cookieRet.append(ck);
            cookieRet.append("; ");
        }

        cookieRet.delete(cookieRet.length() - 2, cookieRet.length());

        return cookieRet.toString();
    }

    public static String handleSetCookieHeaders(Header[] cookieHeaders) {

        if (cookieHeaders.length == 0) {
            return "";
        }

        StringBuilder cookieRet = new StringBuilder();
        for (Header header : cookieHeaders) {
            cookieRet.append(header.getValue());
            cookieRet.append(";");
        }

        cookieRet.delete(cookieRet.length() - 1, cookieRet.length());

        return handleSetCookie(cookieRet.toString());
    }

}
