package topasin.calculate;

import java.util.Map;

import topasin.compare.AsinComparable;
import topasin.compare.AsinComparableFactory;
import topasin.describer.TopAsinDescriber;
import topasin.util.AsinDetail;
import topasin.util.AsinFields;

/**
 * used for hold asin detail for top asin calculation
 * 
 * @author mengzang
 * 
 */
public final class AsinDetailAnalysisFields implements Comparable<AsinDetailAnalysisFields> {

    public AsinDetailAnalysisFields() {
        comparable = AsinComparableFactory.getAsinComparable();
    }

    public static void init(Map<String, Object> rtOptions) {
        AsinDetail.addNeededFields(AsinFields.Asin.getName());
    }

    AsinComparable comparable;

    String groupKey;

    String asin;

    TopAsinDescriber[] describers;

    public String getAsin() {
        return asin;
    }

    public AsinComparable getComparable() {
        return comparable;
    }

    public static AsinDetailAnalysisFields getInstance(AsinDetail asinDetail) {
        AsinDetailAnalysisFields ret = new AsinDetailAnalysisFields();
        ret.fillInstance(asinDetail);
        return ret;
    }

    public void fillInstance(AsinDetail asinDetail) {
        asin = asinDetail.getAsin();
        groupKey = asinDetail.getGroupKey();
    }

    public String getGroupKey() {
        return groupKey;
    }

    @Override
    public int hashCode() {
        return asin.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return asin.equals(obj);
    }

    @Override
    public String toString() {
        return "AsinDetailAnalysisFields [groupKey=" + groupKey + ", asin=" + asin + "]";
    }

    @Override
    public int compareTo(AsinDetailAnalysisFields anotherAsin) {
        return comparable.compareForTopAsin(anotherAsin.comparable);
    }

    public void setDescribers(TopAsinDescriber[] describers) {
        this.describers = describers;
    }

    public TopAsinDescriber[] getDescribers() {
        return describers;
    }

}