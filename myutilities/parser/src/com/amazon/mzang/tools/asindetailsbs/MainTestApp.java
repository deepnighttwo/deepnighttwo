package com.amazon.mzang.tools.asindetailsbs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.TreeMap;

public class MainTestApp {

	BufferedReader origReader;
	BufferedReader sbsReader;

	PrintWriter output;
	PrintWriter error;
	int threshold;

	TopAsinByIogGLAnalytMultiLevelSource analyst;

	public void doTransform() throws IOException, SQLException {
		analyst.doAnalysis();
	}

	public static void main(String... args) throws IOException {
		BufferedReader origReader = new BufferedReader(new FileReader(new File(
				"G:/UnhealthyAsinDetails-transformed.2012-08-21_to_2012-08-28.txt")));
		BufferedReader sbsReader = new BufferedReader(new FileReader(new File(
				"G:/UnhealthyAsinDetails-transformed.2012-08-21_to_2012-08-28.txt")));

		TopAsinByIogGLAnalytMultiLevelSource analyst = new TopAsinByIogGLAnalytMultiLevelSource(
				origReader, sbsReader, new PrintWriter(new OutputStreamWriter(System.out)),
				new PrintWriter(new OutputStreamWriter(System.err)), 200);

		analyst.doAnalysis();
	}

}

class TopAsinByIogGLAnalytMultiLevelSource {

	BufferedReader origReader;
	BufferedReader sbsReader;

	PrintWriter output;
	PrintWriter error;

	int threshold;

	public TopAsinByIogGLAnalytMultiLevelSource(BufferedReader origReader,
			BufferedReader sbsReader, PrintWriter output, PrintWriter error, int threshold) {
		this.origReader = origReader;
		this.sbsReader = sbsReader;
		this.output = output;
		this.error = error;
		this.threshold = threshold;
	}

	public void doAnalysis() throws IOException {
		HashMap<Integer, HashMap<Integer, TreeMap<String, AsinDetailsSBSFields>>> iog2glOrig = buildTree(origReader);
		// veriyMap(new BufferedReader(new FileReader(new File(
		// "G:/UnhealthyAsinDetails-transformed.2012-08-21_to_2012-08-28.txt"))),
		// iog2glOrigMap);
		HashMap<Integer, HashMap<Integer, SBSTopAsins>> sbsResult = analysisFileAgainstIog2GlMap(
				sbsReader, iog2glOrig, threshold);

		outputSBSResult(sbsResult, output);

	}

	// public static void veriyMap(BufferedReader reader, Iog2Gl iog2glOrigMap)
	// throws IOException {
	// String asindetailsLine = reader.readLine();
	// while ((asindetailsLine = reader.readLine()) != null) {
	// AsinDetailLine asindetail = new AsinDetailLine(asindetailsLine);
	// AsinDetailsSBSFields sbs = AsinDetailsSBSFields.getInstance(asindetail);
	// if (sbs.equals(iog2glOrigMap.getAsin(asindetail)) == false) {
	// throw new RuntimeException("Verification Fail");
	// }
	// }
	// System.out.println("verification pass");
	// }

	private static HashMap<Integer, HashMap<Integer, SBSTopAsins>> analysisFileAgainstIog2GlMap(
			BufferedReader reader,
			HashMap<Integer, HashMap<Integer, TreeMap<String, AsinDetailsSBSFields>>> iog2glOrig,
			int threshold) throws IOException {
		HashMap<Integer, HashMap<Integer, SBSTopAsins>> iog2glResult = new HashMap<Integer, HashMap<Integer, SBSTopAsins>>(
				512);
		String asindetailsLine = reader.readLine();
		while ((asindetailsLine = reader.readLine()) != null) {
			AsinDetailLine asinFromLine = new AsinDetailLine(asindetailsLine);

			Integer iog = asinFromLine.getIOG();
			Integer gl = asinFromLine.getGl();

			AsinDetailsSBSFields asinSBS = getAsinDetailsSBSFromIog2GlMap(iog2glOrig, asinFromLine);
			asinSBS.sbsAsin = AsinDetailsSBSFields.getInstance(asinFromLine);
			HashMap<Integer, SBSTopAsins> gl2TopAsin = iog2glResult.get(iog);
			if (gl2TopAsin == null) {
				gl2TopAsin = new HashMap<Integer, SBSTopAsins>(512);
				iog2glResult.put(iog, gl2TopAsin);
			}
			SBSTopAsins sbsTipAsin = gl2TopAsin.get(gl);
			if (sbsTipAsin == null) {
				sbsTipAsin = new SBSTopAsins(threshold);
				gl2TopAsin.put(gl, sbsTipAsin);
			}
			sbsTipAsin.addTopAsin(asinSBS);
		}
		return iog2glResult;
	}

	private static void outputSBSResult(
			HashMap<Integer, HashMap<Integer, SBSTopAsins>> iog2glSBSResult, PrintWriter output) {
		String iog = null;
		String gl = null;
		String asin = null;
		String totalInventoryOrig = null;
		String totalInventoryNew = null;
		String totalUnhealthyOrig = null;
		String totalUnhealthyNew = null;
		String tipOrig = null;
		String tipNew = null;
		String cartonOrig = null;
		String cartonNew = null;
		String diff = null;

		String format = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";
		for (Integer iogKey : iog2glSBSResult.keySet()) {
			output.println("ASIN,GL,IOG,Diff,Total Inventory Orig,Total Inventory New,Total Unhealthy Orig,Total Unhealthy New,TIP Orig,TIP New,Carton Orig,Carton New");
			HashMap<Integer, SBSTopAsins> gl2TopAsin = iog2glSBSResult.get(iogKey);
			iog = String.valueOf(iogKey);
			for (Integer glKey : gl2TopAsin.keySet()) {
				gl = String.valueOf(glKey);
				SBSTopAsins tipAsins = gl2TopAsin.get(glKey);
				for (Entry<Integer, LinkedList<AsinDetailsSBSFields>> entry : tipAsins.topAsins
						.entrySet()) {
					diff = String.valueOf(entry.getKey());
					LinkedList<AsinDetailsSBSFields> asins = entry.getValue();
					for (AsinDetailsSBSFields asinSBS : asins) {
						asin = asinSBS.asin;
						totalInventoryOrig = String.valueOf(asinSBS.totalInventory);
						totalInventoryNew = String.valueOf(asinSBS.sbsAsin.totalInventory);
						totalUnhealthyOrig = String.valueOf(asinSBS.totalUnhealthy);
						totalUnhealthyNew = String.valueOf(asinSBS.sbsAsin.totalInventory);
						tipOrig = String.valueOf(asinSBS.tip);
						tipNew = String.valueOf(asinSBS.sbsAsin.tip);
						cartonOrig = String.valueOf(asinSBS.carton);
						cartonNew = String.valueOf(asinSBS.sbsAsin.carton);
						String line = String.format(format, asin, gl, iog, diff,
								totalInventoryOrig, totalInventoryNew, totalUnhealthyOrig,
								totalUnhealthyNew, tipNew, tipOrig, cartonOrig, cartonNew);
						output.println(line);
					}
				}
			}
		}
		output.flush();
		output.close();
	}

	private static AsinDetailsSBSFields getAsinDetailsSBSFromIog2GlMap(
			HashMap<Integer, HashMap<Integer, TreeMap<String, AsinDetailsSBSFields>>> iog2gl,
			AsinDetailLine asindetail) {
		Integer iog = asindetail.getIOG();
		Integer gl = asindetail.getGl();
		String asin = asindetail.getAsin();

		HashMap<Integer, TreeMap<String, AsinDetailsSBSFields>> gl2asin = iog2gl.get(iog);

		if (gl2asin == null) {
			return null;
		}

		TreeMap<String, AsinDetailsSBSFields> asin2AsinSBS = gl2asin.get(gl);

		if (asin2AsinSBS == null) {
			return null;
		}

		return asin2AsinSBS.get(asin);

	}

	private static HashMap<Integer, HashMap<Integer, TreeMap<String, AsinDetailsSBSFields>>> buildTree(
			BufferedReader reader) throws IOException {
		long start = System.currentTimeMillis();
		int count = 0;

		HashMap<Integer, HashMap<Integer, TreeMap<String, AsinDetailsSBSFields>>> iog2gl = new HashMap<Integer, HashMap<Integer, TreeMap<String, AsinDetailsSBSFields>>>(
				512);
		String asindetailsLine = reader.readLine();
		AsinDetailsSBSFields temp = null;
		while ((asindetailsLine = reader.readLine()) != null) {
			AsinDetailLine asinFromLine = new AsinDetailLine(asindetailsLine);
			Integer iog = asinFromLine.getIOG();
			Integer gl = asinFromLine.getGl();
			String asin = asinFromLine.getAsin();

			HashMap<Integer, TreeMap<String, AsinDetailsSBSFields>> gl2asin = iog2gl.get(iog);
			if (gl2asin == null) {
				gl2asin = new HashMap<Integer, TreeMap<String, AsinDetailsSBSFields>>(512);
				iog2gl.put(iog, gl2asin);
			}
			TreeMap<String, AsinDetailsSBSFields> asin2AsinSBS = gl2asin.get(gl);
			if (asin2AsinSBS == null) {
				asin2AsinSBS = new TreeMap<String, AsinDetailsSBSFields>();
				gl2asin.put(gl, asin2AsinSBS);
			}

			if (temp == null) {
				temp = AsinDetailsSBSFields.getInstance(asinFromLine);
			} else {
				temp.fillInstance(asinFromLine);
			}
			temp = asin2AsinSBS.put(asin, temp);

			count++;
			if (count % 100000 == 0) {
				System.out.println("Build Tree Processed " + count + " lines...");
			}

			if (count == 2000000) {
				System.out.println("Time consumed:" + ((System.currentTimeMillis() - start) / 1000)
						+ "s");
				System.exit(0);
			}

		}

		System.out.println("Time consumed:" + ((System.currentTimeMillis() - start) / 1000) + "s");

		summerizeIog2GlMap(iog2gl);

		return iog2gl;
	}

	private static void summerizeIog2GlMap(
			HashMap<Integer, HashMap<Integer, TreeMap<String, AsinDetailsSBSFields>>> iog2gl) {
		int count = 0;
		for (Integer i : iog2gl.keySet()) {
			System.out.println("IOG:" + i);
			System.out.println("GL, Asin Count");
			HashMap<Integer, TreeMap<String, AsinDetailsSBSFields>> gl2asin = iog2gl.get(i);
			for (Integer j : gl2asin.keySet()) {
				TreeMap<String, AsinDetailsSBSFields> asin2AsinSBS = gl2asin.get(j);
				System.out.print(j + ", ");
				System.out.print(asin2AsinSBS.size());
				count += asin2AsinSBS.size();
			}
		}
		System.out.println();

		System.out.println("Total Group count of IOG-GL-ASIN:" + count);

	}
}

class SBSTopAsins {

	int topNumber;

	TreeMap<Integer, LinkedList<AsinDetailsSBSFields>> topAsins = new TreeMap<Integer, LinkedList<AsinDetailsSBSFields>>();

	public SBSTopAsins(int topNumber) {
		this.topNumber = topNumber;
	}

	public boolean addTopAsin(AsinDetailsSBSFields asinSBS) {
		int diff = asinSBS.getUnhealthyDiff();
		if (topAsins.size() == topNumber) {
			int miniDiff = topAsins.firstKey();
			if (diff < miniDiff) {
				return false;
			} else if (diff == miniDiff) {
				topAsins.get(miniDiff).add(asinSBS);
				return true;
			} else if (diff > miniDiff) {
				topAsins.remove(miniDiff);
			}
		}
		LinkedList<AsinDetailsSBSFields> diffList = topAsins.get(diff);
		if (diffList == null) {
			diffList = new LinkedList<AsinDetailsSBSFields>();
			topAsins.put(diff, diffList);
		}
		diffList.add(asinSBS);
		return true;
	}
}

class AsinDetailsSBSFields {
	public static AsinDetailsSBSFields getInstance(AsinDetailLine asindetail) {
		AsinDetailsSBSFields ret = new AsinDetailsSBSFields();
		ret.fillInstance(asindetail);
		return ret;
	}

	public void fillInstance(AsinDetailLine asindetail) {
		asin = asindetail.getAsin();
		carton = asindetail.getCartonQuantity();
		tip = asindetail.getTargetInventory();
		totalInventory = asindetail.getTotalInventory();
		totalUnhealthy = asindetail.getTotalUnhealthy();
	}

	String asin;
	int totalInventory;
	int totalUnhealthy;
	int carton;
	int tip;

	AsinDetailsSBSFields sbsAsin;

	public int getUnhealthyDiff() {
		return Math.abs(this.totalUnhealthy - sbsAsin.totalUnhealthy);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asin == null) ? 0 : asin.hashCode());
		result = prime * result + carton;
		result = prime * result + tip;
		result = prime * result + totalInventory;
		result = prime * result + totalUnhealthy;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AsinDetailsSBSFields other = (AsinDetailsSBSFields) obj;
		if (asin == null) {
			if (other.asin != null)
				return false;
		} else if (!asin.equals(other.asin))
			return false;
		if (carton != other.carton)
			return false;
		if (tip != other.tip)
			return false;
		if (totalInventory != other.totalInventory)
			return false;
		if (totalUnhealthy != other.totalUnhealthy)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AsinDetailsSBSFields [asin=" + asin + ", totalInventory=" + totalInventory
				+ ", totalUnhealthy=" + totalUnhealthy + ", carton=" + carton + ", tip=" + tip
				+ "]";
	}

}

class AsinDetailLine {
	public static final int Asin = 0;
	public static final int Iog = 1;
	public static final int Gl = 2;
	public static final int Target_Inventory_Level = 3;
	public static final int Total_Inventory = 4;
	public static final int Weekly_Forecast_Demand = 5;
	public static final int Weekly_Cptl_Holding_Cost = 6;
	public static final int One_Week_Historic_Demand = 7;
	public static final int Two_Weeks_Historic_Demand = 8;
	public static final int Three_Weeks_Historic_Demand = 9;
	public static final int Four_Weeks_Historic_Demand = 10;
	public static final int One_Year_Historic_Demand = 11;
	public static final int Our_Price = 12;
	public static final int Cost_Used_For_Calculations = 13;
	public static final int Vendor_Cost = 14;
	public static final int Retail_Contribution = 15;
	public static final int Return_Contribution = 16;
	public static final int Title_Description = 17;
	public static final int Cube = 18;
	public static final int Publication_Date = 19;
	public static final int Release_Date = 20;
	public static final int Sort_Type = 21;
	public static final int Upc = 22;
	public static final int Is_Unprep_Required = 23;
	public static final int Allocated_Inventory = 24;
	public static final int In_Process_Inventory = 25;
	public static final int Unsellable_Inventory = 26;
	public static final int Warehouse = 27;
	public static final int Vendor = 28;
	public static final int Order_Type = 29;
	public static final int Removal_Type = 30;
	public static final int Weekly_Fc_Holding_Cost = 31;
	public static final int Fc_Receipt_Cost = 32;
	public static final int Fc_Removal_Cost = 33;
	public static final int Removal_Amount = 34;
	public static final int Total_Savings = 35;
	public static final int Unhealthy_Quantity = 36;
	public static final int Healthy_Quantity = 37;
	public static final int Dsi_Id = 38;
	public static final int Receipt_Date = 39;
	public static final int Cannot_Return_Before = 40;
	public static final int Must_Return_Before = 41;
	public static final int Do_Id = 42;
	public static final int Ds_Id = 43;
	public static final int Do_Date = 44;
	public static final int Ds_Date = 45;
	public static final int Distributor_Price = 46;
	public static final int Distributor_Cost = 47;
	public static final int Exclusion_Reason = 48;
	public static final int Excluded_Vendor = 49;
	public static final int Excluded_Order_Type = 50;
	public static final int Excluded_Removal_Type = 51;
	public static final int Excluded_Removal_Amount = 52;
	public static final int Excluded_Total_Savings = 53;
	public static final int Excluded_Unhealthy_Quantity = 54;
	public static final int Excluded_Dsi_Id = 55;
	public static final int Excluded_Receipt_Date = 56;
	public static final int Excluded_Cannot_Return_Before = 57;
	public static final int Excluded_Must_Return_Before = 58;
	public static final int Excluded_Do_Id = 59;
	public static final int Excluded_Ds_Id = 60;
	public static final int Excluded_Do_Date = 61;
	public static final int Excluded_Ds_Date = 62;
	public static final int Excluded_Distributor_Price = 63;
	public static final int Excluded_Distributor_Cost = 64;
	public static final int Category = 65;
	public static final int Subcategory = 66;
	public static final int Markdown_Quantity = 67;
	public static final int Markdown_Duration = 68;
	public static final int Markdown_Price = 69;
	public static final int Markdown_Demand_Factor = 70;
	public static final int Elasticity = 71;
	public static final int Total_Healthy_Quantity = 72;
	public static final int Total_Unhealthy_Quantity = 73;
	public static final int Total_Healthy_Woc = 74;
	public static final int Total_Woc = 75;
	public static final int Warehouse_Quantity = 76;
	public static final int Is_Owoc = 77;
	public static final int Vendor_Name = 78;
	public static final int Ship_To_Name = 79;
	public static final int Ship_To_Address_Line_1 = 80;
	public static final int Ship_To_Address_Line_2 = 81;
	public static final int Ship_To_Address_Line_3 = 82;
	public static final int Ship_To_Address_City = 83;
	public static final int Ship_To_Address_State = 84;
	public static final int Ship_To_Address_Province = 85;
	public static final int Ship_To_Address_Postal_Code = 86;
	public static final int Ship_To_Address_Country_Code = 87;
	public static final int Ean = 88;
	public static final int List_Price = 89;
	public static final int Map_Price = 90;
	public static final int Is_Map_Required = 91;
	public static final int Publisher_Code = 92;
	public static final int Total_Retail_Healthy = 93;
	public static final int Total_Retail_Savings = 94;
	public static final int Cost_Used_For_Reporting = 95;
	public static final int Parent_Asin = 96;
	public static final int Replenishment_Category = 97;
	public static final int Child_Asin = 98;
	public static final int Bundle_Quantity = 99;
	public static final int Is_Forced_Markdown = 100;
	public static final int Mean_Age = 101;
	public static final int Is_Deadwood = 102;
	public static final int Fcsku = 103;
	public static final int Expiration_Date = 104;
	public static final int Realm = 105;
	public static final int Is_Authorization_Required = 106;
	public static final int Carton_Quantity = 107;
	public static final int Forecast1 = 108;
	public static final int Forecast2 = 109;
	public static final int Forecast3 = 110;
	public static final int Forecast4 = 111;
	public static final int Forecast5 = 112;
	public static final int Forecast6 = 113;
	public static final int Forecast7 = 114;
	public static final int Forecast8 = 115;
	public static final int Forecast9 = 116;
	public static final int Forecast10 = 117;
	public static final int Forecast11 = 118;
	public static final int Forecast12 = 119;
	public static final int Forecast13 = 120;
	public static final int Forecast14 = 121;
	public static final int Forecast15 = 122;
	public static final int Forecast16 = 123;
	public static final int Forecast17 = 124;
	public static final int Forecast18 = 125;
	public static final int Forecast19 = 126;
	public static final int Forecast20 = 127;
	public static final int Forecast21 = 128;
	public static final int Forecast22 = 129;
	public static final int Forecast23 = 130;
	public static final int Forecast24 = 131;
	public static final int Forecast25 = 132;
	public static final int Forecast26 = 133;
	public static final int Forecast27 = 134;
	public static final int Forecast28 = 135;
	public static final int Forecast29 = 136;
	public static final int Forecast30 = 137;
	public static final int Forecast31 = 138;
	public static final int Forecast32 = 139;
	public static final int Forecast33 = 140;

	String[] row;

	public AsinDetailLine(String line) {
		this(line.split(","));
	}

	public AsinDetailLine(String[] row) {
		for (int i = 0; i < row.length; i++) {
			// This is needed to avoid OOO
			row[i] = new String(row[i]);
		}
		this.row = row;
	}

	public void clear() {
		for (int i = 0; i < row.length; i++) {
			// help GC
			row[i] = null;
		}
	}

	public int getIOG() {
		return Integer.parseInt(row[Iog]);
	}

	public int getGl() {
		return Integer.parseInt(row[Gl]);
	}

	public String getAsin() {
		return row[Asin];
	}

	public String getCategory() {
		return row[Category];
	}

	public String getSubcategory() {
		return row[Subcategory];
	}

	public int getTotalInventory() {
		return getRealIntFromString(row[Total_Inventory]);
	}

	public int getTotalUnhealthy() {
		return getRealIntFromString(row[Total_Unhealthy_Quantity]);
	}

	public int getTotalHealthy() {
		return getRealIntFromString(row[Total_Healthy_Quantity]);
	}

	public int getTargetInventory() {
		return getRealIntFromString(row[Target_Inventory_Level]);
	}

	public int getCartonQuantity() {
		return getRealIntFromString(row[Carton_Quantity]);
	}

	private int getRealIntFromString(String str) {
		return Math.round(Float.parseFloat(str));
	}
}
