package dk.danskebank.markets.trading.client;

import java.util.LinkedList;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;


/*
 * This strategy involves remembering the history of N prices and not
 * the entire history. In theory this should be the most suitable one
 * for the profit. In practice, due to the Random function, it is not 
 * as efficient as ClientSample2
 * 
 * NOTE: This strategy should be benchmarked according to the list size.
 */

public class ClientSample3 implements IClientContract {

	private boolean hasStock = false;
	LinkedList<Double> priceList = new LinkedList<>();
	private double listSize = 100;
	private double profit = 0;

	@Override
	public String getStrategyName() {
		return "MyThirdStrategy";
	}
	

	@Override
	public TradeAction tick(double price) {

		if (!hasStock) {
			if (priceList.size() == listSize) {
				priceList.removeFirst();
				priceList.addLast(price);
			} else {
				priceList.addLast(price);
			}

			if (price < listAverage(priceList)) {
				profit = profit - price;
				hasStock = true;
				return TradeAction.BUY;
			} else
				return TradeAction.DO_NOTHING;

		} else {
			
			if(priceList.size() == listSize)
			{
				priceList.removeFirst();
				priceList.addLast(price);
			}
			else
			{
				priceList.addLast(price);
			}
			
			if(price > listAverage(priceList))
			{
				profit= profit + price;
				hasStock=false;
				return TradeAction.SELL;
			}
			else return TradeAction.DO_NOTHING;

		}
	}

	
	
	private double listAverage(LinkedList<Double> lst) {
		double sum = 0;

		for (int i = 0; i < lst.size(); i++) {
			sum += lst.get(i);
		}
		return sum / lst.size();
	}
	
	public double getProfit()
	{
		return profit;
	}
}
