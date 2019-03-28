package com.zuhlke.sales.entities;


import java.math.BigDecimal;
import java.time.LocalDate;

public class Sale {

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

    public Sale(String orderID, LocalDate orderDate, LocalDate shipDate, String shipMode,
                Integer quantity, BigDecimal discount, BigDecimal profit, String productID,
                String customerName, String category, String customerID) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.shipDate = shipDate;
        this.shipMode = shipMode;
        this.quantity = quantity;
        this.discount = discount;
        this.profit = profit;
        this.productID = productID;
        this.customerName = customerName;
        this.category = category;
        this.customerID = customerID;
    }

    public String getOrderID() {
        return orderID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public LocalDate getShipDate() {
        return shipDate;
    }

    public String getShipMode() {
        return shipMode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public String getProductID() {
        return productID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCategory() {
        return category;
    }

    public String getCustomerID() {
        return customerID;
    }
}
