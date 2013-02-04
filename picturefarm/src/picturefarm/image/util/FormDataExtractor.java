/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Moon Zang
 * 
 */
package picturefarm.image.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

public class FormDataExtractor {

    private static byte[] NEW_LINE = new byte[] { '\r', '\n' };
    private static char FORM_END = '-';
    private static final String CONTENT_TYPE_SEP = "Content-Type: ";

    private String boundary = null;
    private byte[] data;
    private int current;

    private Map<String, String> formAttributes = new HashMap<String, String>();

    public Map<String, String> getFormAttributes() {
        return formAttributes;
    }

    public List<FormImage> getFormImageData() {
        return formImageData;
    }

    public void setFormImageData(List<FormImage> formImageData) {
        this.formImageData = formImageData;
    }

    private List<FormImage> formImageData = new ArrayList<FormImage>();

    public FormDataExtractor(HttpServletRequest req) throws IOException {
        int len = req.getContentLength();
        data = new byte[len];
        ServletInputStream sis = req.getInputStream();
        loadAllData(sis, data);
        current = 0;
    }

    private void loadAllData(ServletInputStream sis, byte[] data)
            throws IOException {
        int loaded = 0;
        int all = data.length;
        while (loaded != all) {
            int read = sis.read(data, loaded, all - loaded);
            loaded += read;
        }
    }

    public void extractFormData() {
        boundary = readLine();
        while (true) {
            String line1 = readLine();
            String line2 = readLine();
            if (line2.length() == 0) {
                addFormAttributeData(line1);
            } else {
                formImageData.add(getFormImageData(line1, line2));
            }
            if (!filterBoundary()) {
                return;
            }
        }
    }

    private void addFormAttributeData(String contentLine) {
        char separator = '\"';
        int s1 = contentLine.indexOf(separator, 0);
        int s2 = contentLine.indexOf(separator, s1 + 1);
        String name = contentLine.substring(s1 + 1, s2);
        formAttributes.put(name, readLine());
    }

    private FormImage getFormImageData(String contentLine,
            String contentTypeLine) {
        readLine();
        char separator = '\"';
        int s1 = contentLine.indexOf(separator, 0);
        int s2 = contentLine.indexOf(separator, s1 + 1);
        int s3 = contentLine.indexOf(separator, s2 + 1);
        int s4 = contentLine.indexOf(separator, s3 + 1);

        String name = contentLine.substring(s1 + 1, s2);
        String fileName = contentLine.substring(s3 + 1, s4);

        byte[] bouChas = ("\r\n" + boundary).getBytes();

        int start = current;

        boolean matched = false;
        while (!matched) {
            for (int i = 0; i < bouChas.length; i++) {
                if (data[current + i] != bouChas[i]) {
                    break;
                }
                if (i == bouChas.length - 1) {
                    matched = true;
                    break;
                }
            }
            current++;
        }
        int end = current - 1;
        // skip the \r\n, keep the same status with read line.
        current++;

        String contentType = contentTypeLine.substring(CONTENT_TYPE_SEP
                .length());

        FormImage image = new FormImage();
        image.setAttributeName(name);
        image.setFileName(fileName);
        image.setContentType(contentType);
        image.setStart(start);
        image.setEnd(end);
        image.setData(data);
        return image;
    }

    private String readLine() {
        StringBuffer sb = new StringBuffer();
        char ch1 = (char) data[current];
        current++;
        char ch2 = (char) data[current];
        current++;
        while (true) {
            if (ch1 == NEW_LINE[0] && ch2 == NEW_LINE[1]) {
                break;
            }
            sb.append(ch1);
            ch1 = ch2;
            ch2 = (char) data[current];
            current++;
        }
        return sb.toString();
    }

    private boolean filterBoundary() {
        current += boundary.length();
        if (data[current] == FORM_END && data[current + 1] == FORM_END) {
            // reach the end of form data
            return false;
        } else {
            // filter \r\n
            current += 2;
            return true;
        }
    }
}
