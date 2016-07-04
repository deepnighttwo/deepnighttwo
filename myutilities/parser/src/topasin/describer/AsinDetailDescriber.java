package topasin.describer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import topasin.calculate.AsinDetailAnalysisFields;
import topasin.compare.AsinComparable;
import topasin.util.AsinDetail;
import topasin.util.TopAsinContext;
import topasin.util.TopAsinUtil;

/**
 * get whole asin detail line and output it.
 * 
 * @author mengzang
 * 
 */
public class AsinDetailDescriber implements TopAsinDescriber {

    Collection<String> asinDetailLineOrig = new ConcurrentLinkedQueue<String>();

    Collection<String> asinDetailLineNew = new ConcurrentLinkedQueue<String>();

    AsinDetailAnalysisFields analysisFields;

    private static final String TITLE = "Asin,Iog,Gl,Target Inventory Level,Total Inventory,Weekly Forecast Demand,Weekly Cptl Holding Cost,One Week Historic Demand,Two Weeks Historic Demand,Three Weeks Historic Demand,Four Weeks Historic Demand,One Year Historic Demand,Our Price,Cost Used For Calculations,Vendor Cost,Retail Contribution,Return Contribution,Title Description,Cube,Publication Date,Release Date,Sort Type,Upc,Is Unprep Required,Allocated Inventory,In Process Inventory,Unsellable Inventory,Warehouse,Vendor,Order Type,Removal Type,Weekly Fc Holding Cost,Fc Receipt Cost,Fc Removal Cost,Removal Amount,Total Savings,Unhealthy Quantity,Healthy Quantity,Dsi Id,Receipt Date,Cannot Return Before,Must Return Before,Do Id,Ds Id,Do Date,Ds Date,Distributor Price,Distributor Cost,Exclusion Reason,Excluded Vendor,Excluded Order Type,Excluded Removal Type,Excluded Removal Amount,Excluded Total Savings,Excluded Unhealthy Quantity,Excluded Dsi Id,Excluded Receipt Date,Excluded Cannot Return Before,Excluded Must Return Before,Excluded Do Id,Excluded Ds Id,Excluded Do Date,Excluded Ds Date,Excluded Distributor Price,Excluded Distributor Cost,Category,Subcategory,Markdown Quantity,Markdown Duration,Markdown Price,Markdown Demand Factor,Elasticity,Total Healthy Quantity,Total Unhealthy Quantity,Total Healthy Woc,Total Woc,Warehouse Quantity,Is Owoc,Vendor Name,Ship To Name,Ship To Address Line 1,Ship To Address Line 2,Ship To Address Line 3,Ship To Address City,Ship To Address State,Ship To Address Province,Ship To Address Postal Code,Ship To Address Country Code,Ean,List Price,Map Price,Is Map Required,Publisher Code,Total Retail Healthy,Total Retail Savings,Cost Used For Reporting,Parent Asin,Replenishment Category,Child Asin,Bundle Quantity,Is Forced Markdown,Mean Age,Is Deadwood,Fcsku,Expiration Date,Realm,Is Authorization Required,Carton Quantity,Forecast1,Forecast2,Forecast3,Forecast4,Forecast5,Forecast6,Forecast7,Forecast8,Forecast9,Forecast10,Forecast11,Forecast12,Forecast13,Forecast14,Forecast15,Forecast16,Forecast17,Forecast18,Forecast19,Forecast20,Forecast21,Forecast22,Forecast23,Forecast24,Forecast25,Forecast26,Forecast27,Forecast28,Forecast29,Forecast30,Forecast31,Forecast32,Forecast33";

    private static final String[] TITLE_ARR = TITLE.split(",");

    @Override
    public void fillDataForOutput(int source, String asinDetailLine, AsinDetail asinDetail,
            AsinDetailAnalysisFields analysisFields) {
        if (TopAsinContext.ORIG_SOURCE == source) {
            asinDetailLineOrig.add(asinDetailLine);
        } else {
            asinDetailLineNew.add(asinDetailLine);

        }
        if (this.analysisFields == null) {
            this.analysisFields = analysisFields;
        }
    }

    @Override
    public String getTopAsinDescription() {
        StringBuilder topAsinDesc = new StringBuilder();
        AsinComparable cmp = analysisFields.getComparable();
        TopAsinUtil.appendLine(topAsinDesc, "Compare Value Diff: " + cmp.getCompareDiffDisplayValue());

        TopAsinUtil.appendLine(topAsinDesc, analysisFieldDiff(asinDetailLineOrig, asinDetailLineNew));

        TopAsinUtil.appendLine(topAsinDesc, "Orig: Compare Value: " + cmp.getCompareOrigDisplayValue());

        TopAsinUtil.appendLine(topAsinDesc, TITLE);
        for (String line : asinDetailLineOrig) {
            TopAsinUtil.appendLine(topAsinDesc, line);
        }
        TopAsinUtil.appendLine(topAsinDesc, "New: Compare Value: " + cmp.getCompareNewDisplayValue());
        TopAsinUtil.appendLine(topAsinDesc, TITLE);
        for (String line : asinDetailLineNew) {
            TopAsinUtil.appendLine(topAsinDesc, line);
        }
        return topAsinDesc.toString();
    }

    private String analysisFieldDiff(Collection<String> origLines, Collection<String> newLines) {
        if (origLines == null || origLines.size() == 0 || newLines == null || newLines.size() == 0) {
            return null;
        }

        List<Set<String>> origRows = buildRows(origLines);
        List<Set<String>> newRows = buildRows(newLines);

        /**
         * it is hard to compare and say column are equal. the question is : does count of a value matters. For example,
         * for column forecast, it doesn't matter how many rows in original and how many rows in new. As long as they
         * have the save value, it can be consider as equal. This is the case here is dealing with. It covers almost all
         * inputs such as forecast, TIP, Carton, Price, Contribution and so on. The case that is not dealing with is
         * count of the value. For example,"Exclusion Reason" column, even in orig and new, there are two values "A" and
         * "B". But in orig there are 3A and 2B but in new there are 3B and 2A. this will be consider as equal in this
         * method.
         */

        StringBuilder diff = new StringBuilder();
        TopAsinUtil.appendLine(diff, "Different Columns");

        for (int i = 0; i < TITLE_ARR.length; i++) {
            Set<String> origVal = origRows.get(i);
            Set<String> newVal = newRows.get(i);
            if (origVal.equals(newVal) == true || (newVal.size() == 0 && origVal.size() == 0)) {
                origRows.set(i, Collections.<String> emptySet());
                newRows.set(i, Collections.<String> emptySet());
            } else {
                diff.append(TITLE_ARR[i] + ",");
            }
        }
        TopAsinUtil.appendLine(diff, null);

        for (int i = 0; i < TITLE_ARR.length; i++) {
            Set<String> origVal = origRows.get(i);
            if (origVal.isEmpty()) {
                continue;
            }
            diff.append(origVal.toString().replace(",", " |") + ",");
        }

        TopAsinUtil.appendLine(diff, null);

        for (int i = 0; i < TITLE_ARR.length; i++) {
            Set<String> newVal = newRows.get(i);
            if (newVal.isEmpty()) {
                continue;
            }
            diff.append(newVal.toString().replace(",", " |") + ",");
        }

        TopAsinUtil.appendLine(diff, null);

        return diff.toString();
    }

    private static List<Set<String>> buildRows(Collection<String> lines) {
        List<Set<String>> rows = new ArrayList<Set<String>>(lines.size());

        for (int i = 0; i < TITLE_ARR.length; i++) {
            rows.add(new LinkedHashSet<String>(lines.size(), (float) 1.0));
        }

        for (String line : lines) {
            String[] rowData = line.split(",");
            for (int i = 0; i < TITLE_ARR.length; i++) {
                rows.get(i).add(rowData[i]);
            }
        }
        return rows;
    }

    @Override
    public String getTopAsinGroupStartDescription() {
        return "Top Asin Details for group:" + analysisFields.getGroupKey();
    }

}
