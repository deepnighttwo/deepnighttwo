package testdecimalformat;

import java.text.DecimalFormat;

public class TestDecimalFormat {

    /**
     * @param args
     */
    public static void main(String[] args) {
        DecimalFormat f = new DecimalFormat("#0.000");
        System.out.println(f.format(-999.2));
        System.out.println(f.format(999.2));
        System.out.println(f.format(-999.2));

    }

}
