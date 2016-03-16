package BlackBox.src.dk.danskebank.markets.trading.client;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;

public class ClientSample implements IClientContract {

	private double lastprice = 0;
	private boolean bought = false;
	private double pct;

	@Override 
	public String getStrategyName() {
		return "ThomasBOT";
	} 

	@Override 
	public TradeAction tick(double price) {
		pct = ((lastprice-price)/price)*100;
		if(pct < -5 && bought) {
			bought = false;
			lastprice = price;
			return TradeAction.SELL;
		}
		if(5 < pct && !bought) {
			bought = true;
			lastprice = price;
			return TradeAction.BUY;
		}
		lastprice = price;
		return TradeAction.DO_NOTHING;
	}
} 