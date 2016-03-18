package dk.danskebank.markets.trading.client.java;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

import dk.danskebank.markets.trading.client.ClientSample;
import dk.danskebank.markets.trading.client.ClientSample2;

public class ClientTester {

	private static ClientSample sample;
	private static ClientSample2 sample2;

	@BeforeClass
    public static void init() {
		sample = new ClientSample();
		sample2 = new ClientSample2();
     
    }
	
	@Test
	public void testNameNotNull() {
		String name = sample.getStrategyName();
		String name2 = sample2.getStrategyName();
		assertNotNull(name);
	}
	
	@Test
	public void testNameNotToShort() {
		String name = sample.getStrategyName();
		String name2 = sample2.getStrategyName();
		if(name.length() < 4 && name2.length() < 4)
			fail("Strategyname must be at least 4 chars long");
	}
	
	@Test
	public void testStrategy() {
		try {
			
			Scanner s = new Scanner(new File("stockPrices2007_2008.txt"));
			String lastLine = "";
			while(s.hasNextLine()) {
				String line = s.nextLine();
				sample.tick(Double.parseDouble(line));
				sample2.tick(Double.parseDouble(line));
				lastLine = line;
			}
			
			System.out.println("Luggi:\tEnd profit = " + (sample.getProfit() + Double.parseDouble(lastLine)) );
			System.out.println("Seb:\tEnd profit = " + (sample2.getProfit() + Double.parseDouble(lastLine)) );
			
		} catch(Exception e) {
			fail("Exception happen while streaming prices:"+e.getMessage());
		}
	}
	
//	@Test
//	public void testSlowDecisions() {
//		
//		try {
//			Random rnd = new Random(123);
//			sample.tick(0.001);
//			long start = System.nanoTime();
//			for(int i=0 ; i < 10000; i++) {
//				sample.tick(rnd.nextDouble()*100000);			
//			}
//			long end = System.nanoTime();
//			
//			if(end-start > 10_000_000_000L) {
//				fail("You are spending to much time analysing the market!");
//			}
//			
//		} catch(Exception e) {
//			fail("Exception happen while streaming prices:"+e.getMessage());
//		}
//	}
}
