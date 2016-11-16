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
		return "jonGameWin";
	}
	
	// Checks current position
	boolean hasPosition;
	
	// Previous EMA values
	private double shortEma;
	private double longEma;
	
	// Tick limit for starting EMA calculations
	private final static int shortPeriod = 13;
	private final static int longPeriod = 49;
	
	// Array for holding initial values
	private double[] initShortArr = new double[shortPeriod];
	private double[] initLongArr = new double[longPeriod];

	public TradeAction Run(double price) {			
		// Check if starting value is set
		if (shortEma == 0) {
			int shortTick = 0;
			double sum = 0;
			for (int i = 0; i < initShortArr.length; i++) {
				if (initShortArr[i] == 0) {
					initShortArr[i] = price;
					break;
				}
				sum += initShortArr[i];
				shortTick++;
			}
			
			if (shortTick == shortPeriod) {
				shortEma = sum / shortPeriod;
			}	
		} else {			
			// Calculate short EMA
			double alpha = 2 / ((double) shortPeriod + 1);
			shortEma = (price - shortEma) * alpha + shortEma;
		}
		
		// Check if starting value is set
		if (longEma == 0) {
			int longTick = 0;
			double sum = 0;
			for (int i = 0; i < initLongArr.length; i++) {
				if (initLongArr[i] == 0) {
					initLongArr[i] = price;
					break;
				}
				sum += initLongArr[i];
				longTick++;
			}
			
			if (longTick == longPeriod) {
				longEma = sum / longPeriod;
			}	
		} else {
			// Calculate long EMA, compensate for half step
			double alpha = 2 / ((double) longPeriod + 0.5);
			longEma = (price - longEma) * alpha + longEma;
		}
		
		// Buy if short moving average is above long moving average, sell if opposite
		if (shortEma > longEma && longEma != 0) {		
			// Check if currently has the position
			if (hasPosition) {
				return TradeAction.DoNothing;
			} else {
				hasPosition = true;
				return TradeAction.Buy;
			}
		} else if (shortEma < longEma) {
			// Check if currently has the position
			if (hasPosition) {
				hasPosition = false;
				return TradeAction.Sell;
			} else {
				return TradeAction.DoNothing;
			}
		} else {
			return TradeAction.DoNothing;
		}
	}
}
