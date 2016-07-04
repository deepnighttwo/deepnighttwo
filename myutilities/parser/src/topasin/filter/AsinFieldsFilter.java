package topasin.filter;

import static topasin.util.TopAsinUtil.LB;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import topasin.util.AsinDetail;

/**
 * filter supported asin field value
 * 
 * @author mengzang
 * 
 */
public class AsinFieldsFilter implements AsinDetailFilter {

    private static Set<String>[] fieldValues;
    private static int[] fieldIndexs;

    private AsinFieldsFilter() {

    }

    private static AsinFieldsFilter filter = null;

    @SuppressWarnings("unchecked")
    public static void initAsinFieldValueFilter(Map<String, List<String>> settings) {
        if (settings == null || settings.size() == 0) {
            return;
        }
        fieldIndexs = new int[settings.size()];
        fieldValues = new Set[settings.size()];

        int i = 0;
        boolean createInstance = false;
        for (Entry<String, List<String>> filterField : settings.entrySet()) {
            fieldIndexs[i] = AsinDetail.getIndexFromFieldName(filterField.getKey());
            fieldValues[i] = getSetFromListWithoutEmptyString(filterField.getValue());
            createInstance |= (fieldValues[i] != null);
            i++;
        }

        if (createInstance == true) {
            filter = new AsinFieldsFilter();
        }
    }

    public static AsinFieldsFilter getInstance() {
        return filter;
    }

    private static Set<String> getSetFromListWithoutEmptyString(List<String> list) {
        Set<String> ret = null;
        if (list == null) {
            return null;
        }
        for (String str : list) {
            if (StringUtils.isNotBlank(str)) {
                if (ret == null) {
                    ret = new HashSet<String>(list.size());
                }
                ret.add(str.trim());
            }
        }
        return ret;
    }

    @Override
    public AsinDetail filterAsinDetailLine(AsinDetail asinDetail) {

        for (int i = 0; i < fieldIndexs.length; i++) {
            if (fieldValues[i] == null) {
                continue;
            }
            String fieldValue = asinDetail.getStringFieldValue(fieldIndexs[i]);
            if (fieldValues[i].contains(fieldValue) == false) {
                return null;
            }
        }
        return asinDetail;
    }

    @Override
    public String toString() {
        StringBuilder filterDesc = new StringBuilder("AsinFieldValueFilter [");
        for (int i = 0; i < fieldIndexs.length; i++) {
            filterDesc.append(LB + "\t" + AsinDetail.getFieldDisplayNameFromIndex(fieldIndexs[i]));
            filterDesc.append("=" + fieldValues[i]);
        }
        filterDesc.append(LB + "]");
        return filterDesc.toString();
    }

}
