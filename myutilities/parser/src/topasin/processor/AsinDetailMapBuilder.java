package topasin.processor;

import java.io.BufferedReader;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import topasin.calculate.AsinDetailAnalysisFields;
import topasin.filter.AsinDetailFilter;
import topasin.util.AsinDetail;
import topasin.util.TopAsinMapFactory;

/**
 * build map from asin detail files
 * 
 * @author mengzang
 * 
 */
public class AsinDetailMapBuilder extends AbstractAsinDetailFileProcessor {

    private final Map<String, Map<String, AsinDetailAnalysisFields>> group2AsinMap;
    private final int source;

    public AsinDetailMapBuilder(BufferedReader reader, int source, AsinDetailFilter filter,
            Map<String, Map<String, AsinDetailAnalysisFields>> group2AsinMap, AtomicInteger totalCounter) {
        super(reader, filter, totalCounter);

        this.group2AsinMap = group2AsinMap;
        this.source = source;
    }

    @Override
    public void processAsinDetailLine(String asinDetailLine, AsinDetail asinDetail) {
        String groupKey = asinDetail.getGroupKey();
        Map<String, AsinDetailAnalysisFields> asinMap = group2AsinMap.get(groupKey);
        if (asinMap == null) {
            asinMap = TopAsinMapFactory.getAsinMap(groupKey);
            group2AsinMap.put(groupKey, asinMap);
        }
        String asin = asinDetail.getAsin();
        // TODO: need to process duplicate ASINs safe if support aggregation function. this is not safe for aggregation
        // function with more than 2 threads
        AsinDetailAnalysisFields newAsin = asinMap.get(asin);
        if (newAsin == null) {
            newAsin = AsinDetailAnalysisFields.getInstance(asinDetail);
            synchronized (asinMap) {
                AsinDetailAnalysisFields newAsinDoubleCheck = asinMap.get(asin);
                if (newAsinDoubleCheck == null) {
                    AsinDetailAnalysisFields oldVal = asinMap.put(asin, newAsin);
                    if (oldVal != null) {
                        throw new RuntimeException("Duplicate added asin to asin map.");
                    }
                } else {
                    newAsin = newAsinDoubleCheck;
                }
            }
        }

        newAsin.getComparable().fillCompareFields(asinDetail, source);

        newAsin = asinMap.put(newAsin.getAsin(), newAsin);
    }
}
