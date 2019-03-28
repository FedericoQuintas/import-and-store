package com.zuhlke.sales.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SaleBuilder {

    private String orderID;
    private LocalDate orderDate;
    private LocalDate shipDate;
    private String shipMode;
    private Integer quantity;
    private BigDecimal discount;
    private BigDecimal profit;
    private String productID;
    private String customerName;
    private String category;
    private String customerID;

    public SaleBuilder withOrderID(String orderID) {
        this.orderID = orderID;
        return this;
    }

    public SaleBuilder withOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public SaleBuilder withShipDate(LocalDate shipDate) {
        this.shipDate = shipDate;
        return this;
    }

    public SaleBuilder withShipMode(String shipMode) {
        this.shipMode = shipMode;
        return this;
    }

    public SaleBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public SaleBuilder withDiscount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public SaleBuilder withProfit(BigDecimal profit) {
        this.profit = profit;
        return this;
    }

    public SaleBuilder withProductID(String productID) {
        this.productID = productID;
        return this;
    }

    public SaleBuilder withCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public SaleBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    public SaleBuilder withCustomerID(String customerID) {
        this.customerID = customerID;
        return this;
    }

    public Sale build() {
        return new Sale(this.orderID, this.orderDate, this.shipDate, this.shipMode, this.quantity,
                this.discount, this.profit, this.productID, this.customerName, this.category, this.customerID);
    }
}
