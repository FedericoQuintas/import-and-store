package com.zuhlke.sales.persistence;

import com.zuhlke.sales.entities.Sale;

import java.util.List;

public interface SalesRepository {
    List<Sale> getSales();

    void store(List<Sale> sales);
}
