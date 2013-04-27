package topasin.filter;

import java.util.List;
import java.util.Map;

import topasin.util.TopAsinContext;

/**
 * create filter instance based on config
 * 
 * @author mengzang
 * 
 */
public class AsinDetailFilterFactory {

    private static final int NO_FILTER = -1;
    private static final int AsinFields_Filter = 0;

    private static int filterType = -1;

    public static void init(Map<String, Object> rtOptions) {
        String filterName = (String) rtOptions.get(TopAsinContext.FILTER_NAME);
        @SuppressWarnings("unchecked")
        Map<String, List<String>> settings = (Map<String, List<String>>) rtOptions.get(TopAsinContext.FILTER_SETTING);
        if ("IogGlFilter".equalsIgnoreCase(filterName) || "AsinFieldsFilter".equalsIgnoreCase(filterName)) {
            filterType = AsinFields_Filter;
            AsinFieldsFilter.initAsinFieldValueFilter(settings);
        } else {
            filterType = NO_FILTER;
        }
    }

    public static AsinDetailFilter getFilter() {
        switch (filterType) {
            case NO_FILTER:
                return null;
            case AsinFields_Filter:
                return AsinFieldsFilter.getInstance();
            default:
                return null;
        }

    }
}
