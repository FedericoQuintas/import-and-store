package com.zuhlke;

import com.zuhlke.sales.actions.ImportSales;
import com.zuhlke.sales.actions.StoreSales;
import com.zuhlke.sales.persistence.MySQLSalesRepository;

public class App
{
    public static void main( String[] args )
    {
        new StoreSalesApplication(new StoreSales(new MySQLSalesRepository()), new ImportSales()).run(args);
    }
}
