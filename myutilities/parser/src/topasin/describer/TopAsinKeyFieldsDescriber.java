package topasin.describer;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import topasin.calculate.AsinDetailAnalysisFields;
import topasin.compare.AsinComparable;
import topasin.util.AsinDetail;
import topasin.util.TopAsinContext;
import topasin.util.TopAsinUtil;

/**
 * output key fields seted in TopAsinFileBasedTransformer.Describer.KeyFields
 * 
 * @author mengzang
 * 
 */

public class TopAsinKeyFieldsDescriber implements TopAsinDescriber {

    AsinDetailAnalysisFields analysisFields;

    private static int[] KEY_FIELDS_INDEX;
    private static String KEY_FIELDS_TITLE;

    private String[] origKeyFieldValues;
    private String[] newKeyFieldValues;

    public static void initKeyFieldNames(Map<String, Object> rtOptions) {
        @SuppressWarnings("unchecked")
        List<String> keyFieldsList = (List<String>) rtOptions.get(TopAsinContext.DESCRIBER_KEY_FIELD_NAMES);

        String[] keyFieldsNames = keyFieldsList.toArray(new String[0]);

        AsinDetail.addNeededFields(keyFieldsNames);

        KEY_FIELDS_INDEX = new int[keyFieldsNames.length];

        StringBuilder titleLine = new StringBuilder("Asin,Compare Val Diff,Compare Val Orig,Compare Val New");

        for (int i = 0; i < keyFieldsNames.length; i++) {
            KEY_FIELDS_INDEX[i] = AsinDetail.getIndexFromFieldName(keyFieldsNames[i]);
            String title = AsinDetail.getFieldDisplayNameFromIndex(KEY_FIELDS_INDEX[i]);

            titleLine.append(",");
            titleLine.append(title);
            titleLine.append(" Orig");

            titleLine.append(",");
            titleLine.append(title);
            titleLine.append(" New");
        }
        KEY_FIELDS_TITLE = titleLine.toString();
    }

    @Override
    public void fillDataForOutput(int source, String asinDetailLine, AsinDetail asinDetail,
            AsinDetailAnalysisFields analysisFields) {
        if (this.analysisFields == null) {
            this.analysisFields = analysisFields;
        }

        fillKeyFields(source, asinDetail);
    }

    private void fillKeyFields(int source, AsinDetail asinDetail) {
        if (KEY_FIELDS_INDEX != null) {
            String[] value2Fill = null;
            if (source == TopAsinContext.ORIG_SOURCE) {
                if (origKeyFieldValues != null) {
                    return;
                }
                value2Fill = origKeyFieldValues = new String[KEY_FIELDS_INDEX.length];
            } else if (source == TopAsinContext.NEW_SOURCE) {
                if (newKeyFieldValues != null) {
                    return;
                }
                value2Fill = newKeyFieldValues = new String[KEY_FIELDS_INDEX.length];
            } else {
                throw new IllegalArgumentException("Unknown source when fill data to asin describer: " + source);
            }

            for (int i = 0; i < KEY_FIELDS_INDEX.length; i++) {
                value2Fill[i] = asinDetail.getStringFieldValue(KEY_FIELDS_INDEX[i]);
            }
        }
    }

    @Override
    public String getTopAsinDescription() {
        StringBuilder topAsinDesc = new StringBuilder();
        AsinComparable cmp = analysisFields.getComparable();

        topAsinDesc.append("\"" + analysisFields.getAsin() + "\",");
        topAsinDesc.append("\"" + cmp.getCompareDiffDisplayValue() + "\",");
        topAsinDesc.append("\"" + cmp.getCompareOrigDisplayValue() + "\",");
        topAsinDesc.append("\"" + cmp.getCompareNewDisplayValue() + "\"");

        if (KEY_FIELDS_INDEX.length > 0) {
            for (int i = 0; i < KEY_FIELDS_INDEX.length; i++) {
                topAsinDesc.append(",\"" + (origKeyFieldValues != null ? origKeyFieldValues[i] + "\"" : "N/A\""));
                topAsinDesc.append(",\"" + (newKeyFieldValues != null ? newKeyFieldValues[i] + "\"" : "N/A\""));
            }
        }
        return topAsinDesc.toString();
    }

    @Override
    public String getTopAsinGroupStartDescription() {
        StringBuilder groupTitle = new StringBuilder();
        TopAsinUtil.appendLine(groupTitle, "Top Asin key fileds Details for group:" + analysisFields.getGroupKey());
        if (StringUtils.isNotBlank(KEY_FIELDS_TITLE) == true) {
            groupTitle.append(KEY_FIELDS_TITLE);
        }
        return groupTitle.toString();
    }

}
