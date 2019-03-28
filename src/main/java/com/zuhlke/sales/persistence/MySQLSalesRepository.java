package com.zuhlke.sales.persistence;

import com.zuhlke.sales.entities.Sale;
import com.zuhlke.sales.entities.SaleBuilder;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySQLSalesRepository implements SalesRepository{

    static Connection crunchifyConn = null;

    public MySQLSalesRepository(){
        makeJDBCConnection();
    }

    private void makeJDBCConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try {

            crunchifyConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zuhlke", "root", "");
            if (crunchifyConn != null) {
                System.out.println("Connection Successful! Enjoy. Now it's time to push data");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println("MySQL Connection Failed!");
            e.printStackTrace();
            return;
        }
    }

    @Override
    public List<Sale> getSales() {
        String selectQueryStatement = "Select * from STORE_ORDER ";
        List<Sale> sales = new ArrayList<>();
        try {

            PreparedStatement crunchifyPrepareStat = crunchifyConn.prepareStatement(selectQueryStatement);
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
                String insertQueryStatement = "INSERT  INTO  STORE_ORDER (ORDER_ID,ORDER_DATE,SHIP_DATE,SHIP_MODE,QUANTITY,DISCOUNT,PROFIT,PRODUCT_ID,CUSTOMER_NAME,CATEGORY,CUSTOMER_ID) VALUES  (?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement crunchifyPrepareStat = crunchifyConn.prepareStatement(insertQueryStatement);
                crunchifyPrepareStat.setString(1, sale.getOrderID());
                crunchifyPrepareStat.setDate(2, java.sql.Date.valueOf(sale.getOrderDate()));
                crunchifyPrepareStat.setDate(3, java.sql.Date.valueOf(sale.getShipDate()));
                crunchifyPrepareStat.setString(4, sale.getShipMode());
                crunchifyPrepareStat.setInt(5, sale.getQuantity());
                crunchifyPrepareStat.setBigDecimal(6, sale.getDiscount());
                crunchifyPrepareStat.setBigDecimal(7, sale.getProfit());
                crunchifyPrepareStat.setString(8, sale.getProductID());
                crunchifyPrepareStat.setString(9, sale.getCustomerName());
                crunchifyPrepareStat.setString(10, sale.getCategory());
                crunchifyPrepareStat.setString(11, sale.getCustomerID());

                crunchifyPrepareStat.executeUpdate();
                crunchifyPrepareStat.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
