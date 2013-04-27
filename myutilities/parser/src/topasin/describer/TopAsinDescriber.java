package topasin.describer;

import topasin.calculate.AsinDetailAnalysisFields;
import topasin.util.AsinDetail;

/**
 * extract output value and output value.
 * 
 * @author mengzang
 * 
 */
public interface TopAsinDescriber {

    void fillDataForOutput(int source, String asinDetailLine, AsinDetail asinDetail,
            AsinDetailAnalysisFields analysisFields);

    String getTopAsinDescription();

    String getTopAsinGroupStartDescription();
}
