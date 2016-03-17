package dk.danskebank.markets.trading.client;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;

public class ClientSample implements IClientContract {

	
	private decimal price_of_stock = 0;
	private decimal previous = 0;
	private decimal profit = price_of_stock - previous;
	private boolean hasBought = false;
	
	
	@Override 
	public String getStrategyName() {
		return "Xenchik_trade"; 
	} 
  
	@Override 
	public TradeAction tick(double price) {
		return TradeAction.BUY;  
		
		if(profit < 0){
			
		return TradeAction.SELL;}
		
		
		else if(profit > 0){
			
			
			return TradeAction.BUY;
		}
		
		return TradeAction.DO_NOTHING;
	
	}
} 