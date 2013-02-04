/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Moon Zang
 * 
 */

package picturefarm;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import picturefarm.image.jdo.ImageManager;
import picturefarm.image.util.FormDataExtractor;
import picturefarm.image.util.FormImage;

@SuppressWarnings("serial")
public class SaveImageGroupServlet extends HttpServlet {

    public static final String IMAGE_GROUP_NAME = "groupName";

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        String serverBase = "http://" + req.getServerName() + ":"
                + req.getServerPort();
        FormDataExtractor extractor = new FormDataExtractor(req);
        extractor.extractFormData();

        Map<String, String> attributes = extractor.getFormAttributes();
        String goupName = attributes.get(IMAGE_GROUP_NAME);
        if (goupName == null || goupName.length() == 0) {
            goupName = String.valueOf(Math.random()).substring(2);
        }

        List<FormImage> images = extractor.getFormImageData();

        ImageManager.storeImageGroup(images, goupName);
        String imageURL = serverBase + "/changingimages/" + goupName + ".jpeg";
        resp.getWriter().println(
                "Image gourp created. You can got your image via <a href=\""
                        + imageURL + "\">" + imageURL + "</a><br><br>");
    }
}
