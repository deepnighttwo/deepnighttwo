package topasin.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * top asin context
 * 
 * @author mengzang
 * 
 */
public interface TopAsinContext {

    int ORIG_SOURCE = 0;
    int NEW_SOURCE = 1;

    // RT option key
    // top asin
    String TOP_ASIN_COUNT = "TopAsinCount";

    // comparator
    String COMPARATOR_NAME = "ComparatorName";
    String ASIN_COMPARE_FIELD_NAME = "AsinCompareFieldNames";
    String TOP_ASIN_SELECTION_OPTION = "TopAsinSelectionOption";

    // group
    String ASIN_GROUP_FIELD_NAMES = "AsinGroupFieldNames";
    String GROUP2ASIN_COUNT_BIN_FILE_NAME = "Group2AsinCountBinFile";

    // multi-thread
    String BUILD_TREE_THREAD_NUMBER = "BuildTreeThreadNumber";
    String ANALYSIS_FILE_THREAD_NUMBER = "AnalysisFileThreadNumber";
    String OUTPUT_THREAD_NUMBER = "OutputThreadNumber";

    // describer
    String DESCRIBER_NAMES = "DescriberName";
    String DESCRIBER_KEY_FIELD_NAMES = "DescriberKeyFieldNames";

    // filter
    String FILTER_NAME = "FilterName";
    String FILTER_SETTING = "FilterSetting";

    // log
    String LOG_INTERVAL = "LogInterval";

    PrintWriter getOutput() throws FileNotFoundException;

    PrintWriter getErrorOutput() throws FileNotFoundException;

    BufferedReader getOriginalFileReader() throws IOException;

    BufferedReader getNewFileReader() throws IOException;

    Map<String, Object> getRTOptions();

    String getOutputResultLocationDescription();

    void addRTOption(String optionKey, Object value);

    boolean verifyParameters();

}
