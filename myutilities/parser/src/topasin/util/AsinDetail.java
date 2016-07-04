package topasin.util;

import static topasin.util.TopAsinUtil.log;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;

/**
 * represent asin detail line
 * 
 * @author mengzang
 * 
 */
public final class AsinDetail {

    public static int getIndexFromFieldName(String fieldName) {
        AsinFields asinFields = AsinFields.getAsinDetailFieldByName(fieldName);
        return asinFields.getIndex();
    }

    public static String getFieldDisplayNameFromIndex(int index) {
        AsinFields asinFields = AsinFields.getAsinDetailFieldByIndex(index);
        return asinFields.getTitle();
    }

    private static int[] GROUP_INDEXS;

    public static final void init(Map<String, Object> rtOptions) {
        String[] groupColumnNames = (String[]) rtOptions.get(TopAsinContext.ASIN_GROUP_FIELD_NAMES);
        AsinDetail.addNeededFields(groupColumnNames);

        if (ArrayUtils.isEmpty(groupColumnNames) == true) {
            log("Warning: No Group keys specified.");
            return;
        }
        GROUP_INDEXS = new int[groupColumnNames.length];
        for (int i = 0; i < groupColumnNames.length; i++) {
            GROUP_INDEXS[i] = AsinDetail.getIndexFromFieldName(groupColumnNames[i]);
        }
    }

    public final String getGroupKey() {
        return TopAsinUtil.genGroupKeyFromStringArray(getStringFieldValues(GROUP_INDEXS));
    }

    private final String[] row;

    public AsinDetail() {
        row = new String[AsinFields.getAsinFieldsCount()];
    }

    // just get field values of specified field index
    private static int[] FIELDS_INDEX = new int[] {
        AsinFields.Asin.getIndex(),
    };

    public static synchronized void addNeededFields(String... fieldNames) {
        if (ArrayUtils.isEmpty(fieldNames)) {
            return;
        }
        Set<Integer> indexs = new TreeSet<Integer>();
        for (Integer i : FIELDS_INDEX) {
            indexs.add(i);
        }
        for (String fieldName : fieldNames) {
            int fieldIndex = getIndexFromFieldName(fieldName);
            indexs.add(fieldIndex);
        }
        Integer[] newIndexs = indexs.toArray(new Integer[0]);

        FIELDS_INDEX = new int[newIndexs.length];

        for (int i = 0; i < newIndexs.length; i++) {
            FIELDS_INDEX[i] = newIndexs[i];
        }
    }

    public void fillAsinDetailsWithNeededFields(final String asinDetailLine) {

        int[] fieldIndexs = FIELDS_INDEX;
        if (fieldIndexs == null || fieldIndexs.length == 0) {
            return;
        }
        int len = asinDetailLine.length();
        int currFieldId = 0;
        int currFiledIdValue = fieldIndexs[currFieldId];
        int prePos = 0;

        int markerCount = -1;
        char marker = ',';
        for (int i = 0; i < len; i++) {
            char curr = asinDetailLine.charAt(i);
            if (curr == marker) {
                markerCount++;
                if (currFiledIdValue == markerCount) {
                    // needed to avoid OOM
                    String value = new String(asinDetailLine.substring(prePos, i).trim());
                    row[currFiledIdValue] = value;
                    currFieldId++;
                    if (currFieldId == fieldIndexs.length) {
                        return;
                    }
                    currFiledIdValue = fieldIndexs[currFieldId];
                }

                prePos = i + 1;
            }
        }
        markerCount++;
        if (currFiledIdValue == markerCount) {
            String value = new String(asinDetailLine.substring(prePos, len));
            row[currFiledIdValue] = value;
            currFieldId++;
        }
        if (currFieldId != fieldIndexs.length) {
            throw new IllegalArgumentException("Asin Detail Line can't fullfil indexs:" + Arrays.toString(FIELDS_INDEX)
                    + ". Asin Detail Line:" + asinDetailLine);
        }
        return;
    }

    public String getAsin() {
        return row[AsinFields.Asin.getIndex()];
    }

    public String[] getStringFieldValues(int[] filedIndexs) {
        if (filedIndexs == null) {
            return null;
        }

        return getStringFieldValues(filedIndexs, new String[filedIndexs.length]);
    }

    public String[] getStringFieldValues(int[] filedIndexs, String[] values) {
        if (filedIndexs == null) {
            return null;
        }
        for (int i = 0; i < filedIndexs.length; i++) {
            values[i] = row[filedIndexs[i]];
        }
        return values;
    }

    public int getIntFieldValue(int index) {
        return TopAsinUtil.getRealIntFromAsinDetailColunmString(row[index]);
    }

    public double getDoubleFieldValue(int index) {
        return Double.parseDouble(row[index]);
    }

    public String getStringFieldValue(int index) {
        return row[index];
    }

    public String[] getRow() {
        return row;
    }

    @Override
    public String toString() {
        return "AsinDetail [row=" + Arrays.toString(row) + "]";
    }

}