package topasin.util;

public enum AsinFields {
    Asin(0, "asin", "Asin"), Iog(1, "iog", "Iog"), Gl(2, "gl", "Gl"), TargetInventoryLevel(3, "target_inventory_level",
            "Target Inventory Level"), TotalInventory(4, "total_inventory", "Total Inventory"), WeeklyForecastDemand(5,
            "weekly_forecast_demand", "Weekly Forecast Demand"), WeeklyCptlHoldingCost(6, "weekly_cptl_holding_cost",
            "Weekly Cptl Holding Cost"), OneWeekHistoricDemand(7, "one_week_historic_demand",
            "One Week Historic Demand"), TwoWeeksHistoricDemand(8, "two_weeks_historic_demand",
            "Two Weeks Historic Demand"), ThreeWeeksHistoricDemand(9, "three_weeks_historic_demand",
            "Three Weeks Historic Demand"), FourWeeksHistoricDemand(10, "four_weeks_historic_demand",
            "Four Weeks Historic Demand"), OneYearHistoricDemand(11, "one_year_historic_demand",
            "One Year Historic Demand"), OurPrice(12, "our_price", "Our Price"), CostUsedForCalculations(13,
            "cost_used_for_calculations", "Cost Used For Calculations"), VendorCost(14, "vendor_cost", "Vendor Cost"), RetailContribution(
            15, "retail_contribution", "Retail Contribution"), ReturnContribution(16, "return_contribution",
            "Return Contribution"), TitleDescription(17, "title_description", "Title Description"), Cube(18, "cube",
            "Cube"), PublicationDate(19, "publication_date", "Publication Date"), ReleaseDate(20, "release_date",
            "Release Date"), SortType(21, "sort_type", "Sort Type"), Upc(22, "upc", "Upc"), IsUnprepRequired(23,
            "is_unprep_required", "Is Unprep Required"), AllocatedInventory(24, "allocated_inventory",
            "Allocated Inventory"), InProcessInventory(25, "in_process_inventory", "In Process Inventory"), UnsellableInventory(
            26, "unsellable_inventory", "Unsellable Inventory"), Warehouse(27, "warehouse", "Warehouse"), Vendor(28,
            "vendor", "Vendor"), OrderType(29, "order_type", "Order Type"), RemovalType(30, "removal_type",
            "Removal Type"), WeeklyFcHoldingCost(31, "weekly_fc_holding_cost", "Weekly Fc Holding Cost"), FcReceiptCost(
            32, "fc_receipt_cost", "Fc Receipt Cost"), FcRemovalCost(33, "fc_removal_cost", "Fc Removal Cost"), RemovalAmount(
            34, "removal_amount", "Removal Amount"), TotalSavings(35, "total_savings", "Total Savings"), UnhealthyQuantity(
            36, "unhealthy_quantity", "Unhealthy Quantity"), HealthyQuantity(37, "healthy_quantity", "Healthy Quantity"), DsiId(
            38, "dsi_id", "Dsi Id"), ReceiptDate(39, "receipt_date", "Receipt Date"), CannotReturnBefore(40,
            "cannot_return_before", "Cannot Return Before"), MustReturnBefore(41, "must_return_before",
            "Must Return Before"), DoId(42, "do_id", "Do Id"), DsId(43, "ds_id", "Ds Id"), DoDate(44, "do_date",
            "Do Date"), DsDate(45, "ds_date", "Ds Date"), DistributorPrice(46, "distributor_price", "Distributor Price"), DistributorCost(
            47, "distributor_cost", "Distributor Cost"), ExclusionReason(48, "exclusion_reason", "Exclusion Reason"), ExcludedVendor(
            49, "excluded_vendor", "Excluded Vendor"), ExcludedOrderType(50, "excluded_order_type",
            "Excluded Order Type"), ExcludedRemovalType(51, "excluded_removal_type", "Excluded Removal Type"), ExcludedRemovalAmount(
            52, "excluded_removal_amount", "Excluded Removal Amount"), ExcludedTotalSavings(53,
            "excluded_total_savings", "Excluded Total Savings"), ExcludedUnhealthyQuantity(54,
            "excluded_unhealthy_quantity", "Excluded Unhealthy Quantity"), ExcludedDsiId(55, "excluded_dsi_id",
            "Excluded Dsi Id"), ExcludedReceiptDate(56, "excluded_receipt_date", "Excluded Receipt Date"), ExcludedCannotReturnBefore(
            57, "excluded_cannot_return_before", "Excluded Cannot Return Before"), ExcludedMustReturnBefore(58,
            "excluded_must_return_before", "Excluded Must Return Before"), ExcludedDoId(59, "excluded_do_id",
            "Excluded Do Id"), ExcludedDsId(60, "excluded_ds_id", "Excluded Ds Id"), ExcludedDoDate(61,
            "excluded_do_date", "Excluded Do Date"), ExcludedDsDate(62, "excluded_ds_date", "Excluded Ds Date"), ExcludedDistributorPrice(
            63, "excluded_distributor_price", "Excluded Distributor Price"), ExcludedDistributorCost(64,
            "excluded_distributor_cost", "Excluded Distributor Cost"), Category(65, "category", "Category"), Subcategory(
            66, "subcategory", "Subcategory"), MarkdownQuantity(67, "markdown_quantity", "Markdown Quantity"), MarkdownDuration(
            68, "markdown_duration", "Markdown Duration"), MarkdownPrice(69, "markdown_price", "Markdown Price"), MarkdownDemandFactor(
            70, "markdown_demand_factor", "Markdown Demand Factor"), Elasticity(71, "elasticity", "Elasticity"), TotalHealthyQuantity(
            72, "total_healthy_quantity", "Total Healthy Quantity"), TotalUnhealthyQuantity(73,
            "total_unhealthy_quantity", "Total Unhealthy Quantity"), TotalHealthyWoc(74, "total_healthy_woc",
            "Total Healthy Woc"), TotalWoc(75, "total_woc", "Total Woc"), WarehouseQuantity(76, "warehouse_quantity",
            "Warehouse Quantity"), IsOwoc(77, "is_owoc", "Is Owoc"), VendorName(78, "vendor_name", "Vendor Name"), ShipToName(
            79, "ship_to_name", "Ship To Name"), ShipToAddressLine1(80, "ship_to_address_line_1",
            "Ship To Address Line 1"), ShipToAddressLine2(81, "ship_to_address_line_2", "Ship To Address Line 2"), ShipToAddressLine3(
            82, "ship_to_address_line_3", "Ship To Address Line 3"), ShipToAddressCity(83, "ship_to_address_city",
            "Ship To Address City"), ShipToAddressState(84, "ship_to_address_state", "Ship To Address State"), ShipToAddressProvince(
            85, "ship_to_address_province", "Ship To Address Province"), ShipToAddressPostalCode(86,
            "ship_to_address_postal_code", "Ship To Address Postal Code"), ShipToAddressCountryCode(87,
            "ship_to_address_country_code", "Ship To Address Country Code"), Ean(88, "ean", "Ean"), ListPrice(89,
            "list_price", "List Price"), MapPrice(90, "map_price", "Map Price"), IsMapRequired(91, "is_map_required",
            "Is Map Required"), PublisherCode(92, "publisher_code", "Publisher Code"), TotalRetailHealthy(93,
            "total_retail_healthy", "Total Retail Healthy"), TotalRetailSavings(94, "total_retail_savings",
            "Total Retail Savings"), CostUsedForReporting(95, "cost_used_for_reporting", "Cost Used For Reporting"), ParentAsin(
            96, "parent_asin", "Parent Asin"), ReplenishmentCategory(97, "replenishment_category",
            "Replenishment Category"), ChildAsin(98, "child_asin", "Child Asin"), BundleQuantity(99, "bundle_quantity",
            "Bundle Quantity"), IsForcedMarkdown(100, "is_forced_markdown", "Is Forced Markdown"), MeanAge(101,
            "mean_age", "Mean Age"), IsDeadwood(102, "is_deadwood", "Is Deadwood"), Fcsku(103, "fcsku", "Fcsku"), ExpirationDate(
            104, "expiration_date", "Expiration Date"), Realm(105, "realm", "Realm"), IsAuthorizationRequired(106,
            "is_authorization_required", "Is Authorization Required"), CartonQuantity(107, "carton_quantity",
            "Carton Quantity"), Forecast1(108, "forecast1", "Forecast1"), Forecast2(109, "forecast2", "Forecast2"), Forecast3(
            110, "forecast3", "Forecast3"), Forecast4(111, "forecast4", "Forecast4"), Forecast5(112, "forecast5",
            "Forecast5"), Forecast6(113, "forecast6", "Forecast6"), Forecast7(114, "forecast7", "Forecast7"), Forecast8(
            115, "forecast8", "Forecast8"), Forecast9(116, "forecast9", "Forecast9"), Forecast10(117, "forecast10",
            "Forecast10"), Forecast11(118, "forecast11", "Forecast11"), Forecast12(119, "forecast12", "Forecast12"), Forecast13(
            120, "forecast13", "Forecast13"), Forecast14(121, "forecast14", "Forecast14"), Forecast15(122,
            "forecast15", "Forecast15"), Forecast16(123, "forecast16", "Forecast16"), Forecast17(124, "forecast17",
            "Forecast17"), Forecast18(125, "forecast18", "Forecast18"), Forecast19(126, "forecast19", "Forecast19"), Forecast20(
            127, "forecast20", "Forecast20"), Forecast21(128, "forecast21", "Forecast21"), Forecast22(129,
            "forecast22", "Forecast22"), Forecast23(130, "forecast23", "Forecast23"), Forecast24(131, "forecast24",
            "Forecast24"), Forecast25(132, "forecast25", "Forecast25"), Forecast26(133, "forecast26", "Forecast26"), Forecast27(
            134, "forecast27", "Forecast27"), Forecast28(135, "forecast28", "Forecast28"), Forecast29(136,
            "forecast29", "Forecast29"), Forecast30(137, "forecast30", "Forecast30"), Forecast31(138, "forecast31",
            "Forecast31"), Forecast32(139, "forecast32", "Forecast32"), Forecast33(140, "forecast33", "Forecast33");

    private final int index;
    private final String name;
    private final String title;

    AsinFields(int index, String name, String title) {
        this.index = index;
        this.name = name;
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public static AsinFields getAsinDetailFieldByIndex(int index) {
        for (AsinFields asinFields : AsinFields.values()) {
            if (asinFields.index == index) {
                return asinFields;
            }
        }
        throw new IllegalArgumentException(TopAsinUtil.ASINFIELDS_NOT_FOUND + ":index - " + index);
    }

    public static AsinFields getAsinDetailFieldByName(String name) {
        for (AsinFields asinFields : AsinFields.values()) {
            if (asinFields.name.equalsIgnoreCase(name)) {
                return asinFields;
            }
        }
        throw new IllegalArgumentException(TopAsinUtil.ASINFIELDS_NOT_FOUND + ":name - " + name);
    }

    public static int getAsinFieldsCount() {
        return AsinFields.values().length;
    }

}
