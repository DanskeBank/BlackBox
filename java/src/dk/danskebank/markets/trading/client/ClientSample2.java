package dk.danskebank.markets.trading.client;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;

/*
 * This strategy involves comparing the stock price with an
 * average of all stock I have in my history. If the price is smaller than
 * my average, I buy it; if the price is bigger than my average i sell it
 * 
 * NOTE: I take into account the entire history
 */


public class ClientSample2 implements IClientContract {

	private boolean hasStock = false;
	private double profit = 0.0;
	private double average = 0.0;
	private double count = 0.0;
	private double sum = 0.0;


	@Override
	public String getStrategyName() {
		return "MySecondStrategy";
	}

	@Override
	public TradeAction tick(double price) {

		if (!hasStock) {
			sum = sum + price;
			count++;
			average = sum / count;

			if (price < average) {
				profit = profit - price;
				hasStock = true;
				return TradeAction.BUY;
			} else
				return TradeAction.DO_NOTHING;

		}

		else  {
			sum = sum + price;
			count++;
			average = sum / count;
			if (price > average) {
				profit = profit + price;
				hasStock = false;
				return TradeAction.SELL;
			}
			else
				return TradeAction.DO_NOTHING;
		} 
		
	}

	public double getProfit() {
		return profit;
	}

}
