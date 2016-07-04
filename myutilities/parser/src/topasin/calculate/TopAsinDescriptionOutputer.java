package topasin.calculate;

import static topasin.util.TopAsinUtil.log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.ArrayUtils;

import topasin.describer.AsinDescriberFactory;
import topasin.filter.AsinDetailFilterFactory;
import topasin.processor.AsinDetailFiller;
import topasin.util.AsinDetailSortComparator;
import topasin.util.TopAsinContext;
import topasin.util.TopAsinUtil;

/**
 * fill details to top asin and output top asin.
 * 
 * @author mengzang
 * 
 */
public class TopAsinDescriptionOutputer {
    TopAsinContext topAsinContext;

    public TopAsinDescriptionOutputer(TopAsinContext context) {
        this.topAsinContext = context;
    }

    public void outputTopAsinAnlysisResult(Map<String, TopAsinsCalculator> topAsinResult) {
        Map<String, Map<String, AsinDetailAnalysisFields>> group2TopAsinMap = prepareTopAsinMap(topAsinResult);

        Map<String, Map<String, AsinDetailAnalysisFields>> topAsinWithDetails = fillAsinDetailData2TopAsin(group2TopAsinMap);

        dumpTopAsin(topAsinWithDetails);
    }

    private void dumpTopAsin(Map<String, Map<String, AsinDetailAnalysisFields>> topAsinWithDetails) {
        PrintWriter output = null;
        try {
            output = topAsinContext.getOutput();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(TopAsinUtil.OUTPUT_FILE_NOT_CREATEABLE_ERR, e);
        }

        List<String> describerNames = AsinDescriberFactory.getAllDescriberNames();
        for (int i = 0; i < describerNames.size(); i++) {
            log("Outputing top asin decription using describer " + describerNames.get(i) + "...");
            Collection<Entry<String, Map<String, AsinDetailAnalysisFields>>> sortedTopAsinEntries = TopAsinUtil
                    .sortMapEntryByGroupKey(topAsinWithDetails.entrySet());

            Comparator<AsinDetailAnalysisFields> topAsinComparatorByAbsDiff = AsinDetailSortComparator.getInstance();

            for (Entry<String, Map<String, AsinDetailAnalysisFields>> topAsin4Group : sortedTopAsinEntries) {
                AsinDetailAnalysisFields[] sortedTopAsin4Group = topAsin4Group.getValue().values()
                        .toArray(new AsinDetailAnalysisFields[0]);
                if (ArrayUtils.isEmpty(sortedTopAsin4Group) == true) {
                    continue;
                }
                output.println(sortedTopAsin4Group[0].getDescribers()[i].getTopAsinGroupStartDescription());
                Arrays.sort(sortedTopAsin4Group, topAsinComparatorByAbsDiff);
                for (AsinDetailAnalysisFields topAsin : sortedTopAsin4Group) {
                    String description = topAsin.getDescribers()[i].getTopAsinDescription();
                    output.println(description);
                }
                output.println("");
            }
        }
        output.close();
        log("Output top asin decription finished.");
    }

    private CountDownLatch startFillAsinDetailsData(BufferedReader origReader, int source, int threadNumber,
            Map<String, Map<String, AsinDetailAnalysisFields>> group2TopAsins) {
        AtomicInteger origLineCounter = new AtomicInteger();
        Runnable[] fillers = new Runnable[threadNumber];
        for (int i = 0; i < threadNumber; i++) {
            fillers[i] = new AsinDetailFiller(origReader, source, AsinDetailFilterFactory.getFilter(), group2TopAsins,
                    origLineCounter);
        }

        String fillerName = TopAsinContext.ORIG_SOURCE == source ? "Original asin detail filler"
                : "New asin detail filler";

        int logInterval = (Integer) topAsinContext.getRTOptions().get(TopAsinContext.LOG_INTERVAL);

        final CountDownLatch finishLock = new CountDownLatch(1);
        TopAsinUtil.asyncExecuteMulthThreadAndWait4Termination(fillerName, fillers, logInterval, origLineCounter,
                new Runnable() {

                    @Override
                    public void run() {
                        finishLock.countDown();
                    }
                });
        return finishLock;
    }

    private Map<String, Map<String, AsinDetailAnalysisFields>> fillAsinDetailData2TopAsin(
            Map<String, Map<String, AsinDetailAnalysisFields>> group2TopAsins) {
        int outputThreadNumber = (Integer) topAsinContext.getRTOptions().get(TopAsinContext.OUTPUT_THREAD_NUMBER);
        int origFileIteratorThread = outputThreadNumber;
        int newFileIteratorThread = outputThreadNumber;
        log("Filling top asin with asin details...");
        try {
            BufferedReader origReader = topAsinContext.getOriginalFileReader();
            CountDownLatch origFillerLock = startFillAsinDetailsData(origReader, TopAsinContext.ORIG_SOURCE,
                    origFileIteratorThread, group2TopAsins);
            origFillerLock.await();

            BufferedReader newReader = topAsinContext.getNewFileReader();
            CountDownLatch newFillerLock = startFillAsinDetailsData(newReader, TopAsinContext.NEW_SOURCE,
                    newFileIteratorThread, group2TopAsins);
            newFillerLock.await();

            log("Filling top asin with asin finished");
        } catch (IOException e) {
            System.exit(119);
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return group2TopAsins;
    }

    /**
     * move top asin from TopAsins's queue to a Map<String(Asin),AsinDetailAnalysisFields>
     * 
     * @param topAsinResult
     * @return
     */
    private static Map<String, Map<String, AsinDetailAnalysisFields>> prepareTopAsinMap(
            Map<String, TopAsinsCalculator> topAsinResult) {
        // no multi-thread write. use HashMap
        Map<String, Map<String, AsinDetailAnalysisFields>> group2TopAsins = new HashMap<String, Map<String, AsinDetailAnalysisFields>>(
                topAsinResult.size() * 2);

        for (Entry<String, TopAsinsCalculator> entry : topAsinResult.entrySet()) {
            TopAsinsCalculator topAsins4Group = entry.getValue();
            String groupKey = entry.getKey();
            Collection<AsinDetailAnalysisFields> asins = topAsins4Group.getTopAsins();
            Map<String, AsinDetailAnalysisFields> value = new HashMap<String, AsinDetailAnalysisFields>(
                    asins.size() * 2);
            group2TopAsins.put(groupKey, value);
            for (AsinDetailAnalysisFields asin : asins) {
                asin.setDescribers(AsinDescriberFactory.getAllDescribers());
                value.put(asin.getAsin(), asin);
            }
        }

        return group2TopAsins;
    }

}