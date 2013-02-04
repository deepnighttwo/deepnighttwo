package parsewsdl;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;

public class FreeMarkerTest {
    public static void main(String[] args) throws IOException,
            TemplateException {
        Configuration cfg = new Configuration();
        String templateName = "test";
        StringTemplateLoader tloader = new StringTemplateLoader();
        cfg.setTemplateLoader(tloader);
        tloader.putTemplate(templateName,
                "asdfasdfasdf\"${AAA}\"sadfasdfasdfasdf");

        cfg.setObjectWrapper(new DefaultObjectWrapper());

        freemarker.template.Template temp = cfg.getTemplate(templateName);
        Writer writer = new StringWriter();
        Map mapping = new HashMap();
        mapping.put("AAA", "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
        temp.process(mapping, writer);
        System.out.println(writer.toString());
    }
}
