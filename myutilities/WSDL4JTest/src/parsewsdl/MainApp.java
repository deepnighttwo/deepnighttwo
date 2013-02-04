package parsewsdl;

import java.util.List;

import javax.wsdl.Definition;
import javax.wsdl.Types;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.schema.Schema;
import javax.wsdl.factory.WSDLFactory;

import org.w3c.dom.Element;

public class MainApp {

    /**
     * @param args
     * @throws WSDLException
     */
    public static void main(String[] args) throws WSDLException {
        // TODO Auto-generated method stub
        WSDLFactory wsdlFac = WSDLFactory.newInstance();
        Definition def = wsdlFac
                .newWSDLReader()
                .readWSDL(
                        "C:/mymise/myprojects/myutilities/Test/src/saxparsertest/type.wsdl");
        Types types = def.getTypes();
        List ee = types.getExtensibilityElements();
        for (Object obj : ee) {
            if ((obj instanceof Schema) == false) {
                continue;
            }
            Schema schema = (Schema) obj;
            Element element = schema.getElement();
            System.out.println(element);
        }
        System.out.println(def);
    }
}
