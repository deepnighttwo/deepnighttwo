package com.amazon.mzang.tools.old;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.amazon.mzang.tools.FileUtil;

public class GLUtils {

    public static Map<String, String[]> getAllGLKeyGL() throws IOException {
        Map<String, String[]> gls = new LinkedHashMap<String, String[]>();
        List<String[]> rows = FileUtil.readFileAsTable("gl.txt", "\t");
        for (String[] row : rows) {
            String gl = row[0].trim();
            gls.put(gl, row);
        }
        return gls;
    }

    public static Map<String, String[]> getAllGLKeyGLName() throws IOException {
        Map<String, String[]> gls = new LinkedHashMap<String, String[]>();
        List<String[]> rows = FileUtil.readFileAsTable("gl.txt", "\t");
        for (String[] row : rows) {
            String glName = row[1].trim();
            gls.put(glName, row);
        }
        return gls;
    }

}
