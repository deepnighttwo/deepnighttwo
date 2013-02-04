package javamap;
import java.util.HashMap;
import java.util.Map;

public class MapMemoTest {

    public static void main(String[] args) {
        long s = System.currentTimeMillis();
        Map<String, FieldTest> m = new HashMap<String, FieldTest>(9000000, 1.0f);
        while (m.size() < 8000000) {
            FieldTest t = FieldTest.getInstance();
            m.put(t.asin, t);
        }
        System.out.println("finished in " + (System.currentTimeMillis() - s) / 1000);
        System.gc();
        try {
            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(m.size());
    }
}

class FieldTest {
    String asin;
    double v1;
    double v2;
    double v3;

    private static int i = 1000000000;

    public static FieldTest getInstance() {
        FieldTest f = new FieldTest();
        f.asin = String.valueOf(i);
        i++;
        return f;
    }
}
