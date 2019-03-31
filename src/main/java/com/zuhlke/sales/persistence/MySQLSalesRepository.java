package com.zuhlke.sales.persistence;

import com.zuhlke.sales.entities.Sale;
import com.zuhlke.sales.entities.SaleBuilder;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySQLSalesRepository implements SalesRepository{

    static Connection connection = null;

    public MySQLSalesRepository(Connection conn){
        connection = conn;
    }

    @Override
    public List<Sale> getSales() {
        String selectQueryStatement = "Select * from STORE_ORDER";
        List<Sale> sales = new ArrayList<>();
        try {

            PreparedStatement crunchifyPrepareStat = connection.prepareStatement(selectQueryStatement);
            ResultSet rs = crunchifyPrepareStat.executeQuery();

            while(rs.next()){
                String orderID = rs.getString("ORDER_ID");
                LocalDate orderDate = rs.getDate("ORDER_DATE").toLocalDate();
                LocalDate shipDate = rs.getDate("SHIP_DATE").toLocalDate();
                String shipMode = rs.getString("SHIP_MODE");
                Integer quantity = rs.getInt("QUANTITY");
                BigDecimal discount = rs.getBigDecimal("DISCOUNT");
                BigDecimal profit = rs.getBigDecimal("PROFIT");
                String productID = rs.getString("PRODUCT_ID");
                String customerName = rs.getString("CUSTOMER_NAME");
                String category = rs.getString("CATEGORY");
                String customerID = rs.getString("CUSTOMER_ID");

                sales.add(new SaleBuilder().withOrderID(orderID).withOrderDate(orderDate).withShipDate(shipDate)
                .withShipMode(shipMode).withQuantity(quantity).withDiscount(discount).withProfit(profit)
                .withProductID(productID).withCustomerName(customerName).withCategory(category)
                .withCustomerID(customerID).build());

            }

            crunchifyPrepareStat.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return sales;
    }

    @Override
    public void store(List<Sale> sales) {
        for(Sale sale: sales) {

            try {
                String insertQueryStatement = "INSERT  INTO  STORE_ORDER " +
                        "(ORDER_ID,ORDER_DATE,SHIP_DATE,SHIP_MODE,QUANTITY,DISCOUNT,PROFIT,PRODUCT_ID,CUSTOMER_NAME,CATEGORY,CUSTOMER_ID)" +
                        " VALUES  (?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(insertQueryStatement);
                preparedStatement.setString(1, sale.getOrderID());
                preparedStatement.setDate(2, java.sql.Date.valueOf(sale.getOrderDate()));
                preparedStatement.setDate(3, java.sql.Date.valueOf(sale.getShipDate()));
                preparedStatement.setString(4, sale.getShipMode());
                preparedStatement.setInt(5, sale.getQuantity());
                preparedStatement.setBigDecimal(6, sale.getDiscount());
                preparedStatement.setBigDecimal(7, sale.getProfit());
                preparedStatement.setString(8, sale.getProductID());
                preparedStatement.setString(9, sale.getCustomerName());
                preparedStatement.setString(10, sale.getCategory());
                preparedStatement.setString(11, sale.getCustomerID());

                preparedStatement.executeUpdate();
                preparedStatement.close();
                System.out.println("Stored sale with Order ID: " + sale.getOrderID());
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
