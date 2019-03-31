package com.zuhlke;

import com.zuhlke.sales.actions.ImportSales;
import com.zuhlke.sales.actions.StoreSales;
import com.zuhlke.sales.persistence.MySQLSalesRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App
{
    public static void main( String[] args )
    {
        new StoreSalesApplication(new StoreSales(new MySQLSalesRepository(makeJDBCConnection("jdbc:mysql://localhost:3306/zuhlke"))), new ImportSales()).run(args);
    }


    private static Connection makeJDBCConnection(String databaseURL) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(databaseURL, "root", "");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("MySQL Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }
}
