package suntest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This source file is part of project littleaccountbook. This project is under
 * GNU General Public License v2. This Project is a C/S account book. Swing is
 * used to create the GUI of this project and Java DB is used to store data in
 * local machine. Full source code of this project is available at
 * http://littleaccountbook.googlecode.com/svn/trunk/
 * littleaccountbook-read-only
 * 
 * @author Moon Zang
 * 
 */

public enum RomanNumeral {
    // private static Map<Integer, RomanNumeral> masdfap = new
    // LinkedHashMap<Integer, RomanNumeral>();
    I(1), V(5), X(10), L(50), C(100), D(500), M(1000);
    private static Map<Integer, RomanNumeral> map = new LinkedHashMap<Integer, RomanNumeral>();

    public final int val;

    RomanNumeral(int val) {
        this.val = val;
        storeInMap();
    }

    private void storeInMap() {
        map.put(val, this);
    }

    public static RomanNumeral fromInt(int val) {
        return map.get(val);
    }

    public static void main(String[] args) {
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            if (fromInt(i) != null) {
                sum += i;
            }
        }
        System.out.println(sum);
    }
}
