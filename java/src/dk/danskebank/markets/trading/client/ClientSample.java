package dk.danskebank.markets.trading.client;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;

import java.util.ArrayList;
import java.util.Collections;

public class ClientSample implements IClientContract {
	private ArrayList<Double> previousPrices = new ArrayList<>();

	@Override 
	public String getStrategyName() {
		return "MedianIsWin";
	} 
  
	@Override 
	public TradeAction tick(double price) {
		if (previousPrices.isEmpty()) {
			previousPrices.add(price);
			return TradeAction.BUY;
		}
		if (price > previousPrices.get(previousPrices.size()/2)) {
			previousPrices.add(price);
			Collections.sort(previousPrices);
			return TradeAction.SELL;
		}
		previousPrices.add(price);
		Collections.sort(previousPrices);
		return TradeAction.BUY;
	}
} 