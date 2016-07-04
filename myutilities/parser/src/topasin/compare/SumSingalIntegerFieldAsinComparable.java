package topasin.compare;

import topasin.util.AsinDetail;
import topasin.util.TopAsinContext;

/**
 * this comparator is for single integer field compare.
 * 
 * @author mengzang
 * 
 */
public final class SumSingalIntegerFieldAsinComparable implements AsinComparable {

    private static int COMPARE_INDEX = -1;

    private int compareValueOrig = TopAsinSelectionOptionHelper.NULL_VALUE;
    private int compareValueNew = TopAsinSelectionOptionHelper.NULL_VALUE;


    public static void initialAsinCompareField(String compareIndexName) {
        COMPARE_INDEX = AsinDetail.getIndexFromFieldName(compareIndexName);
        AsinDetail.addNeededFields(compareIndexName);
    }

    @Override
    public synchronized void fillCompareFields(AsinDetail asinDetail, int source) {
        if (TopAsinContext.ORIG_SOURCE == source) {
            if (compareValueOrig == TopAsinSelectionOptionHelper.NULL_VALUE) {
                compareValueOrig = 0;
            }
            compareValueOrig += asinDetail.getIntFieldValue(COMPARE_INDEX);
        } else if (TopAsinContext.NEW_SOURCE == source) {
            if (compareValueNew == TopAsinSelectionOptionHelper.NULL_VALUE) {
                compareValueNew = 0;
            }
            compareValueNew += asinDetail.getIntFieldValue(COMPARE_INDEX);
        }
    }

    @Override
    public boolean isValidAsinForTopAsin() {
        return TopAsinSelectionOptionHelper.applyTopAsinSelectionOption(compareValueOrig, compareValueNew);
    }

    private int getCompareDiff() {
        int diff = 0;
        boolean hasNew = compareValueNew >= 0;
        boolean hasOrig = compareValueOrig >= 0;

        if (hasNew == true && hasOrig == true) {
            // in both
            diff = compareValueNew - compareValueOrig;
        } else if (hasNew == true && hasOrig == false) {
            // only in new
            diff = compareValueNew - 0;
        } else if (hasNew == false && hasOrig == true) {
            // only in orig
            diff = 0 - compareValueOrig;
        }
        return diff;
    }

    private int getCompareDiffAbs() {
        return Math.abs(getCompareDiff());
    }

    @Override
    public int compareForTopAsin(AsinComparable asin) {
        return compareAbsDiff((SumSingalIntegerFieldAsinComparable) asin);
    }

    @Override
    public int compareForOutputSort(AsinComparable asin) {
        return -1 * compareAbsDiff((SumSingalIntegerFieldAsinComparable) asin);
    }

    private int compareAbsDiff(SumSingalIntegerFieldAsinComparable asin) {
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
        if (this.compareValueOrig < 0) {
            return "N/A";
        }
        return String.valueOf(this.compareValueOrig);
    }

    @Override
    public String getCompareNewDisplayValue() {
        if (this.compareValueNew < 0) {
            return "N/A";
        }
        return String.valueOf(this.compareValueNew);
    }

    @Override
    public String toString() {
        return "SingalIntegerFieldAsinComparable [compareValueOrig=" + compareValueOrig + ", compareValueNew="
                + compareValueNew + ", compareAbsDiff=" + this.getCompareDiff() + "]";
    }

}
