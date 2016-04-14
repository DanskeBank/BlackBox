#DBBlackbox

This folder contains a sample eclipse project on how to implement a solution for the Danske Bank Blackbox challenge.
Besides a library containing the interface needed to be defined and the tradeaction types, some unit tests have also been included. 

##Getting started?
The interface IClientContract contains two methods. One which is returning the name of you trading strategy named "getStrategyName", and another named "tick" which is handling if you should buy or sell or do nothing based on the price tick you receive.
When participating in the competition Danske Bank can decide to run different stock prices. For instance one run could be based on Microsoft stocks but another could be based on Nokia or something completely different.
What is important is that your algorithm is generic enough to handle such changes.
Your algorithm decisions doesn't affect the market!

##sample code
```java
package dk.danskebank.markets.trading.client;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;

public class ClientSample implements IClientContract {

	@Override 
	public String getStrategyName() {
		return "Sample"; 
	} 
  
	@Override 
	public TradeAction tick(double price) {
		return TradeAction.SELL;   
	}
} 
```
In this example and simple algorithm is shown. It's strategy is to always Sell, and the strategy is identified as "Sample".
