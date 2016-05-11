package dk.danskebank.markets.trading.client;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;

import java.util.ArrayList;
import java.util.Collections;

public class ClientSample implements IClientContract {
	private ArrayList<Double> previousPrices = new ArrayList<>();
	private ArrayList<Double> unSortedPrices = new ArrayList<>();
	private boolean hasStock = false;
	private int count = 0;
	private int memory = 100;

	@Override 
	public String getStrategyName() {
		return "MedianIsWin";
	} 
  
	@Override 
	public TradeAction tick(double price) {
		if (previousPrices.isEmpty()) {
			previousPrices.add(price);
			unSortedPrices.add(price);
			hasStock = true;
			count++;
			return TradeAction.BUY;
		}

		if (price > previousPrices.get(previousPrices.size()/2)) {
			if (count >= memory) {
				previousPrices.remove(unSortedPrices.get(count - memory));
			}
			count++;
			previousPrices.add(price);
			unSortedPrices.add(price);
			Collections.sort(previousPrices);
			if (hasStock) {
				if (price > previousPrices.get(previousPrices.size()/2) * 1.0) {
					hasStock = false;
					return TradeAction.SELL;
				}
			}
			return TradeAction.DO_NOTHING;

		}
		if (count >= memory) {
			previousPrices.remove(unSortedPrices.get(count - memory));
		}
		count++;
		previousPrices.add(price);
		unSortedPrices.add(price);
		Collections.sort(previousPrices);
		if (hasStock) {
			return TradeAction.DO_NOTHING;
		}
		hasStock = true;
		return TradeAction.BUY;
	}
} 