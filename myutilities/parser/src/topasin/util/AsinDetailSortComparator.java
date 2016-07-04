package topasin.util;

import java.util.Comparator;

import topasin.calculate.AsinDetailAnalysisFields;

/**
 * sort TopAsin by abs(diff) desc
 * 
 * @author mengzang
 * 
 */
public class AsinDetailSortComparator implements Comparator<AsinDetailAnalysisFields> {

    private AsinDetailSortComparator() {

    }

    private static AsinDetailSortComparator INSTANCE = new AsinDetailSortComparator();

    public static Comparator<AsinDetailAnalysisFields> getInstance() {
        return INSTANCE;
    }

    @Override
    public int compare(AsinDetailAnalysisFields a1, AsinDetailAnalysisFields a2) {
        return a1.getComparable().compareForOutputSort(a2.getComparable());
    }

}
