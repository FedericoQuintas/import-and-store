package com.zuhlke;

import com.zuhlke.sales.actions.ImportSales;
import com.zuhlke.sales.actions.StoreSales;
import com.zuhlke.sales.entities.Sale;
import com.zuhlke.sales.entities.SaleBuilder;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class StoresSalesApplicationTest {

    private ImportSales importSalesMock;
    private StoreSales storeSalesMock;
    private StoreSalesApplication storeSalesApplication;

    @Before
    public void before() {
        importSalesMock = mock(ImportSales.class);
        storeSalesMock = mock(StoreSales.class);
        storeSalesApplication = new StoreSalesApplication(storeSalesMock, importSalesMock);
    }

    @Test
    public void whenApplicationRunsThenImportSalesActionIsCalledWithCorrectPaths()
    {
        String[] files = {"file1", "file2"};
        storeSalesApplication.run(files);

        verify(importSalesMock).importFromCsv(files);
    }

    @Test
    public void whenApplicationRunsThenStoreSalesActionIsCalled()
    {

        List<Sale> expectedSales = new ArrayList();
        Sale sale =  new SaleBuilder().withOrderID("CA-2016-152156").withOrderDate(null).withShipDate(null)
                .withShipMode("Second Class").withQuantity(5).withDiscount(new BigDecimal(0))
                .withProfit(new BigDecimal(41.9136)).withProductID("FUR-BO-10001798").withCustomerName("Clair Gute")
                .withCategory("Furniture").withCustomerID("CG-12520").build();
        expectedSales.add(sale);

        when(importSalesMock.importFromCsv(new String[]{})).thenReturn(expectedSales);

        storeSalesApplication.run(new String[]{});

        verify(storeSalesMock).store(expectedSales);

    }

}
