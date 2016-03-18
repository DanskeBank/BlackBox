package dk.danskebank.markets.trading.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;

public class ClientSample2 implements IClientContract {

	LinkedList<Double> priceList = new LinkedList<>();
	private double lastPrice = 0;
	private double diff = 0;	
	private double max = Double.MIN_VALUE, maxDiff = Double.MIN_VALUE;
	private double min = 0;
	private double average = 0;
	private List<Double> averageList = new ArrayList<>();
	private double lastBuy;
	
	int pricesToStore = 150;
	double margin = 0.005; 
			
	double profit = 0;
	int count = 0;
	double avarage;			
	boolean hasStock = false;
	double localMinPrice, localMaxPrice, buyPrice, minDiff = 0, maxDiff2;
	
	
	List<Double> priceHistory = new ArrayList<Double>();

	@Override
	public String getStrategyName() {
		return "MyThirdStrategy";
	}
	

	@Override
	public TradeAction tick(double price) {
		count++;
		TradeAction ta = TradeAction.DO_NOTHING;
		
		this.priceHistory.add(price);
		if (this.priceHistory.size() > this.pricesToStore)
			this.priceHistory.remove(0);
		
		this.avarage = getAvarage(this.priceHistory);
	
		if (this.minDiff < this.avarage - price && this.avarage > price && !this.hasStock) {
			ta = TradeAction.BUY;
			this.buyPrice = price;
			this.hasStock = true;
			this.profit -= price;
			this.lastBuy = price;
		} else if ((this.avarage < this.buyPrice && this.minDiff > this.avarage - price && this.avarage > price && this.hasStock) || (this.avarage < price*(1+this.margin) && this.hasStock) || (this.buyPrice*1.03 < price && this.hasStock)) {
			if(this.hasStock)
			ta = TradeAction.SELL;
			this.buyPrice = 0;
			this.hasStock = false;
			this.profit += price;
		}
		
		
		this.localMinPrice = getLocalMin(this.priceHistory);
		this.localMaxPrice = getLocalMax(this.priceHistory);

		this.minDiff = this.avarage - this.localMinPrice;
		
		return ta;
	}
	
	public double getAvarage(List<Double> list) {
		double sum = 0;
		for (Double item : list)
			sum += item;
		
		return sum / list.size();
	}
	
	public double getLocalMin(List<Double> list) {
		double min = list.get(0);
		
		for (Double item : list)
			if (item < min) 
				min = item;
		
		return min;
	}
	
public double getLocalMax(List<Double> list) {
	double max = list.get(0);
	
	for (Double item : list)
		if (item > max) 
			max = item;
	
	return max;
	}

	private boolean shouldSell(double value) {
		boolean sell = false;
		
		if (diff > maxDiff2) {
			maxDiff2 = diff;
			max = value;
			sell = true;
		}
		
		return sell;
	}


	private boolean shouldBuy(double value) {
		return value < average*0.90;
	}
	
	public double getLastBuy() {
		return lastBuy;
	}
	
	public double getProfit() {
		return this.profit;
	}
} 