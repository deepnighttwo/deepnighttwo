package topasin.compare;

import java.text.DecimalFormat;

import topasin.util.AsinDetail;
import topasin.util.AsinFields;
import topasin.util.TopAsinContext;
import topasin.util.TopAsinUtil;

/**
 * compare top asin use (total unhealthy * cost used for calculations)
 * 
 * @author mengzang
 * 
 */
public final strictfp class StandardOIHComparable implements AsinComparable {

    private static int TOTAL_UNHEALTH_QTY = AsinFields.TotalUnhealthyQuantity.getIndex();

    private static int COST4REPORT = AsinFields.CostUsedForReporting.getIndex();

    public static void initStandardOIHComparable() {
        AsinDetail.addNeededFields(AsinFields.TotalUnhealthyQuantity.getName(),
                AsinFields.CostUsedForReporting.getName());
    }

    private double compareValueOrig = TopAsinSelectionOptionHelper.NULL_VALUE;
    private double compareValueNew = TopAsinSelectionOptionHelper.NULL_VALUE;

    @Override
    public void fillCompareFields(AsinDetail asinDetail, int source) {
        if (TopAsinContext.ORIG_SOURCE == source) {
            compareValueOrig = asinDetail.getDoubleFieldValue(TOTAL_UNHEALTH_QTY)
                    * asinDetail.getDoubleFieldValue(COST4REPORT);
        } else if (TopAsinContext.NEW_SOURCE == source) {
            compareValueNew = asinDetail.getDoubleFieldValue(TOTAL_UNHEALTH_QTY)
                    * asinDetail.getDoubleFieldValue(COST4REPORT);
        }
    }

    @Override
    public boolean isValidAsinForTopAsin() {
        return TopAsinSelectionOptionHelper.applyTopAsinSelectionOption(compareValueOrig, compareValueNew);
    }

    private double getCompareDiff() {
        double diff = 0;
        boolean hasNew = compareValueNew > TopAsinSelectionOptionHelper.INVALID_VALUE;
        boolean hasOrig = compareValueOrig > TopAsinSelectionOptionHelper.INVALID_VALUE;

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

    private double getCompareDiffAbs() {
        return Math.abs(getCompareDiff());
    }

    @Override
    public int compareForTopAsin(AsinComparable asin) {
        return compareDiff((StandardOIHComparable) asin);
    }

    /**
     * desc order
     */
    @Override
    public int compareForOutputSort(AsinComparable asin) {
        return -1 * compareDiff((StandardOIHComparable) asin);
    }

    private int compareDiff(StandardOIHComparable asin) {
        if (asin == null) {
            return 1;
        }
        return TopAsinUtil.moneyCompare(getCompareDiffAbs(), asin.getCompareDiffAbs());
    }

    @Override
    public String getCompareDiffDisplayValue() {
        return getFormattedDouble(getCompareDiff());
    }

    @Override
    public String getCompareOrigDisplayValue() {
        if (compareValueOrig < TopAsinSelectionOptionHelper.INVALID_VALUE) {
            return "N/A";
        }
        return getFormattedDouble(this.compareValueOrig);
    }

    @Override
    public String getCompareNewDisplayValue() {
        if (compareValueNew < TopAsinSelectionOptionHelper.INVALID_VALUE) {
            return "N/A";
        }
        return getFormattedDouble(this.compareValueNew);
    }

    private String getFormattedDouble(double value) {
        DecimalFormat moneyValueFormat = new DecimalFormat("0.000");
        String ret = moneyValueFormat.format(value);
        // somehow on FR prod host 12003, the result using ',' for '.'
        if (ret.indexOf(',') >= 0) {
            ret = ret.replace(',', '.');
        }
        return ret;
    }

}
