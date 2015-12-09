package dk.danskebank.markets.trading.client.java;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.blackbox.client.TradeAction;
import dk.danskebank.markets.trading.client.ClientSample;
import dk.danskebank.markets.trading.client.MovingAverage;

public class ClientTester {

	
	private static IClientContract client;

	@BeforeClass
    public static void init() {
		client = new MovingAverage(15, 5);
      System.out.println("Init");
    }
	
	@Test
	public void testNameNotNull() {
		String name = client.getStrategyName();
		assertNotNull(name);
	}
	
	@Test
	public void testNameNotToShort() {
		String name = client.getStrategyName();
		if(name.length() < 4)
			fail("Strategyname must be at least 4 chars long");
	}
	
	@Test
	public void testStrategy() {
		try {
			Random rnd = new Random(123);
			client.tick(0.001);
			for(int i=0 ; i < 10000; i++) {
				client.tick(rnd.nextDouble()*50);			
			}
		} catch(Exception e) {
			fail("Exception happen while streaming prices:"+e.getMessage());
		}
	}
	
	@Test
	public void testSlowDecisions() {
		
		try {
			Random rnd = new Random(123);
			client.tick(0.001);
			long start = System.nanoTime();
			for(int i=0 ; i < 10000; i++) {
				client.tick(rnd.nextDouble()*100000);			
			}
			long end = System.nanoTime();
			
			if(end-start > 10_000_000_000L) {
				fail("You are spending to much time analysing the market!");
			}
			
		} catch(Exception e) {
			fail("Exception happen while streaming prices:"+e.getMessage());
		}
	}
	
	@Test 
	public void sampleDataTest() {
		double [] prices = new double [] 
				{10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
				10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
				10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
				10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
				11, 12, 13, 14, 15, 16, 17, 17.3, 17.4, 17.5,
				18.5, 19.5, 20.5, 21.5, 22.5, 23.3, 19, 18, 23, 25,
				21, 19, 18, 15, 13, 10, 8, 7, 5, 15, 20, 25, 35, 42, 12, 21};
		
		for (double price: prices) {
			TradeAction action = client.tick(price);
			if (action.equals(TradeAction.DO_NOTHING)){
				System.out.println("Doing nothing");
				continue;
			}
			if (action.equals(TradeAction.BUY)) System.out.println("BUY");
			else System.out.println("SELL");
		}
	}
	
}
