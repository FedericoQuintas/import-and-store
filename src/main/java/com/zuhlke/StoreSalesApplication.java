package com.zuhlke;

import com.zuhlke.sales.actions.ImportSales;
import com.zuhlke.sales.actions.StoreSales;
import com.zuhlke.sales.entities.Sale;

import java.util.List;

public class StoreSalesApplication {

    private ImportSales importSales;
    private StoreSales storeSales;

    public StoreSalesApplication(StoreSales storeSales, ImportSales importSales) {
        this.storeSales = storeSales;
        this.importSales = importSales;
    }

    public void run(String[] args) {
        try {
            List<Sale> products = importSales.importFromCsv(args);

            storeSales.store(products);
        } catch (Exception ex){
            System.out.println("Process interrupted due to: " + ex.getMessage());
        }
    }
}
