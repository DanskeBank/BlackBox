package implementation;

import interfaces.IStrategy;
import interfaces.TradeAction;
/*
 * Easy example of implements of the IStrategy :) 
 * IT IS IMPORTANT TO KEEP YOUR IMPLEMENTATION OF ISTRATEGY IN THIS PACKAGE, implementation, TOGETHERE WITH THE CLASSNAME STRATEGY
 * OTHERWISE DATA CAN NOT BE GENERATED ONCE THE STRATEGY IS SUBMITTED.
 * You have three choices specified in the TradeAction enum. These are Buy, DoNothing and Sell. 
 * You can buy at most one equity and sell one equity. Buying several times in a row is equal to buy one time and then DoNothing. The same things goes for selling.
 * Remember to test your strategy with Main.java before submitting.
 * For submitting export this java project as a jar:
 * 1. Right click project name.
 * 2. Export.
 * 3. Java -> Jar file
 * 4. Finish
 * 5. Submit the jar file
 * Good luck! :)  
 */
public class Strategy implements IStrategy {

	
	public String UniqueStrategyName() {
		return "JarNr2";
	}
	
	boolean hasPosition;

	public TradeAction Run(double price) {
		
		if(hasPosition){
			hasPosition = !hasPosition;
			return TradeAction.Buy;			
		}
		hasPosition = !hasPosition;
		return TradeAction.Sell;
	}

}
