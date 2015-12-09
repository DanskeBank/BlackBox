package dk.danskebank.markets.trading.client.java;

public class Test {

	public static void main(String[] args) {
		
		ClientTester ct = new ClientTester();
		ct.init();
		
		ct.testNameNotNull();
		ct.testNameNotToShort();
		
		//ct.testStrategy();
		
	    ct.testSlowDecisions();
	
	}

}
