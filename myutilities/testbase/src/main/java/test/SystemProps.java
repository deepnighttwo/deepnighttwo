package test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

public class SystemProps {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Properties props = System.getProperties();
        Collection<?> values = props.keySet();
        Iterator<?> it = values.iterator();
        while (it.hasNext()) {
            Object key = it.next();
            System.out.println(key);
            Object value = props.getProperty((String) key);
            System.out.println("\t" + value);
        }
        byte value = Short.valueOf("192").byteValue();
        System.out.println("\t" + value);
        File file = new File((String) props.get("java.io.tmpdir"), "mytesttemp");
        if (file.exists()) {
            file.delete();
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
