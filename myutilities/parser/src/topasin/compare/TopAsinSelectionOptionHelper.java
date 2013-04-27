package topasin.compare;

import java.util.Map;

public final class TopAsinSelectionOptionHelper {

    /**
     * top ASIN selection strategy Strategy code
     */
    private static boolean ONLY_EXIST_IN_BOTH_FILE = true;
    private static boolean ONLY_EXIST_IN_NEW_FILE = true;
    private static boolean ONLY_EXIST_IN_ORIG_FILE = true;
    private static boolean ONLY_DECREASE = true;
    private static boolean ONLY_INCREASE = true;

    public static final int NULL_VALUE = -8;
    public static final int INVALID_VALUE = -7;

    public static void initCompareStrategy(Map<String, String> topAsinSelectionStrategy) {
        ONLY_EXIST_IN_BOTH_FILE = Boolean.valueOf(topAsinSelectionStrategy.get("OnlyExistInBothFile"));
        ONLY_EXIST_IN_NEW_FILE = Boolean.valueOf(topAsinSelectionStrategy.get("OnlyExistInNewFile"));
        ONLY_EXIST_IN_ORIG_FILE = Boolean.valueOf(topAsinSelectionStrategy.get("OnlyExistInOrigFile"));
        ONLY_DECREASE = Boolean.valueOf(topAsinSelectionStrategy.get("OnlyDecrease"));
        ONLY_INCREASE = Boolean.valueOf(topAsinSelectionStrategy.get("OnlyIncrease"));
    }

    public static final boolean applyTopAsinSelectionOption(int compareValueOrig, int compareValueNew) {
        int diff = 0;
        boolean hasNew = compareValueNew >=  -4;
        boolean hasOrig = compareValueOrig >= -4;

        if (hasNew == true && hasOrig == true) {
            // in both
            if (ONLY_EXIST_IN_BOTH_FILE == false) {
                return false;
            }
            diff = compareValueNew - compareValueOrig;
        } else if (hasNew == true && hasOrig == false) {
            // only in new
            if (ONLY_EXIST_IN_NEW_FILE == false) {
                return false;
            }
            diff = compareValueNew - 0;
        } else if (hasNew == false && hasOrig == true) {
            // only in orig
            if (ONLY_EXIST_IN_ORIG_FILE == false) {
                return false;
            }
            diff = 0 - compareValueOrig;
        } else {
            throw new RuntimeException("Value not assigned when added to map.");
        }

        if (diff == 0) {
            return false;
        } else if (diff > 0) {
            return ONLY_INCREASE;
        } else {
            return ONLY_DECREASE;
        }

    }

    public static final boolean applyTopAsinSelectionOption(double compareValueOrig, double compareValueNew) {
        double diff = 0;
        boolean hasNew = compareValueNew > -1;
        boolean hasOrig = compareValueOrig > -1;

        if (hasNew == true && hasOrig == true) {
            // in both
            if (ONLY_EXIST_IN_BOTH_FILE == false) {
                return false;
            }
            diff = compareValueNew - compareValueOrig;
        } else if (hasNew == true && hasOrig == false) {
            // only in new
            if (ONLY_EXIST_IN_NEW_FILE == false) {
                return false;
            }
            diff = compareValueNew - 0;
        } else if (hasNew == false && hasOrig == true) {
            // only in orig
            if (ONLY_EXIST_IN_ORIG_FILE == false) {
                return false;
            }
            diff = 0 - compareValueOrig;
        } else {
            throw new RuntimeException("Value not assigned when added to map.");
        }

        if (diff == 0) {
            return false;
        } else if (diff > 0) {
            return ONLY_INCREASE;
        } else {
            return ONLY_DECREASE;
        }
    }

}
