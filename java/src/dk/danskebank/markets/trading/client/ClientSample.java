package dk.danskebank.markets.trading.client;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;

/*
 * This strategy is an a simple one: always sell the stock when the
 * price in the pipeline is bigger than the price I bought it.
 */

public class ClientSample implements IClientContract {

	private boolean hasStock = false;
	private double strockPrice = 0.0;
	private double profit = 0.0;

	@Override
	public String getStrategyName() {
		return "MyStrategy";
	}

	@Override
	public TradeAction tick(double price) {
	
		if (!hasStock) {		
			hasStock = true;
			strockPrice = price;
			profit = profit - price;
			return TradeAction.BUY;
		}

		else {
			if (strockPrice < price) {
				
				hasStock = false;
				profit = profit + price;
				strockPrice = 0.0;
				
				return TradeAction.SELL;
			} else {
				return TradeAction.DO_NOTHING;
			}

		}

	}
	
	public double getProfit()
	{
		return profit;
	}
	
	public double getStock()
	{
		return strockPrice;
	}

}