package topasin.describer;

import static topasin.util.TopAsinUtil.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import topasin.util.TopAsinContext;
import topasin.util.TopAsinUtil;

/**
 * create output instance based on config
 * 
 * @author mengzang
 * 
 */
public class AsinDescriberFactory {

    public static final int ASIN_DETAILS_DESCRIBER = 0;
    public static final int ASIN_KEY_FIELDS_DESCRIBER = 1;

    private static int[] DESCRIBER_TYPES;

    private static List<String> DESCRIBER_NAMES = new ArrayList<String>();

    @SuppressWarnings("unchecked")
    public static void init(Map<String, Object> rtOptions) {
        List<String> describerNames = (List<String>) rtOptions.get(TopAsinContext.DESCRIBER_NAMES);

        if (CollectionUtils.isEmpty(describerNames)) {
            throw new IllegalArgumentException(TopAsinUtil.NO_ASIN_DESCRIBER_ERR);
        }

        DESCRIBER_TYPES = new int[describerNames.size()];

        for (int i = 0; i < describerNames.size(); i++) {
            String describerName = describerNames.get(i);
            if ("AsinDetailDescriber".equalsIgnoreCase(describerName)) {
                DESCRIBER_TYPES[i] = ASIN_DETAILS_DESCRIBER;
                DESCRIBER_NAMES.add(describerName);
            } else if ("TopAsinKeyFieldsDescriber".equalsIgnoreCase(describerName)) {
                DESCRIBER_TYPES[i] = ASIN_KEY_FIELDS_DESCRIBER;
                DESCRIBER_NAMES.add(describerName);
                TopAsinKeyFieldsDescriber.initKeyFieldNames(rtOptions);
            } else {
                log("No such describer:" + describerName);
            }
        }
        DESCRIBER_NAMES = Collections.unmodifiableList(DESCRIBER_NAMES);
    }

    public static TopAsinDescriber[] getAllDescribers() {
        TopAsinDescriber[] ret = new TopAsinDescriber[DESCRIBER_TYPES.length];
        for (int i = 0; i < DESCRIBER_TYPES.length; i++) {
            ret[i] = createOutputInstance(DESCRIBER_TYPES[i]);
        }
        return ret;
    }

    public static List<String> getAllDescriberNames() {
        return DESCRIBER_NAMES;
    }

    public static TopAsinDescriber createOutputInstance(int describerType) {
        switch (describerType) {
            case ASIN_DETAILS_DESCRIBER:
                return new AsinDetailDescriber();
            case ASIN_KEY_FIELDS_DESCRIBER:
                return new TopAsinKeyFieldsDescriber();
            default:
                return null;
        }
    }
}
