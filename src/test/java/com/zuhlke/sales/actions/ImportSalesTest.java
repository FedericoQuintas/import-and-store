package com.zuhlke.sales.actions;

import com.zuhlke.sales.entities.Sale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ImportSalesTest {

    private ImportSales importSales;
    private BigDecimal expectedProfit;
    private BigDecimal expectedSales;

    @Before
    public void beforeEach() {
        importSales = new ImportSales();

        expectedProfit = new BigDecimal("41.9136");
        expectedProfit.setScale(4, RoundingMode.CEILING);

        expectedSales = new BigDecimal("261.96");
        expectedSales.setScale(4, RoundingMode.CEILING);
    }

    @Test
    public void whenImportSalesFromOneFileThenTheyAreRetrievedWithAllTheFields()
    {

        String[] files = {"src/test/resources/sales1-test.csv"};

        List<Sale> sales = importSales.importFromCsv(files);

        Sale firstSale = sales.get(0);

        Assert.assertEquals(2, sales.size());
        Assert.assertEquals("CA-2016-152156", firstSale.getOrderID());
        Assert.assertEquals("2016-11-08", firstSale.getOrderDate().toString());
        Assert.assertEquals("2016-11-11", firstSale.getShipDate().toString());
        Assert.assertEquals("Second Class", firstSale.getShipMode());
        Assert.assertEquals(new Integer(2), firstSale.getQuantity());
        Assert.assertEquals(new BigDecimal(0), firstSale.getDiscount());
        Assert.assertEquals(expectedProfit, firstSale.getProfit());
        Assert.assertEquals("FUR-BO-10001798", firstSale.getProductID());
        Assert.assertEquals("Claire Gute", firstSale.getCustomerName());
        Assert.assertEquals("Furniture", firstSale.getCategory());
        Assert.assertEquals("CG-12520", firstSale.getCustomerID());
    }

    @Test
    public void whenImportSalesFromTwoFilesThenTheyAreRetrievedWithAllTheFields()
    {

        String[] files = {"src/test/resources/sales1-test.csv", "src/test/resources/sales2-test.csv"};

        List<Sale> sales = importSales.importFromCsv(files);

        Sale firstSaleFromSecondFile = sales.get(2);

        BigDecimal expectedDiscount = new BigDecimal("0.45");
        expectedDiscount.setScale(2, RoundingMode.CEILING);

        Assert.assertEquals(5, sales.size());
        Assert.assertEquals("US-2015-108966", firstSaleFromSecondFile.getOrderID());
        Assert.assertEquals("2016-11-08", firstSaleFromSecondFile.getOrderDate().toString());
        Assert.assertEquals("2016-11-11", firstSaleFromSecondFile.getShipDate().toString());
        Assert.assertEquals("Standard Class", firstSaleFromSecondFile.getShipMode());
        Assert.assertEquals(new Integer(2), firstSaleFromSecondFile.getQuantity());
        Assert.assertEquals(expectedDiscount, firstSaleFromSecondFile.getDiscount());
        Assert.assertEquals(expectedProfit, firstSaleFromSecondFile.getProfit());
        Assert.assertEquals("FUR-BO-10001798", firstSaleFromSecondFile.getProductID());
        Assert.assertEquals("Sean O'Donnell", firstSaleFromSecondFile.getCustomerName());
        Assert.assertEquals("Furniture", firstSaleFromSecondFile.getCategory());
        Assert.assertEquals("SO-20335", firstSaleFromSecondFile.getCustomerID());
    }

}
