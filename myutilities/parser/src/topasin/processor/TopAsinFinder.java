package topasin.processor;

import java.util.Map;
import java.util.Map.Entry;

import topasin.calculate.AsinDetailAnalysisFields;
import topasin.calculate.TopAsinsCalculator;

/**
 * find top asin using (asin detail map of one asin detai file) and ( each asin detail line of another asin detail file)
 * 
 * @author mengzang
 * 
 */
public class TopAsinFinder implements Runnable {
    private final String groupKey;
    private final Map<String, AsinDetailAnalysisFields> asins4Group;
    private final Map<String, TopAsinsCalculator> group2TopAsin;

    public TopAsinFinder(String groupKey, Map<String, AsinDetailAnalysisFields> asins4Group,
            Map<String, TopAsinsCalculator> group2TopAsin) {
        this.groupKey = groupKey;
        this.asins4Group = asins4Group;
        this.group2TopAsin = group2TopAsin;
    }

    @Override
    public void run() {
        TopAsinsCalculator topasinsCalculator = new TopAsinsCalculator();
        group2TopAsin.put(groupKey, topasinsCalculator);

        for (Entry<String, AsinDetailAnalysisFields> asinData : asins4Group.entrySet()) {
            AsinDetailAnalysisFields asinFields = asinData.getValue();
            asinFields.getComparable();
            topasinsCalculator.addTopAsin(asinFields);
        }
    }
}
