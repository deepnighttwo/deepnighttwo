package topasin.calculate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import topasin.compare.AsinComparable;
import topasin.util.TopAsinContext;
import topasin.util.TopAsinUtil;

/**
 * hold top asin queue(heap)
 * 
 * @author mengzang
 * 
 */
public final class TopAsinsCalculator {

    private static int TOP_ASIN_COUNT;

    /*
     * this uses AsinComparable for sort
     */
    private Queue<AsinDetailAnalysisFields> topAsins = new PriorityQueue<AsinDetailAnalysisFields>(TOP_ASIN_COUNT);
    /*
     * this uses asin string to avoid duplication
     */
    private Set<String> topAsinSet = new HashSet<String>(TOP_ASIN_COUNT * 2);

    private ReentrantLock sizeLock = new ReentrantLock();

    public static void init(Map<String, Object> rtOptions) {
        int topAsinCount = TopAsinUtil.getInt(rtOptions.get(TopAsinContext.TOP_ASIN_COUNT), 10);
        TOP_ASIN_COUNT = topAsinCount;
    }

    AtomicInteger totalChanged = new AtomicInteger();

    public boolean addTopAsin(AsinDetailAnalysisFields asinTopUnhealthDiff) {
        if (asinTopUnhealthDiff.comparable.isValidAsinForTopAsin() == false) {
            return false;
        }

        sizeLock.lock();
        try {
            if (topAsinSet.contains(asinTopUnhealthDiff.asin) == true) {
                return true;
            }
            if (topAsins.size() == TOP_ASIN_COUNT) {
                AsinComparable mini = topAsins.peek().comparable;
                if (mini.compareForTopAsin(asinTopUnhealthDiff.comparable) >= 0) {
                    return false;
                }
                AsinDetailAnalysisFields notTop = topAsins.poll();
                topAsinSet.remove(notTop.asin);
            }
            boolean ret = topAsins.add(asinTopUnhealthDiff);
            topAsinSet.add(asinTopUnhealthDiff.asin);
            return ret;
        } finally {
            sizeLock.unlock();
        }
    }

    public Collection<AsinDetailAnalysisFields> getTopAsins() {
        return topAsins;
    }

    public int getTotalUnhealthy() {
        return totalChanged.get();
    }
}