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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class AccountBook {
    private static AccountBook accountBook;
    private JTabbedPane m_mainPane;
    private JFrame m_mainFrame;

    public JFrame getMainFrame() {
        return m_mainFrame;
    }

    public synchronized static AccountBook getAccountBook() {
        if (accountBook == null) {
            accountBook = new AccountBook();
        }
        return accountBook;
    }

    private AccountBook() {
        m_mainPane = new JTabbedPane();
        m_mainFrame = new JFrame();
        init();
    }

    private void init() {

        buidWindow();

        buildTabs();

    }

    private void buildTabs() {
        m_mainPane.setTabPlacement(JTabbedPane.LEFT);
        m_mainPane.addTab("Cost", createCostTab());

        m_mainFrame.getContentPane().add(m_mainPane);
    }

    private void buidWindow() {
        m_mainFrame.setSize(new Dimension(500, 500));
        m_mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        placeWindowCenter(m_mainFrame);
    }

    private Component createCostTab() {
        JPanel pane = new JPanel();
        return pane;
    }

    public static void placeWindowCenter(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(new Point(x, y));
    }
}
