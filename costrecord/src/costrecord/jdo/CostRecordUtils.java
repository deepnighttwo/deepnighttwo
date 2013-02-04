/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Moon Zang
 * 
 */

package costrecord.jdo;

public class CostRecordUtils {

    public static boolean checkStringValue(String str) {
        return str != null && str.trim().length() > 0;
    }

    public static boolean checkDoubleValue(String value) {
        if (value == null || value.trim().length() == 0) {
            return false;
        }
        try {
            Double.valueOf(value);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static boolean checkLongValue(String value) {
        if (value == null || value.trim().length() == 0) {
            return false;
        }
        try {
            Long.valueOf(value);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

}
