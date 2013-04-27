package topasin.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import topasin.filter.AsinDetailFilter;
import topasin.util.AsinDetail;

/**
 * base class for process asin detail file
 * 
 * @author mengzang
 * 
 */
public abstract class AbstractAsinDetailFileProcessor implements Runnable {

    private final BufferedReader reader;
    private final AtomicInteger totalCounter;
    private final AsinDetailFilter filter;
    private final int batchSize = 50000;

    public AbstractAsinDetailFileProcessor(final BufferedReader reader, final AsinDetailFilter filter,
            final AtomicInteger totalCounter) {
        this.reader = reader;
        this.filter = filter;
        this.totalCounter = totalCounter;
    }

    @Override
    public void run() {
        final AsinDetail asinDetail = new AsinDetail();
        final String[] batch = new String[batchSize];
        while (true) {
            try {
                readLineBatch(batch);
                if (batch[0] == null) {
                    break;
                }
                processLineBatch(asinDetail, batch);
            } catch (Throwable e) {
                e.printStackTrace();
                // result will be incorrect when any exception occurs, there is no need to continue process
                System.exit(1);
            }
        }
    }

    private void processLineBatch(AsinDetail asinDetail, final String[] batch) {
        int count = 0;
        String asinDetailLine = null;
        for (int i = 0; i < batchSize; i++) {
            asinDetailLine = batch[i];
            batch[i] = null;
            if (asinDetailLine == null) {
                return;
            }
            asinDetail.fillAsinDetailsWithNeededFields(asinDetailLine);
            if (filter != null && (filter.filterAsinDetailLine(asinDetail) == null)) {
                continue;
            }
            processAsinDetailLine(asinDetailLine, asinDetail);
            count++;
            if (count == 10000) {
                totalCounter.addAndGet(count);
                count = 0;
            }
        }
        totalCounter.addAndGet(count);
    }

    private void readLineBatch(final String[] batch) throws IOException {
        synchronized (reader) {
            String line = null;
            int counter = 0;
            while (counter < batchSize && (line = reader.readLine()) != null) {
                batch[counter] = line;
                counter++;
            }
        }
    }

    public abstract void processAsinDetailLine(String asinDetailLine, AsinDetail asinDetail);

}
