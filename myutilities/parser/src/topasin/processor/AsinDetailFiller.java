package topasin.processor;

import java.io.BufferedReader;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import topasin.calculate.AsinDetailAnalysisFields;
import topasin.describer.TopAsinDescriber;
import topasin.filter.AsinDetailFilter;
import topasin.util.AsinDetail;

/**
 * fill details to describer
 * 
 * @author mengzang
 * 
 */
public final class AsinDetailFiller extends AbstractAsinDetailFileProcessor {
    private final Map<String, Map<String, AsinDetailAnalysisFields>> group2TopAsins;
    private final int source;

    public AsinDetailFiller(final BufferedReader asinDetailReader, final int source, final AsinDetailFilter filter,
            final Map<String, Map<String, AsinDetailAnalysisFields>> group2TopAsins, final AtomicInteger totalCounter) {
        super(asinDetailReader, filter, totalCounter);
        this.group2TopAsins = group2TopAsins;
        this.source = source;
    }

    @Override
    public void processAsinDetailLine(String asinDetailLine, AsinDetail asinDetail) {
        AsinDetailAnalysisFields asin = AsinDetailAnalysisFields.getInstance(asinDetail);
        String groupKey = asin.getGroupKey();
        Map<String, AsinDetailAnalysisFields> topAsins = group2TopAsins.get(groupKey);
        if (topAsins == null) {
            return;
        }
        AsinDetailAnalysisFields topAsin = topAsins.get(asin.getAsin());
        if (topAsin != null) {
            TopAsinDescriber[] describers = topAsin.getDescribers();
            for (TopAsinDescriber describer : describers) {
                describer.fillDataForOutput(source, asinDetailLine, asinDetail, topAsin);
            }
        }
    }
}