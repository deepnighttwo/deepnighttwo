package topasin.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Util for top asin
 * 
 * @author mengzang
 * 
 */
public final class TopAsinUtil {

    public static final String LB = System.getProperty("line.separator");

    // Error code
    public static final String CONTEXT_VERIFY_FAILLED_ERR = "Context verification failure error";
    public static final String RESOURCE_NOT_FOUNT_ERR = "Resource Not Found error";
    public static final String OUTPUT_FILE_NOT_CREATEABLE_ERR = "Output file not readable error";
    public static final String NO_ASIN_DESCRIBER_ERR = "No asin describer found error";
    public static final String NO_SUCH_COMPARATOR_ERR = "No such comparator error";
    public static final String PROCESS_RESOUCE_ERR = "Error occured while processing file";
    public static final String SCRIPT_EVAL_ERROR = "Error occured while evalue script";
    public static final String SCRIPT_RETRIEVE_ERROR = "Error occured when retrieving script";
    public static final String ASINFIELDS_NOT_FOUND = "No AsinFields found for input";

    private static final String TOP_ASIN_GROUP_SEPERATOR = "-";

    public static int getInt(Object val, int defVal) {
        if (val == null) {
            return defVal;
        }
        if (val instanceof Integer) {
            return (Integer) val;
        }
        try {
            return Integer.parseInt(val.toString());
        } catch (Exception ex) {
            return defVal;
        }
    }

    public static <K, V> Collection<Entry<String, Map<K, V>>> sortMapEntryByGroupKey(
            Set<Entry<String, Map<K, V>>> entries) {
        List<Entry<String, Map<K, V>>> sortedEntries = new ArrayList<Entry<String, Map<K, V>>>(entries.size());
        sortedEntries.addAll(entries);
        GroupKeyComparator<K, V> comparator = new GroupKeyComparator<K, V>();
        Collections.sort(sortedEntries, comparator);
        return sortedEntries;
    }

    public static class GroupKeyComparator<K, V> implements Comparator<Entry<String, Map<K, V>>> {

        @Override
        public int compare(Entry<String, Map<K, V>> o1, Entry<String, Map<K, V>> o2) {
            if (o1 == null && o2 == null) {
                return 0;
            }
            if (o1 == null) {
                return -1;
            }
            if (o2 == null) {
                return 1;
            }
            String[] groupKeyElements1 = o1.getKey().split(TOP_ASIN_GROUP_SEPERATOR);
            String[] groupKeyElements2 = o2.getKey().split(TOP_ASIN_GROUP_SEPERATOR);
            for (int i = 0; i < groupKeyElements1.length; i++) {
                String e1 = groupKeyElements1[i];
                String e2 = groupKeyElements2[i];
                int ret = e1.compareTo(e2);
                if (ret == 0) {
                    continue;
                } else {
                    try {
                        int i1 = Integer.parseInt(e1);
                        int i2 = Integer.parseInt(e2);
                        return i1 - i2;
                    } catch (Exception ex) {
                        return e1.compareTo(e2);
                    }
                }
            }
            return 0;
        }
    }

    public static int getRealIntFromAsinDetailColunmString(String str) {
        return Math.round(Float.parseFloat(str));
    }

    public static int doubleCompare(double d1, double d2) {
        double diffVal = d1 - d2;
        if (diffVal < 0.0001 && diffVal > -0.0001) {
            return 0;
        } else {
            return diffVal > 0 ? 1 : -1;
        }
    }

    public static void executeMulthThreadAndWait4Termination(final String taskName, final Runnable[] runnables,
            final long interval, final Number counter) throws InterruptedException {
        log("Starting " + taskName + " with " + runnables.length + " threads...");
        long start = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(runnables.length);
        for (Runnable runnable : runnables) {
            service.execute(runnable);
        }
        service.shutdown();
        if (counter != null) {
            int preProcessedLineNumber = 0;
            while (service.awaitTermination(interval, TimeUnit.SECONDS) == false) {
                int totalProcessedLineNumber = counter.intValue();
                throughputLog(interval, start, taskName, preProcessedLineNumber, totalProcessedLineNumber);
                preProcessedLineNumber = totalProcessedLineNumber;
            }
        } else {
            while (service.awaitTermination(interval, TimeUnit.SECONDS) == false) {
                log("Time consumed " + ((System.currentTimeMillis() - start) / 1000) + "s. Processing...");
            }
        }
        log("=====================================================================================================================================");
        StringBuilder endMessage = new StringBuilder(taskName + " finished. Time consumed "
                + ((System.currentTimeMillis() - start) / 1000) + "s. ");
        if (counter != null) {
            endMessage.append("Total processed " + counter.intValue() + " lines.");
        }
        log(endMessage.toString());
        log("=====================================================================================================================================");
    }

    private static void throughputLog(final long interval, final long start, final String logTitle,
            final int preNumber, int totalProcessedLineNumber) {
        int processKLineCountInterval = (totalProcessedLineNumber - preNumber) / 1000;
        int totalProcessedKLine = totalProcessedLineNumber / 1000;
        int timeUsed = (int) ((System.currentTimeMillis() - start) / 1000);
        log(logTitle + " processed " + processKLineCountInterval + "k lines in " + interval
                + " seconds.Total Processed " + totalProcessedKLine + "k lines in " + timeUsed + " seconds");
    }

    public static void asyncExecuteMulthThreadAndWait4Termination(final String taskName, final Runnable[] runnables,
            final long interval, final Number counter, final Runnable callback) {
        Thread taskThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    executeMulthThreadAndWait4Termination(taskName, runnables, interval, counter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (callback != null) {
                        callback.run();
                    }
                }
            }

        });
        taskThread.start();
        return;
    }

    public static String genGroupKeyFromStringArray(String[] groupElements) {
        if (groupElements == null) {
            return "";
        }
        StringBuilder groupKeyBuilder = new StringBuilder();
        for (String groupElement : groupElements) {
            groupKeyBuilder.append(groupElement);
            groupKeyBuilder.append(TOP_ASIN_GROUP_SEPERATOR);
        }
        groupKeyBuilder.deleteCharAt(groupKeyBuilder.length() - 1);
        return groupKeyBuilder.toString();
    }

    @SuppressWarnings("unchecked")
    public static String describeMap(Map<? extends Object, ? extends Object> map, String indent) {
        StringBuilder des = new StringBuilder();
        des.append(LB);
        des.append(indent);
        des.append("[");
        for (Entry<? extends Object, ? extends Object> entry : map.entrySet()) {
            des.append(LB);
            des.append(indent);
            des.append(entry.getKey());
            des.append(" --> ");
            Object value = entry.getValue();
            if (value instanceof Object[]) {
                des.append(ArrayUtils.toString((Object[]) value));
            } else if (value instanceof Map) {
                // this would bring a dead loop if map value has the same map instance...
                des.append(describeMap((Map<? extends Object, ? extends Object>) value, indent + "    "));
            } else {
                des.append(value);
            }
        }
        des.append(LB);
        des.append(indent);
        des.append("]");

        return des.toString();
    }

    public static int moneyCompare(double m1, double m2) {
        double diff = m1 - m2;
        if (diff < 0.001 && diff > -0.001) {
            return 0;
        }
        return diff > 0 ? 1 : -1;
    }

    public static void appendLine(StringBuilder content, Object line) {
        if (line != null) {
            content.append(line);
        }
        content.append(LB);
    }

    public static void log(Object obj) {
        System.out.println(obj);
    }

}
