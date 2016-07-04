package topasin.compare;

import topasin.util.AsinDetail;

/**
 * asin compare for top asin
 * 
 * @author mengzang
 * 
 */
public interface AsinComparable {
    /**
     * fill data that used for compare. source can be ORIG_SOURCE = 0 or NEW_SOURCE = 1. NOTICE: the instance will be
     * reused. so make sure the value of the instance will be re-rewrite every time this method is called.
     * 
     * @param asinDetail
     * @param source
     */
    void fillCompareFields(AsinDetail asinDetail, int source);

    /**
     * if this asin is like to be a top asin. E.g. the diff is > 0
     * 
     * @return
     */
    boolean isValidAsinForTopAsin();

    /**
     * compare for top asin. should return > 0 if the asin is more like a top asin, 0 if the same, <0 if more unlike to
     * be top asin
     * 
     * @param asin
     * @return
     */
    int compareForTopAsin(AsinComparable asin);

    /**
     * compare for sort. should return >0 if the asin's change is bigger, 0, equals or <0 if change is smaller
     * 
     * @param asin
     * @return
     */
    int compareForOutputSort(AsinComparable asin);

    /**
     * this is for output
     * 
     * @return
     */
    String getCompareDiffDisplayValue();

    /**
     * this is for output
     * 
     * @return
     */
    String getCompareOrigDisplayValue();

    /**
     * this is for output
     * 
     * @return
     */
    String getCompareNewDisplayValue();

}
