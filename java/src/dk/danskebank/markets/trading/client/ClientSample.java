package dk.danskebank.markets.trading.client;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;

import java.util.LinkedList;

public class ClientSample implements IClientContract {

	private LinkedList<Double> lastThreePrices = new LinkedList<>();
	private LinkedList<Double> lastTenPrices = new LinkedList<>();
	private double profit = 0;
	private double boughtAt;
	private double lastThreeAverage;
	private double lastTenAverage;
	private boolean alreadyBought;

	@Override
	public String getStrategyName() {
		return "XelNika";
	}

	@Override
	public TradeAction tick(double price) {
		calcAverages(price);
		if (alreadyBought) {
			if (lastThreeAverage < lastTenAverage && boughtAt < price) {
				sell(price);
			}

			if (boughtAt > price && price > lastTenAverage) {
				sell(price);
			}
		}
		else {
			if (lastThreeAverage > lastTenAverage && price < lastThreeAverage) {
				buy(price);
			}
		}
		return TradeAction.DO_NOTHING;
	}

	public double getProfit() {
		return profit;
	}

	public TradeAction buy(double price){
		alreadyBought = true;
		profit -= price;
		boughtAt = price;
		return TradeAction.BUY;
	}

	public TradeAction sell(double price) {
		alreadyBought = false;
		profit += price;
		return TradeAction.SELL;
	}

	public void calcAverages(double price) {
		if (lastThreePrices.size() > 2) {
			lastThreePrices.removeFirst();
			if (lastTenPrices.size() > 9) {
				lastTenPrices.removeFirst();
			}
		}
		lastTenPrices.add(price);
		lastThreePrices.add(price);

		double temp = 0;
		for (double p : lastTenPrices) {
			temp += p;
		}
		lastTenAverage = temp / lastTenPrices.size();

		temp = 0;
		for (double p : lastThreePrices) {
			temp += p;
		}
		lastThreeAverage = temp / lastThreePrices.size();
	}
}