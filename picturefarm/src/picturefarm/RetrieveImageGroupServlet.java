/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Moon Zang
 * 
 */
package picturefarm;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import picturefarm.image.jdo.ImageManager;

@SuppressWarnings("serial")
public class RetrieveImageGroupServlet extends HttpServlet {

    public static final char PATH_SEP = '/';
    public static final char FILE_SEP = '.';

    public void service(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String imageURI = req.getRequestURI();
        int start = imageURI.lastIndexOf(PATH_SEP);
        String groupName = imageURI.substring(start);
        groupName = groupName.substring(1, groupName.lastIndexOf(FILE_SEP));
        StringBuffer contentType = new StringBuffer();
        byte[][] data = ImageManager.getImage(groupName, contentType);
        resp.setContentType(contentType.toString());
        ServletOutputStream outStream = resp.getOutputStream();
        for (int i = 0; i < data.length; i++) {
            outStream.write(data[i]);
        }
        outStream.flush();
    }
}
