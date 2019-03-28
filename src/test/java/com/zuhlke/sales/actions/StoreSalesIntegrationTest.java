package com.zuhlke.sales.actions;

import com.zuhlke.sales.entities.Sale;
import com.zuhlke.sales.entities.SaleBuilder;
import com.zuhlke.sales.persistence.MySQLSalesRepository;
import com.zuhlke.sales.persistence.SalesRepository;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StoreSalesIntegrationTest
{

    @Test
    public void whenStoreSalesThenSalesCanBeRetrievedFromTheRepository()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        LocalDate orderDate = LocalDate.parse("08.11.16", formatter);
        LocalDate shipDate = LocalDate.parse("11.11.16", formatter);

        Sale sale =  new SaleBuilder().withOrderID("CA-2016-152156").withOrderDate(orderDate).withShipDate(shipDate)
                .withShipMode("Second Class").withQuantity(5).withDiscount(new BigDecimal("0.45"))
                .withProfit(new BigDecimal(41.9136)).withProductID("FUR-BO-10001798").withCustomerName("Claire Gute")
                .withCategory("Furniture").withCustomerID("CG-12520").build();

        List<Sale> sales = new ArrayList();
        sales.add(sale);
        SalesRepository repository = new MySQLSalesRepository();

        new StoreSales(repository).store(sales);

        List<Sale> storedSales = repository.getSales();
        Sale firstSale = storedSales.get(0);

        assertSaleFields(storedSales, firstSale);

    }

    private void assertSaleFields(List<Sale> storedSales, Sale firstSale) {

        BigDecimal expectedProfit = new BigDecimal("41.91");
        expectedProfit.setScale(2, RoundingMode.CEILING);

        BigDecimal expectedSales = new BigDecimal("261.96");
        expectedSales.setScale(2, RoundingMode.CEILING);
        BigDecimal expectedDiscount = new BigDecimal("0.45");
        expectedDiscount.setScale(0, RoundingMode.CEILING);


        Assert.assertEquals(1, storedSales.size());
        Assert.assertEquals("CA-2016-152156", firstSale.getOrderID());
        Assert.assertEquals("2016-11-08", firstSale.getOrderDate().toString());
        Assert.assertEquals("2016-11-11", firstSale.getShipDate().toString());
        Assert.assertEquals("Second Class", firstSale.getShipMode());
        Assert.assertEquals(new Integer(5), firstSale.getQuantity());
        Assert.assertEquals(expectedDiscount, firstSale.getDiscount());
        Assert.assertEquals(expectedProfit, firstSale.getProfit());
        Assert.assertEquals("FUR-BO-10001798", firstSale.getProductID());
        Assert.assertEquals("Claire Gute", firstSale.getCustomerName());
        Assert.assertEquals("Furniture", firstSale.getCategory());
        Assert.assertEquals("CG-12520", firstSale.getCustomerID());
    }

}
