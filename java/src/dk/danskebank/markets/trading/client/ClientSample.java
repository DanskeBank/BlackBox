package dk.danskebank.markets.trading.client;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;

public class ClientSample implements IClientContract {

	public Queue<Double> priceQ10 = new LinkedList<Double>();
	public Queue<Double> priceQ60 = new LinkedList<Double>();

	LinkedList c;
	double profit=0.0;
	double ma10before=0.0;
	double ma10after=0;
	double ma60before=0;
	double ma60after=0;	
	
	int mark=0;//-1 : last order is buy; 1: last order is sell;
	
	@Override 
	public String getStrategyName() {
		return "Sample"; 
	} 
  
	@Override 
	public TradeAction tick(double price) {
		//System.out.println("The stock present price is ");
		//System.out.println(price);
		
		//10 days moving average
		ma10before=average(priceQ10);
		
		if (priceQ10.size()<10)
		{
			priceQ10.offer(price);
		}
		if(priceQ10.size()==10)
		{
			priceQ10.poll();
			priceQ10.offer(price);		
		}
		
		 ma10after=average(priceQ10);
		 
		// 60 days moving average
		ma60before=average(priceQ60);
		if (priceQ60.size()<60)
		{
			priceQ60.offer(price);
		}
		if(priceQ60.size()==60)
		{
			priceQ60.poll();
			priceQ60.offer(price);		
		}
		ma60after=average(priceQ60);
			
		//buy when its 10 day moving average goes above the 60 day moving average
		if((ma10before < ma60before) & (ma10after > ma60after))
		{
			profit = profit - price;
			//System.out.println("buy");
			//System.out.println("profit is");
			//System.out.println(profit);
			if(mark==1){
				mark=-1;
				return TradeAction.BUY;
			}
			else 
				return TradeAction.DO_NOTHING;
			
		}
		//buy when its 10 day moving average goes below the 60 day moving average
		if((ma10before > ma60before) & (ma10after < ma60after))
		{
			profit = profit + price;
			//System.out.println("sell");
			//System.out.println("profit is");
			//System.out.println(profit);
			if(mark==-1){
				mark=1;
				return TradeAction.SELL;
			}
			else 
				return TradeAction.DO_NOTHING;
				
		}
		else
		{
			return TradeAction.DO_NOTHING;			
		}

	}
	
	public double average(Queue q){
		int size = q.size();
		c= new LinkedList(q);//clone of queue q
		if (size == 0)
		{
			c.offer(0.0);
			size=size+1;
		}		
		double sum = 0;
		for(int i=0;i<size;i++)
		{
			double headElement=(double) c.poll();
			sum=sum + headElement;
		}
		double average = sum/size;
		return average;	
	}
} 