package parsewsdl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TestXSD {

    public static void log(String str) {
        System.out.println(str);
    }

    public static void main(String[] args) throws IOException,
            TemplateException {

        String test = "\r\n asdfasdf asdfasdf\r\n";
        test = test.trim();
        System.out.println(test.trim());

        File f = new File(
                "C:\\mymise\\myprojects\\myutilities\\WSDL4JTest\\src\\parsewsdl\\asdf.xsd");
        XSDTypeParser parser = new XSDTypeParser(new FileInputStream(f));
        List<CommonTypeProp> types = parser.getAllTypes();

        for (CommonTypeProp type : types) {
            log("=======================");
            log(type.getTypeName());
            log(type.getSchemaTemplate());
            log("+++++++++++++++++++++++");
            Writer writer = new StringWriter();
            Map<String, String> mapping = new HashMap<String, String>();
            mapping.put(XSDTypeParser.namespaceMarker, type.getNamespace());
            mapping.put(XSDTypeParser.typeNameMarker, type.getTypeName());
            mapping.put(XSDTypeParser.documentMarker, type.getDescription());
            Template temp = getTemplate(type.getSchemaTemplate(), type
                    .getTypeName());
            temp.process(mapping, writer);
            System.out.println(writer.toString());
        }
    }

    public static Template getTemplate(String template, String templateName)
            throws IOException {
        Configuration cfg = new Configuration();
        StringTemplateLoader tloader = new StringTemplateLoader();
        cfg.setTemplateLoader(tloader);
        tloader.putTemplate(templateName, template);
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        Template temp = cfg.getTemplate(templateName);
        return temp;
    }
}
