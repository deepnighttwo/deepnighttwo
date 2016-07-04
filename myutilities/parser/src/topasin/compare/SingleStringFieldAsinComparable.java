package topasin.compare;

import org.apache.commons.lang3.StringUtils;

import topasin.util.AsinDetail;
import topasin.util.TopAsinContext;

/**
 * this comparator is for single integer field compare.
 * 
 * @author mengzang
 * 
 */
public final class SingleStringFieldAsinComparable implements AsinComparable {

    private static int COMPARE_INDEX = -1;

    private String compareValueOrig = NULL_VALUE;
    private String compareValueNew = NULL_VALUE;

    private static final String NULL_VALUE = "<Null>";

    public static void initialAsinCompareField(String compareIndexName) {
        COMPARE_INDEX = AsinDetail.getIndexFromFieldName(compareIndexName);
        AsinDetail.addNeededFields(compareIndexName);
    }

    @Override
    public void fillCompareFields(AsinDetail asinDetail, int source) {
        if (TopAsinContext.ORIG_SOURCE == source) {
            compareValueOrig = asinDetail.getStringFieldValue(COMPARE_INDEX);
        } else if (TopAsinContext.NEW_SOURCE == source) {
            compareValueNew = asinDetail.getStringFieldValue(COMPARE_INDEX);
        }
    }

    @Override
    public boolean isValidAsinForTopAsin() {
        return StringUtils.equals(compareValueOrig, compareValueNew) == false;
    }

    private int getCompareDiff() {
        return compareValueOrig.compareTo(compareValueNew);
    }

    private int getCompareDiffAbs() {
        return Math.abs(getCompareDiff());
    }

    @Override
    public int compareForTopAsin(AsinComparable asin) {
        return compareAbsDiff((SingleStringFieldAsinComparable) asin);
    }

    @Override
    public int compareForOutputSort(AsinComparable asin) {
        return -1 * compareAbsDiff((SingleStringFieldAsinComparable) asin);
    }

    private int compareAbsDiff(SingleStringFieldAsinComparable asin) {
        if (asin == null) {
            return 1;
        }
        return this.getCompareDiffAbs() - asin.getCompareDiffAbs();
    }

    @Override
    public String getCompareDiffDisplayValue() {
        return String.valueOf(this.getCompareDiff());
    }

    @Override
    public String getCompareOrigDisplayValue() {
        return compareValueOrig;
    }

    @Override
    public String getCompareNewDisplayValue() {
        return compareValueNew;
    }

    @Override
    public String toString() {
        return "SingalIntegerFieldAsinComparable [compareValueOrig=" + compareValueOrig + ", compareValueNew="
                + compareValueNew + ", compareAbsDiff=" + this.getCompareDiff() + "]";
    }

}
