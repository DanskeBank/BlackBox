package dk.danskebank.markets.trading.client;

import java.util.LinkedList;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;

public class ClientSample implements IClientContract {
	
	private LinkedList<Double> tenDays = new LinkedList<>();
	private boolean ownsStock = false;

	@Override 
	public String getStrategyName() {
		return "HartoftFiveTenDaysAverageCompare"; 
	} 
  
	@Override 
	public TradeAction tick(double price) {
		tenDays.addLast(price);
		if (tenDays.size() < 10){ // Ten days have not yet past
			return TradeAction.DO_NOTHING;
		}
		if (tenDays.size() > 10){ // Only keep last 10 days in list
			tenDays.removeFirst();
		}
		double tenDayAvg, fiveDayAvg;
		double tenDaySum = 0;
		double fiveDaySum = 0;
		for (int i = 0; i < 10; i++){
			if (i > 4){ // Do sum for last 5 days
				fiveDaySum += tenDays.get(i);
			}
			tenDaySum += tenDays.get(i);
		}
		tenDayAvg = tenDaySum / 10;
		fiveDayAvg = fiveDaySum / 5;
		if (fiveDayAvg > tenDayAvg && !ownsStock){
			ownsStock = true;
			return TradeAction.BUY;
		}else if (fiveDayAvg < tenDayAvg && ownsStock){
			ownsStock = false;
			return TradeAction.SELL;
		}
		return TradeAction.DO_NOTHING;
	}
} 