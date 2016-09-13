package implementation;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import interfaces.IStrategy;
import interfaces.TradeAction;

public class Main {
	
	
	private static IStrategy strategy = new Strategy(); //OBS...	Change this to the corresponding name of your implementation.
	
	/*
	 * This is just for testing purposes to make sure your strategy is working as intended :)
	 * The strategy will be tested on market data and will output amount of time the strategy buys, does nothing, or sells.
	 * 
	 */
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Path currentRelativePath = Paths.get("");	

		String quotes = currentRelativePath.resolve("data").toAbsolutePath().toString();
 		
 		try{			
			Path path = Paths.get(quotes);
			DirectoryStream<Path> jsonStream = Files.newDirectoryStream(path);
			List<Path> pathsToJson = new ArrayList<Path>();
			JSONParser parser = new JSONParser();

			for(Path entry : jsonStream){
				Object obj = parser.parse(new FileReader(entry.toAbsolutePath().toString()));
				JSONObject jsonObject = (JSONObject) obj;			
				String ticker = (String) jsonObject.get("Ticker");
				System.out.println("Processing " + strategy.UniqueStrategyName() + " on " + ticker + "...");
				JSONArray prices =  (JSONArray) jsonObject.get("Prices");
				Iterator<JSONObject> iterator = prices.iterator();
				List<TradeAction> actions = new ArrayList<TradeAction>();
				int buys = 0;
				int doNothings = 0;
				int sells = 0;
				while(iterator.hasNext()){
					double price = (double) iterator.next().get("Value");
					TradeAction action = strategy.Run(price);
					switch(action){
						case Buy:
							buys++;
							break;
						case DoNothing:
							doNothings++;
							break;
						case Sell:
							sells++;
							break;
					}					
				}
				System.out.println("Bought " + buys + " times");
				System.out.println("DoNothing " + doNothings + " times");
				System.out.println("Sold " + sells + " times\n");
			}
			System.out.println("We are all done!");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}

}
