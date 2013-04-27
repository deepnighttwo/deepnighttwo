

def printIndex():
    asindetailsfile = file("asindetails.csv")
    head = asindetailsfile.readline()
    head = head.strip()
    colNames = head.split(",")
    i = 0;
    for colName in colNames:
        print "public static final int", colName.title(), "=", i, ";"
        i += 1


def printFieldName():
    asindetailsfile = file("asindetails.csv")
    head = asindetailsfile.readline()
    head = head.strip()
    colNames = head.split(",")
    for colName in colNames:
        print "public static final String", colName.title() + "_Str=\"" + colName.title() + "\";"
        
        
def pritnMap():
    asindetailsfile = file("asindetails.csv")
    head = asindetailsfile.readline()
    head = head.strip()
    colNames = head.split(",")
    i = 0;
    print "static Map<String,Integer> map = new HashMap<String,Integer>(", len(colNames), ");"

    print "static{"
    for colName in colNames:
        colName = colName.strip().title()
        colName = "\"" + colName + "\""
        
        print "map.put(", colName, ",", i, ");"
        i += 1
    print "}"
        
def pritnMapi2s():
    asindetailsfile = file("asindetails.csv")
    head = asindetailsfile.readline()
    head = head.strip()
    colNames = head.split(",")
    i = 0;
    print "Map<String,Integer> map = new HashMap<String,Integer>(", len(colNames), ");"


    print "static{"
    for colName in colNames:
        colName = colName.strip().title()
        colName = "\"" + colName + "\""
        
        print "map.put(", i, ",", colName.replace("_", " "), ");"
        i += 1
    print "}"

def printEnum():
    asindetailsfile = file("asindetails.csv")
    head = asindetailsfile.readline()
    head = head.strip()
    colNames = head.split(",")
    i = 0
    for colName in colNames:
        colName = colName.strip()
        print colName.title().replace("_", ""), "(", i, ",", "\"" + colName + "\"", ",", "\"" + colName.title().replace("_"," ") + "\"),"
        i += 1;


if __name__ == "__main__":
     #pritnMap()
     #pritnMapi2s()
     #title = "asin,iog,gl,target_inventory_level,total_inventory,weekly_forecast_demand,weekly_cptl_holding_cost,one_week_historic_demand,two_weeks_historic_demand,three_weeks_historic_demand,four_weeks_historic_demand,one_year_historic_demand,our_price,cost_used_for_calculations,vendor_cost,retail_contribution,return_contribution,title_description,cube,publication_date,release_date,sort_type,upc,is_unprep_required,allocated_inventory,in_process_inventory,unsellable_inventory,warehouse,vendor,order_type,removal_type,weekly_fc_holding_cost,fc_receipt_cost,fc_removal_cost,removal_amount,total_savings,unhealthy_quantity,healthy_quantity,dsi_id,receipt_date,cannot_return_before,must_return_before,do_id,ds_id,do_date,ds_date,distributor_price,distributor_cost,exclusion_reason,excluded_vendor,excluded_order_type,excluded_removal_type,excluded_removal_amount,excluded_total_savings,excluded_unhealthy_quantity,excluded_dsi_id,excluded_receipt_date,excluded_cannot_return_before,excluded_must_return_before,excluded_do_id,excluded_ds_id,excluded_do_date,excluded_ds_date,excluded_distributor_price,excluded_distributor_cost,category,subcategory,markdown_quantity,markdown_duration,markdown_price,markdown_demand_factor,elasticity,total_healthy_quantity,total_unhealthy_quantity,total_healthy_woc,total_woc,warehouse_quantity,is_owoc,vendor_name,ship_to_name,ship_to_address_line_1,ship_to_address_line_2,ship_to_address_line_3,ship_to_address_city,ship_to_address_state,ship_to_address_province,ship_to_address_postal_code,ship_to_address_country_code,ean,list_price,map_price,is_map_required,publisher_code,total_retail_healthy,total_retail_savings,cost_used_for_reporting,parent_asin,replenishment_category,child_asin,bundle_quantity,is_forced_markdown,mean_age,is_deadwood,fcsku,expiration_date,realm,is_authorization_required,carton_quantity,forecast1,forecast2,forecast3,forecast4,forecast5,forecast6,forecast7,forecast8,forecast9,forecast10,forecast11,forecast12,forecast13,forecast14,forecast15,forecast16,forecast17,forecast18,forecast19,forecast20,forecast21,forecast22,forecast23,forecast24,forecast25,forecast26,forecast27,forecast28,forecast29,forecast30,forecast31,forecast32,forecast33".title().replace('_',' ')
     #print title
     #printFieldName()
     printEnum();
