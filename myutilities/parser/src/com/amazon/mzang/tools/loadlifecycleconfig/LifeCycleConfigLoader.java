package com.amazon.mzang.tools.loadlifecycleconfig;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class LifeCycleConfigLoader {

    /**
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws Exception {
        String lc = "insert workflow_db.oih_config(id,org,gl,iog,category,subcategory,asin,value,tag,valueType) values "
                + "(concat('lctest',uuid()),'%s',%s, %s, %s, %s, null, '%s', 'LIFECYCLE_%sRELEASED', 'NUMBER');";
        // GL, IOG, Category, Sub Category, Mean Age, Quarter,
        BufferedReader br = new BufferedReader(
                new FileReader(
                        "C:\\mymise\\myprojects\\deepnighttwo\\myutilities\\parser\\src\\com\\amazon\\mzang\\tools\\loadlifecycleconfig\\data.csv"));
        String line = null;
        int[] iogs = {
                70, 71, 73
        };
        while ((line = br.readLine()) != null) {
            try {
                String[] row = line.split("\t");
                String category = row[0].trim();
                String quarter = row[1].trim();
                int age = Math.round(Float.parseFloat(row[3].trim()));
                for (int iog : iogs) {
                    System.out.println(String.format(lc, "CNAmazon", 147, iog, category, "null", age, quarter));
                }
            } catch (Exception ex) {
                continue;
            }
        }
    }
}
