/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Moon Zang
 * 
 */

package demo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Random;

public final class DataGenerator {

    private static final Random r = new Random();

    private static char[] CHARS = new char[] { 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ',', '.', '?', '>', '<', '[',
            ']', '{', '}', '`', '~', '!', '@', '#', '$', '%', '^', '&', '*',
            '(', ')', '-', '_', '+', '=', '/', '\\', '\t', '\r', '\n', '1',
            '2', '3', '4', '5', '6', '7', '8', '9', '0', '\'', '\"' };

    public static Boolean getBooleanValue() {
        return r.nextBoolean();
    }

    public static Byte getByteValue() {
        return (byte) r.nextInt();
    }

    public static Short getShortValue() {
        return (short) r.nextInt();
    }

    public static Integer getIntegerValue() {
        return r.nextInt();
    }

    public static Long getLongValue() {
        return r.nextLong();
    }

    public static Float getFloatValue() {
        return r.nextFloat();
    }

    public static Double getDoubleValue() {
        return r.nextDouble();
    }

    public static Date getDataValue() {
        return new Date(System.currentTimeMillis());
    }

    public static Timestamp getTimestampValue() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String getStringValue(int length) {
        StringBuffer sb = new StringBuffer(length);
        int v = r.nextInt();
        int dalta = (short) r.nextInt();
        int len = CHARS.length;
        for (int i = 0; i < length; i++) {
            v -= dalta;
            if (v < 0) {
                v = Math.abs(r.nextInt());
            }
            sb.append(CHARS[v % len]);
            dalta = v / len;
        }
        return sb.toString();
    }
}
