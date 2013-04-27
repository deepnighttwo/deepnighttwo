package topasin.calculate;

import static topasin.util.TopAsinUtil.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import topasin.filter.AsinDetailFilterFactory;
import topasin.processor.AsinDetailMapBuilder;
import topasin.processor.TopAsinFinder;
import topasin.util.TopAsinContext;
import topasin.util.TopAsinMapFactory;
import topasin.util.TopAsinUtil;

/**
 * Top asin calculator
 * 
 * @author mengzang
 * 
 */
public class TopAsinExtractorFlow {

    private TopAsinContext topAsinContext;

    public TopAsinExtractorFlow(TopAsinContext tipasinContext) {
        this.topAsinContext = tipasinContext;
    }

    public void doAnalysis() throws IOException, InterruptedException {

        final Map<String, Map<String, AsinDetailAnalysisFields>> group2AsinMap = TopAsinMapFactory.getGroupMap();

        log("================Building asin map using new asin details file...================");
        int buildTreeThreadNumberOrig = (Integer) topAsinContext.getRTOptions().get(
                TopAsinContext.BUILD_TREE_THREAD_NUMBER);
        buildGroup2AsinMap(group2AsinMap, topAsinContext.getNewFileReader(), TopAsinContext.NEW_SOURCE,
                buildTreeThreadNumberOrig, "New AsinDetailsFile Tree Builder");

        TopAsinMapFactory.storeHistoryGroupSize(group2AsinMap);

        log("================Building asin map using orig asin details file...================");
        int buildTreeThreadNumberNew = (Integer) topAsinContext.getRTOptions().get(
                TopAsinContext.ANALYSIS_FILE_THREAD_NUMBER);
        buildGroup2AsinMap(group2AsinMap, topAsinContext.getOriginalFileReader(), TopAsinContext.ORIG_SOURCE,
                buildTreeThreadNumberNew, "Orig AsinDetailsFile Tree Builder");

        log("======================Calculating Top Asin from asin map ...======================");
        Map<String, TopAsinsCalculator> topAsinResult = calculateTopAsinUsingAsinMap(group2AsinMap,
                "Top Asin Calculator");

        // release memory. output would use lots of memory to hold top asin description data.
        group2AsinMap.clear();

        log("===========================Outputing top asin results...===========================");
        outputTopAsinResult(topAsinResult);
        log("Top asin output finished.");

    }

    private Map<String, Map<String, AsinDetailAnalysisFields>> buildGroup2AsinMap(
            Map<String, Map<String, AsinDetailAnalysisFields>> group2AsinMap, BufferedReader reader, int source,
            int threadCount, String builderName) throws IOException, InterruptedException {
        // so far integer is enough...
        final AtomicInteger totalCounter = new AtomicInteger();
        Runnable[] builders = new Runnable[threadCount];
        for (int i = 0; i < threadCount; i++) {
            builders[i] = new AsinDetailMapBuilder(reader, source, AsinDetailFilterFactory.getFilter(), group2AsinMap,
                    totalCounter);
        }

        int logInterval = (Integer) topAsinContext.getRTOptions().get(TopAsinContext.LOG_INTERVAL);

        TopAsinUtil.executeMulthThreadAndWait4Termination(builderName, builders, logInterval, totalCounter);

        return group2AsinMap;
    }

    private Map<String, TopAsinsCalculator> calculateTopAsinUsingAsinMap(
            final Map<String, Map<String, AsinDetailAnalysisFields>> group2AsinMap, String analystName)
            throws InterruptedException {
        final Map<String, TopAsinsCalculator> group2TopAsin = TopAsinMapFactory.getGroupMap();

        List<Runnable> analysts = new ArrayList<Runnable>(group2AsinMap.size());
        for (Entry<String, Map<String, AsinDetailAnalysisFields>> groupData : group2AsinMap.entrySet()) {
            String groupKey = groupData.getKey();
            Map<String, AsinDetailAnalysisFields> asins4Group = groupData.getValue();
            Runnable analyst = new TopAsinFinder(groupKey, asins4Group, group2TopAsin);
            analysts.add(analyst);
        }

        int logInterval = (Integer) topAsinContext.getRTOptions().get(TopAsinContext.LOG_INTERVAL);

        TopAsinUtil.executeMulthThreadAndWait4Termination(analystName, analysts.toArray(new Runnable[0]), logInterval,
                null);

        return group2TopAsin;
    }

    private void outputTopAsinResult(Map<String, TopAsinsCalculator> topAsinResult) {
        TopAsinDescriptionOutputer topAsinDump = new TopAsinDescriptionOutputer(topAsinContext);
        topAsinDump.outputTopAsinAnlysisResult(topAsinResult);
    }

}
