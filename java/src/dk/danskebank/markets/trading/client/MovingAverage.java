package dk.danskebank.markets.trading.client;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;

public class MovingAverage implements IClientContract{

	private int ticksOfAverage;
	private int ticksForUpwardTrend;
	private FixedSizeQueue queue;
	private LinkedList<Double> movingAverage;
	
	
	public MovingAverage(int ticksOfAverage, int ticksForUpwardTrend) {
		this.ticksOfAverage = ticksOfAverage;
		this.ticksForUpwardTrend = ticksForUpwardTrend;
		this.queue = new FixedSizeQueue(ticksOfAverage);
		movingAverage = new LinkedList<Double>();
	}
	
	
	@Override
	public String getStrategyName() {
		return "MovingAverage-" + ticksOfAverage;
	}

	@Override
	public TradeAction tick(double price) {
		queue.add(price);
		if (!queue.isFull()) return TradeAction.DO_NOTHING;
		
		movingAverage.addFirst(getMovingAverage());
		if (movingAverage.size() < ticksForUpwardTrend) return TradeAction.DO_NOTHING;
		
		if (hasUpwardTrend()) return TradeAction.BUY;
		return TradeAction.SELL;
		
		
	}
	
	private boolean hasUpwardTrend() {		
		return movingAverage.getFirst() > movingAverage.get(ticksForUpwardTrend-1);
	}


	private Double getMovingAverage() {
		double res = 0;
		for (double d : queue.getInternalList()) {
			res += d;
		}
		return res/queue.getInternalList().size();
	}


	private class FixedSizeQueue {
		private int size;
		private LinkedList<Double> internalList;
			
		public FixedSizeQueue (int size) {
			this.size = size;
			this.internalList = new LinkedList<Double>();
		}
		
		public void add(Double element) {
			if (internalList.size() < size) 
				internalList.addFirst(element);
			else {
				internalList.removeLast();
				internalList.addFirst(element);
			}
		}
		
		public boolean isFull() {
			return this.size == internalList.size();
		}

		public LinkedList<Double> getInternalList() {
			return internalList;
		}
	}
}
