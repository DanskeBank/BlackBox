package dk.danskebank.markets.trading.client;

import java.util.LinkedList;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;

public class ClientSample implements IClientContract {

	@Override 
	public String getStrategyName() {
		return "THE ALGORITHM OF NORDEA"; 
	} 
  
	double prevPrice = 0;
	
	@Override 
	public TradeAction tick(double price) {
		if(price > prevPrice) {
			prevPrice = price;
			return TradeAction.BUY;			
		}
		else {
			prevPrice = price;
			return TradeAction.SELL;			
		}
  
	}
} 
