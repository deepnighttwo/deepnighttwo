package suntest;
/**
 * This source file is part of project littleaccountbook.
 * This project is under GNU General Public License v2.
 * This Project is a C/S account book.
 * Swing is used to create the GUI of this project and Java DB is used to store data in local machine.
 * Full source code of this project is available at http://littleaccountbook.googlecode.com/svn/trunk/ littleaccountbook-read-only
 * 
 * @author  Moon Zang
 * 
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Searching {
    public static void main(String[] args) {
        String[] strings = { "0", "1", "2", "3", "4", "5" };

        List<Integer> integers = new ArrayList<Integer>();
        for (String s : strings) {
            integers.add(Integer.valueOf(s));
        }

        System.out.println(Collections.binarySearch(integers, 1, cmp));
    }

    static Comparator<Integer> cmp = new Comparator<Integer>() {
        public int compare(Integer i, Integer j) {
            System.out.println(i.hashCode() + "			" + j.hashCode());
            return i < j ? -1 : (i == j ? 0 : 1);
        }
    };
}