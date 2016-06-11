package interfaces;

/*
 * This is the interface to implement.
 * 
 */
public interface IStrategy {
	
	public String UniqueStrategyName();
	
	public TradeAction Run(double price);

}
