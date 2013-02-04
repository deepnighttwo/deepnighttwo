package picturefarm;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import picturefarm.image.util.FormDataExtractor;
import picturefarm.image.util.FormImage;

@SuppressWarnings("serial")
public class PicturefarmServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Hello, world");
        FormDataExtractor extractor = new FormDataExtractor(req);
        extractor.extractFormData();

        // List<String[]> attributes = extractor.getFormAttributes();
        // for (String[] str : attributes) {
        // System.out.print(str[0] + "\t");
        // System.out.println(str[1]);
        // }

        List<FormImage> images = extractor.getFormImageData();
        for (FormImage image : images) {
            System.out.print(image.getAttributeName() + "\t");
            System.out.print(image.getFileName() + "\t");
            System.out.println(image.getEnd() - image.getStart());
        }

    }
}
