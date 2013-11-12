package com.ctrip.clog.util;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: mzang
 * Date: 11/11/13
 * Time: 7:34 PM
 */
public class RegxTest {

    @Test
    public void testReg() {
        Pattern pattern = Pattern.compile("(?s)^.{10}\\QA+B=C\\E\\QGVA\\E(?:\\QAAA\\E|\\QBBB\\E)(?:.{4})*\\QKy\\E\\QVa\\E(?:.{4})*$");
        Matcher matcher = pattern.matcher("0123456789A+B=CGVABBBAbCdZZZZKyVa12341234");

        System.out.println(matcher.find());

        System.out.println("Found the text " + matcher.group() + " starting at " + matcher.start() + " index and ending at index " + matcher.end());
    }
}
