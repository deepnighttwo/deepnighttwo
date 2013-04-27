package topasin.util;

import static topasin.util.TopAsinUtil.log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import topasin.calculate.AsinDetailAnalysisFields;

/**
 * Create asin map. Store asin map history size.
 * 
 * @author mengzang
 * 
 */
@SuppressWarnings("unchecked")
public class TopAsinMapFactory {

    private static Map<String, Integer> group2AsinCountMap = new LinkedHashMap<String, Integer>();

    private static String binFile;

    private static int threadNumber = 3;

    private static final boolean USE_THREAD_SAFE = false;

    public static void init(Map<String, Object> rtOptions) {
        binFile = (String) rtOptions.get(TopAsinContext.GROUP2ASIN_COUNT_BIN_FILE_NAME);
        threadNumber = (Integer) rtOptions.get(TopAsinContext.BUILD_TREE_THREAD_NUMBER);
        loadHistoryGroupSize();
    }

    public static void storeHistoryGroupSize(Map<String, Map<String, AsinDetailAnalysisFields>> group2AsinMap) {
        Collection<Entry<String, Map<String, AsinDetailAnalysisFields>>> sortedEntries = TopAsinUtil
                .sortMapEntryByGroupKey(group2AsinMap.entrySet());
        for (Entry<String, Map<String, AsinDetailAnalysisFields>> entry : sortedEntries) {
            String groupKey = entry.getKey();
            int asinCount = entry.getValue().size();
            log("Group " + groupKey + " has " + asinCount + " asins.");
            group2AsinCountMap.put(groupKey, asinCount);
        }

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(binFile));
            oos.writeObject(group2AsinCountMap);
            oos.close();
        } catch (Throwable ex) {
            log("Store Group 2 Asin Count error: " + ex);
        }
    }

    private static void loadHistoryGroupSize() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(binFile));
            group2AsinCountMap = (Map<String, Integer>) ois.readObject();
            ois.close();
        } catch (Throwable ex) {
            log("No previous Group 2 Asin Count: " + ex);
        }
    }

    public static <K, V> Map<K, V> getAsinMap(String groupKey) {
        if (USE_THREAD_SAFE) {
            return getAsinMapThreadSafe(groupKey);
        } else {
            return getAsinMapThreadUnsafe(groupKey);
        }
    }

    public static <K, V> Map<K, V> getGroupMap() {
        if (USE_THREAD_SAFE) {
            return getGroupMapThreadSafe();
        } else {
            return getGroupMapThreadUnsafe();
        }
    }

    public static <K, V> Map<K, V> getAsinMapThreadSafe(String groupKey) {
        Integer asinCount = group2AsinCountMap.get(groupKey);
        if (asinCount == null) {
            return new ConcurrentHashMap<K, V>(2 << 20, 0.75f, threadNumber);
        }
        return new ConcurrentHashMap<K, V>((int) Math.round(asinCount * 1.25), 1.0f, threadNumber);

    }

    public static <K, V> Map<K, V> getGroupMapThreadSafe() {
        int groupSize = group2AsinCountMap.size();
        if (groupSize == 0) {
            return new ConcurrentHashMap<K, V>(2 << 10, 0.75f, threadNumber);
        }
        return new ConcurrentHashMap<K, V>((int) Math.round(groupSize * 1.25), 1.0f, threadNumber);
    }

    public static <K, V> Map<K, V> getAsinMapThreadUnsafe(String groupKey) {
        Integer asinCount = group2AsinCountMap.get(groupKey);
        if (asinCount == null) {
            return new HashMap<K, V>(2 << 20, 0.75f);
        }
        return new HashMap<K, V>((int) Math.round(asinCount * 1.25), 1.0f);

    }

    public static <K, V> Map<K, V> getGroupMapThreadUnsafe() {
        int groupSize = group2AsinCountMap.size();
        if (groupSize == 0) {
            return new HashMap<K, V>(2 << 10, 0.75f);
        }
        return new HashMap<K, V>((int) Math.round(groupSize * 1.25), 1.0f);
    }

}
