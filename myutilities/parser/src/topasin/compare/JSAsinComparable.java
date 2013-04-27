package topasin.compare;

import static topasin.util.TopAsinUtil.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import topasin.util.AsinDetail;
import topasin.util.TopAsinUtil;

/**
 * This is for more flexible usage. javascript can be used for asin compare. this comparator has the same function with
 * StandardOIHComparable.
 * 
 * @author mengzang
 * 
 */
public final class JSAsinComparable implements AsinComparable {

    private static final ScriptEngine jsEngine = (new ScriptEngineManager()).getEngineByName("javascript");

    private static int[] KEY_FIELDS_INDEX;

    private static String globalInitScript;
    private static String instanceInitScript;
    private static final String fillCompareFieldsScript = "fillCompareFields(p_values, p_source, cmpVals);";
    private static final String preCheckForTopAsinScript = "preCheckForTopAsin(cmpVals);";
    private static final String compareForTopAsinScript = "compareForTopAsin(p_bindings, cmpVals);";
    private static final String compareForOutputSortScript = "compareForOutputSort(p_bindings, cmpVals);";
    private static final String getCompareDiffDisplayValueScript = "getCompareDiffDisplayValue(cmpVals);";
    private static final String getCompareOrigDisplayValueScript = "getCompareOrigDisplayValue(cmpVals);";
    private static final String getCompareNewDisplayValueScript = "getCompareNewDisplayValue(cmpVals);";

    private final Bindings bindings;

    private static final Bindings globalBindings = jsEngine.createBindings();

    private static final TopAsinUtil topAsinUtil4JS = new TopAsinUtil();

    private static void logHelp() {
        log("=====================================JSAsinComparable Help=====================================");
        log(" - JSAsinComparable retrieve settings from system envs: jcFields, jcGlobalInitScript, jcInstanceInitScript.");
        log(" - System env jcFields's value is the asin detail fields name that used in script.It will be retrieved and stored in java string array. These value can be used in script for compare calculation.");
        log(" - System env jcGlobalInitScript's value is the global scope java script. It should contains script functions, global variables but no instance related variables."
                + " RI is provided as top-asin-java-script-comparator-globalinit-example.js");
        log(" - System env jcInstanceInitScript's value is the instance script. it should provide the following functions as of the script version of AsinComparable implementation.");
        log("\t" + fillCompareFieldsScript);
        log("\t" + preCheckForTopAsinScript);
        log("\t" + compareForTopAsinScript);
        log("\t" + getCompareDiffDisplayValueScript);
        log("\t" + getCompareOrigDisplayValueScript);
        log("\t" + getCompareNewDisplayValueScript);
        log(" RI is provided as top-asin-java-script-comparator-instance-example.js.");
        log(" - For both jcGlobalInitScript and jcInstanceInitScript, if the value starts with " + File.separator
                + ", then it will be considerred as file path. The value would be the file content of the file path.");
        log(" - Notice that if declare too many variables in js, it would bring OOM exception.");
        log("===============================================================================================");

    }

    public static void initJSAsinComparable() {
        logHelp();
        String jcFieldsEnv = System.getenv("jcFields");
        globalInitScript = getScriptStringFromEvn(System.getenv("jcGlobalInitScript"));
        instanceInitScript = getScriptStringFromEvn(System.getenv("jcInstanceInitScript"));
        log("============================Javascript Comparator config============================");
        log("Javascript Comparator Fields=" + jcFieldsEnv);
        log("Javascript Comparator Global Init Script=" + globalInitScript);
        log("Javascript Comparator Instance Init Script=" + instanceInitScript);
        log("====================================================================================");

        String[] jcFields = jcFieldsEnv.split(",");
        AsinDetail.addNeededFields(jcFields);
        KEY_FIELDS_INDEX = new int[jcFields.length];
        for (int i = 0; i < jcFields.length; i++) {
            KEY_FIELDS_INDEX[i] = AsinDetail.getIndexFromFieldName(jcFields[i]);
        }

        globalBindings.put("topAsinUtil4JS", topAsinUtil4JS);
        // globalBindings.put("config", ConfigFactory.getDefaultConfig());
        try {
            jsEngine.eval(globalInitScript, globalBindings);
            jsEngine.setBindings(globalBindings, ScriptContext.GLOBAL_SCOPE);
        } catch (ScriptException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(TopAsinUtil.SCRIPT_EVAL_ERROR, e);
        }
    }

    private static String getScriptStringFromEvn(String envVal) {
        log("Script env value=" + envVal);
        if (envVal.charAt(0) == File.separatorChar) {
            log("Retrive script from file " + envVal);
            StringBuilder script = new StringBuilder();
            try {
                BufferedReader scriptReader = new BufferedReader(new FileReader(envVal));
                String line = null;
                while ((line = scriptReader.readLine()) != null) {
                    script.append(line + TopAsinUtil.LB);
                }
            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException(TopAsinUtil.SCRIPT_RETRIEVE_ERROR, e);
            } catch (IOException e) {
                throw new IllegalArgumentException(TopAsinUtil.SCRIPT_RETRIEVE_ERROR, e);
            }
            return script.toString();
        } else {
            return envVal;
        }
    }

    public JSAsinComparable() {
        bindings = jsEngine.createBindings();
        try {
            jsEngine.eval(instanceInitScript, bindings);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public void fillCompareFields(AsinDetail asinDetail, int source) {
        String[] value2Fill = new String[KEY_FIELDS_INDEX.length];
        for (int i = 0; i < KEY_FIELDS_INDEX.length; i++) {
            value2Fill[i] = asinDetail.getStringFieldValue(KEY_FIELDS_INDEX[i]);
        }
        bindings.put("p_values", value2Fill);
        bindings.put("p_source", source);
        try {
            jsEngine.eval(fillCompareFieldsScript, bindings);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        bindings.remove("p_values");
        bindings.remove("p_source");
    }

    @Override
    public boolean isValidAsinForTopAsin() {
        try {
            return (Boolean) jsEngine.eval(preCheckForTopAsinScript, bindings);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int compareForTopAsin(AsinComparable asin) {
        return getIntRetFromJSDouble(asin, compareForTopAsinScript);
    }

    @Override
    public int compareForOutputSort(AsinComparable asin) {
        return getIntRetFromJSDouble(asin, compareForOutputSortScript);
    }

    /**
     * don't know y, when an int returned from js, it is converted to Double type...
     */
    private int getIntRetFromJSDouble(AsinComparable asin, String script) {
        int ret = 0;
        try {
            bindings.put("p_bindings", ((JSAsinComparable) asin).bindings);
            ret = TopAsinUtil.doubleCompare((Double) jsEngine.eval(script, bindings), 0);
            bindings.remove("p_bindings");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public String getCompareDiffDisplayValue() {
        String ret = null;
        try {
            ret = String.valueOf(jsEngine.eval(getCompareDiffDisplayValueScript, bindings));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public String getCompareOrigDisplayValue() {
        String ret = null;
        try {
            ret = String.valueOf(jsEngine.eval(getCompareOrigDisplayValueScript, bindings));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public String getCompareNewDisplayValue() {
        String ret = null;
        try {
            ret = String.valueOf(jsEngine.eval(getCompareNewDisplayValueScript, bindings));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
