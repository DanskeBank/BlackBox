package dk.danskebank.markets.trading.client;

import java.util.ArrayList;
import java.util.List;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;

public class ClientSample implements IClientContract {

	
	int pricesToStore = 150;
	double margin = 0.005; 
			
	double profit = 0;
	int count = 0;
	double avarage;			
	boolean hasStock = false;
	double localMinPrice, localMaxPrice, buyPrice, minDiff = 0, maxDiff;
	
	
	List<Double> priceHistory = new ArrayList<Double>();
	
	@Override 
	public String getStrategyName() {
		return "Make It Rain! Version 1"; 
	}
  
	@Override 
	public TradeAction tick(double price) {
		count++;
		TradeAction ta = TradeAction.DO_NOTHING;
		
		this.priceHistory.add(price);
		if (this.priceHistory.size() > this.pricesToStore)
			this.priceHistory.remove(0);
		
		this.avarage = getAvarage(this.priceHistory);
	
		if (this.minDiff < this.avarage - price && this.avarage > price) {
			if (!this.hasStock) {
				ta = TradeAction.BUY;
				this.buyPrice = price;
				this.hasStock = true;
				this.profit -= price;
			}
		} else if ((this.avarage < this.buyPrice && this.hasStock && this.minDiff > this.avarage - price && this.avarage > price) || (this.avarage < price*(1+this.margin)) || (this.buyPrice*1.03 < price)) {
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
} 