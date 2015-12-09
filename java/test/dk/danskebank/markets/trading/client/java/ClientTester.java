package dk.danskebank.markets.trading.client.java;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import dk.danskebank.blackbox.client.IClientContract;
import dk.danskebank.markets.trading.client.ClientSample;
import dk.danskebank.markets.trading.client.ClientSample2;
import dk.danskebank.markets.trading.client.ClientSample3;

public class ClientTester {

//	private static ClientSample sample;
//	private static ClientSample2 sample; //so far the most suitable one
	private static ClientSample3 sample;
	
	
	@BeforeClass
    public static void init() {
	//	sample = new ClientSample();
	//	sample=new ClientSample2();
		sample=new ClientSample3();
		
		
      System.out.println("Init");
      
    }
	
	
	@Test
	public void testNameNotNull() {
		String name = sample.getStrategyName();
		assertNotNull(name);
	}
	
	@Test
	public void testNameNotToShort() {
		String name = sample.getStrategyName();
		if(name.length() < 4)
			fail("Strategyname must be at least 4 chars long");
	}
	
	@Test
	public void testStrategy() {
		try {
			Random rnd = new Random(123);
			sample.tick(0.001);
			for(int i=0 ; i < 10_000; i++) {
				sample.tick(rnd.nextDouble()*50);
			}
			
			System.out.println("Profit:" +sample.getProfit());
		} catch(Exception e) {
			fail("Exception happen while streaming prices:"+e.getMessage());
		}
	}
	
	@Test
	public void testSlowDecisions() {
		
		try {
			Random rnd = new Random(123);
			sample.tick(0.001);
			long start = System.nanoTime();
			
			for(int i=0 ; i < 10_000; i++) {
			//	sample.tick(rnd.nextDouble()*100000);	
				sample.tick(rnd.nextDouble()*50);		
			}
			long end = System.nanoTime();
			
			if(end-start > 10_000_000_000L) {
				fail("You are spending to much time analysing the market!");
			}
			
			System.out.println("Profit:" + sample.getProfit());
			
		} catch(Exception e) {
			fail("Exception happen while streaming prices:"+e.getMessage());
		}
	}
}
