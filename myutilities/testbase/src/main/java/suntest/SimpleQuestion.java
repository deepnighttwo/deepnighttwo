package suntest;
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

public class SimpleQuestion {
    static boolean yesOrNo(String s) {
        s = s.toLowerCase();
        if (s.equals("yes") || s.equals("y") || s.equals("t")) {
            s = "true";
        }
        return Boolean.getBoolean(s);
    }

    public static void main(String[] args) {
        System.out.println(yesOrNo("true") + " " + yesOrNo("Yes"));
    }
}
