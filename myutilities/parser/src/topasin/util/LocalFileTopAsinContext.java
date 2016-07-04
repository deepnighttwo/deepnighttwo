package topasin.util;

import static topasin.util.TopAsinUtil.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.StringUtils;

import topasin.filter.AsinDetailFilterFactory;

/**
 * top asin context impled by local file.
 * 
 * @author mengzang
 * 
 */
public class LocalFileTopAsinContext implements TopAsinContext {

    private String outputFile, errorFile, origFile, newFile;

    private int origRawSkipLine, newRawSkipLine;

    private Map<String, Object> rtOptions = new LinkedHashMap<String, Object>();

    public LocalFileTopAsinContext(String output, String error, String orig, int origRawSkipLine, String sbs,
            int sbsRawSkipLine) {
        this.outputFile = output;
        this.errorFile = error;
        this.origFile = orig;
        this.origRawSkipLine = origRawSkipLine;
        this.newFile = sbs;
        this.newRawSkipLine = sbsRawSkipLine;
    }

    @Override
    public void addRTOption(String optionKey, Object value) {
        rtOptions.put(optionKey, value);
    }

    @Override
    public PrintWriter getOutput() throws FileNotFoundException {
        return getPrintWriter(outputFile, System.out);
    }

    @Override
    public PrintWriter getErrorOutput() throws FileNotFoundException {
        return getPrintWriter(errorFile, System.err);
    }

    @Override
    public BufferedReader getOriginalFileReader() throws IOException {
        return getBufferedReader(origFile, origRawSkipLine);
    }

    @Override
    public BufferedReader getNewFileReader() throws IOException {
        return getBufferedReader(newFile, newRawSkipLine);
    }

    private static PrintWriter getPrintWriter(String filepath, OutputStream defaultStrem) throws FileNotFoundException {
        if (filepath == null || filepath.isEmpty()) {
            if (defaultStrem != null) {
                return new PrintWriter(new OutputStreamWriter(defaultStrem));
            }
            throw new IllegalArgumentException(TopAsinUtil.OUTPUT_FILE_NOT_CREATEABLE_ERR);
        }
        return new PrintWriter(filepath);
    }

    private static BufferedReader getBufferedReader(String filepath, int skipLine) {
        if (filepath == null) {
            throw new IllegalArgumentException(TopAsinUtil.RESOURCE_NOT_FOUNT_ERR);
        }
        BufferedReader reader = null;

        try {
            if (filepath.toLowerCase().endsWith(".gz")) {
                reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(filepath),
                        1024 * 128)));
            } else if (filepath.toLowerCase().endsWith(".zip")) {
                reader = new BufferedReader(new InputStreamReader(new ZipInputStream(new FileInputStream(filepath))));
            } else {
                reader = new BufferedReader(new FileReader(filepath));
            }
            for (int i = 0; i < skipLine; i++) {
                reader.readLine();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(TopAsinUtil.RESOURCE_NOT_FOUNT_ERR, e);
        }
        return reader;
    }

    @Override
    public Map<String, Object> getRTOptions() {
        return rtOptions;
    }

    @Override
    public String toString() {
        String lb = System.getProperty("line.separator");
        return "LocalFileTopAsinAnlysisEnv [" + lb + "outputFile=" + outputFile + lb + "errorFile=" + errorFile + lb
                + "origFile=" + origFile + lb + "newFile=" + newFile + lb + "origRawSkipLine=" + origRawSkipLine + lb
                + "newRawSkipLine=" + newRawSkipLine + lb + "filter=" + AsinDetailFilterFactory.getFilter() + lb
                + "rtOptions=" + TopAsinUtil.describeMap(rtOptions, "") + lb + "]";
    }

    @Override
    public String getOutputResultLocationDescription() {
        return "Result is outputed to file " + outputFile;
    }

    @Override
    public boolean verifyParameters() {
        boolean ret = true;

        log("verifying key parameters...");

        File origDataFile = new File(origFile);
        if (origDataFile.isFile()) {
            log("Original asin details file exists.");
        } else {
            log("Original asin details file NOT exists:" + origFile);
            ret = false;
        }
        File newDataFile = new File(newFile);
        if (newDataFile.isFile()) {
            log("New asin details file exists.");
        } else {
            log("New asin details file NOT exists:" + newFile);
            ret = false;
        }

        if (StringUtils.isNotBlank(outputFile) == true) {
            File output = new File(outputFile);
            try {
                if (output.createNewFile() || output.isFile()) {
                    log("Output file is creatable.");
                } else {
                    log("Output file is NOT creatable:" + outputFile);
                    ret = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                log("Got exception when try to create output file:" + e.getMessage());
                ret = false;
            }
        }

        return ret;
    }

}
