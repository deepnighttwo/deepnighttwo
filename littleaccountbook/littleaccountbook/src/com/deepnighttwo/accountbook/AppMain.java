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

package com.deepnighttwo.accountbook;

public class AppMain {
    public static void main(String[] args) {
        AccountBook.getAccountBook().getMainFrame().setVisible(true);
    }
}
