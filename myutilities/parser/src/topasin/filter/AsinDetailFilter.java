package topasin.filter;

import topasin.util.AsinDetail;

/**
 * filter asins that no to be calculated in top asin.
 * 
 * @author mengzang
 * 
 */
public interface AsinDetailFilter {

    /**
     * return null if filtered.
     * 
     * @param asinDetail
     * @return
     */
    AsinDetail filterAsinDetailLine(AsinDetail asinDetail);

}
