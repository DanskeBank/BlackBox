package dk.danskebank.markets.trading.client.java;

import dk.danskebank.blackbox.client.TradeAction;
import dk.danskebank.markets.trading.client.ClientSample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Madss on 01-05-2016.
 */
public class Tester {


    public static void main(String[] args) {
        ClientSample cs = new ClientSample();
        ArrayList<Double> data = new ArrayList<>(parseCSV());
        double buyingPrice = 0;
        double profit = 0;

        for (double price : data) {
            System.out.println(price);
            TradeAction response = cs.tick(price);

            if (response.equals(TradeAction.BUY)) {
                System.out.println("Buy");
                buyingPrice = price;
            }
            if (response.equals(TradeAction.SELL)) {
                profit += price - buyingPrice;
                System.out.println("Sell " + profit);
            }

        }
        System.out.println(profit);
    }

    public static ArrayList<Double> parseCSV() {
        ArrayList<Double> stockPrices = new ArrayList<>();
        String csvFile = "/Users/Madss/Downloads/vestas2.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            br.readLine();
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] stock = line.split(cvsSplitBy);
                stockPrices.add(0,Double.parseDouble(stock[6]));
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
        return stockPrices;
    }
}
