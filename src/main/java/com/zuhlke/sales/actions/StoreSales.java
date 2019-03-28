package com.zuhlke.sales.actions;

import com.zuhlke.sales.entities.Sale;
import com.zuhlke.sales.persistence.SalesRepository;

import java.util.List;

public class StoreSales {

    private final SalesRepository repository;

    public StoreSales(SalesRepository repository) {
        this.repository = repository;
    }

    public void store(List<Sale> sales) {
        repository.store(sales);
    }
}
