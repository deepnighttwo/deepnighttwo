package topasin.compare;

import java.util.Map;

import topasin.util.TopAsinContext;
import topasin.util.TopAsinUtil;

/**
 * create AsinComparable based on config
 * 
 * @author mengzang
 * 
 */
public final class AsinComparableFactory {

    public static final int SINGLE_INTEGER_FIELD_COMPARE = 0;
    public static final int OIH_STANDARD_COMPARE = 1;
    public static final int JS_COMPARE = 2;
    public static final int SumSingleIntegerFieldAsinComparable_Type = 3;
    public static final int SingleStringFieldAsinComparable_Type = 4;

    private static int compareType = 0;

    public static void init(Map<String, Object> rtOptions) {
        String comparatorName = (String) rtOptions.get(TopAsinContext.COMPARATOR_NAME);
        @SuppressWarnings("unchecked")
        Map<String, String> topAsinSelectionOption = (Map<String, String>) rtOptions
                .get(TopAsinContext.TOP_ASIN_SELECTION_OPTION);
        TopAsinSelectionOptionHelper.initCompareStrategy(topAsinSelectionOption);
        if ("SingleIntegerComparator".equalsIgnoreCase(comparatorName)) {
            compareType = SINGLE_INTEGER_FIELD_COMPARE;
            SingleIntegerFieldAsinComparable.initialAsinCompareField(((String[]) rtOptions
                    .get(TopAsinContext.ASIN_COMPARE_FIELD_NAME))[0]);
        } else if ("StandardOIHComparable".equalsIgnoreCase(comparatorName)) {
            compareType = OIH_STANDARD_COMPARE;
            StandardOIHComparable.initStandardOIHComparable();
        } else if ("JSAsinComparable".equalsIgnoreCase(comparatorName)) {
            compareType = JS_COMPARE;
            JSAsinComparable.initJSAsinComparable();
        } else if ("SumSingalIntegerFieldAsinComparable".equalsIgnoreCase(comparatorName)) {
            compareType = SumSingleIntegerFieldAsinComparable_Type;
            SumSingalIntegerFieldAsinComparable.initialAsinCompareField(((String[]) rtOptions
                    .get(TopAsinContext.ASIN_COMPARE_FIELD_NAME))[0]);
        } else if ("SingleStringFieldAsinComparable".equalsIgnoreCase(comparatorName)) {
            compareType = SingleStringFieldAsinComparable_Type;
            SingleStringFieldAsinComparable.initialAsinCompareField(((String[]) rtOptions
                    .get(TopAsinContext.ASIN_COMPARE_FIELD_NAME))[0]);
        } else {
            throw new IllegalArgumentException(TopAsinUtil.NO_SUCH_COMPARATOR_ERR + ":" + comparatorName);
        }

    }

    public static AsinComparable getAsinComparable() {
        switch (compareType) {
            case SINGLE_INTEGER_FIELD_COMPARE:
                return new SingleIntegerFieldAsinComparable();
            case OIH_STANDARD_COMPARE:
                return new StandardOIHComparable();
            case JS_COMPARE:
                return new JSAsinComparable();
            case SumSingleIntegerFieldAsinComparable_Type:
                return new SumSingalIntegerFieldAsinComparable();
            case SingleStringFieldAsinComparable_Type:
                return new SingleStringFieldAsinComparable();
            default:
                return null;
        }
    }
}
