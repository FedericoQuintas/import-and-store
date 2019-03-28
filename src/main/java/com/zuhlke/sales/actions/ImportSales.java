package com.zuhlke.sales.actions;

import com.zuhlke.sales.entities.Sale;
import com.zuhlke.sales.entities.SaleBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ImportSales {

    public List<Sale> importFromCsv(String[] files) {
        List<Sale> sales = new ArrayList<Sale>();
        for (String file : files) {
            readFile(file, sales);
        }
        return sales;
    }

    private void readFile(String file, List<Sale> sales) {
        String csvFile = file;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] sale = line.split(cvsSplitBy);
                LocalDate orderDate = LocalDate.parse(sale[1], formatter);
                LocalDate shipDate = LocalDate.parse(sale[2], formatter);

                Sale createdSale = new SaleBuilder().withOrderID(sale[0]).withOrderDate(orderDate).withShipDate(shipDate)
                        .withShipMode(sale[3]).withCustomerID(sale[4]).withCustomerName(sale[5])
                        .withProductID(sale[12]).withCategory(sale[13])
                        .withQuantity(Integer.valueOf(sale[17])).withDiscount(new BigDecimal(sale[18]))
                        .withProfit(new BigDecimal(sale[19])).build();

                sales.add(createdSale);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
