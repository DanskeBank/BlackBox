using System;
using System.IO;
using StrategyTests.TestHelpers;
using Formatting = System.Xml.Formatting;
using BlackBox;
using SampleStrategy;
using System.Linq;

namespace StrategyTests
{
    class StrategyTest
    {
        // This is for testing purposes only, write your code in Strategy.cs and test it here :)
        // You will recieve output with the amount of buys, sells and do nothings, your strategy made.
        public static void Main()
        {

            string _jsonRoot = Environment.CurrentDirectory + "\\" + "Data";
            Directory.CreateDirectory(Environment.CurrentDirectory + "\\" + "Data");

            var jsonFiles = Directory.GetFiles(_jsonRoot, "*.json");
            foreach (var jsonFilePath in jsonFiles)
            {
                var jsonContent = File.ReadAllText(jsonFilePath);
                var jsonName = Path.GetFileNameWithoutExtension(jsonFilePath);
                var dataProvider = new JsonMarketDataProvider(jsonName, jsonContent);

                var prices = dataProvider.GetPrices().OrderBy(p => p.Date).ToList();
                IStrategy _strategy = new Strategy();

                int Buys = 0;
                int Sells = 0;
                int DoNothings = 0;

                var tradingPrices = prices.Select(price =>
                {
                    price.Action = _strategy.Run(price.Value);
                    switch (price.Action)
                    {
                        case TradeAction.Buy:
                            Buys++;
                            break;
                        case TradeAction.DoNothing:
                            DoNothings++;
                            break;
                        case TradeAction.Sell:
                            Sells++;
                            break;
                    }
                    price.Value = decimal.Round(decimal.Round(price.Value, 2) + 0.001m, 2);
                    return price;
                }).ToList();
                Console.WriteLine($"Processing {_strategy.UniqueName} on {jsonName} prices ...");
                Console.WriteLine("Bought {0} times", Buys);
                Console.WriteLine("Did nothing {0} times", DoNothings);
                Console.WriteLine("Sold {0} times\n", Sells);
                
                
            }
            Console.Write("\nPlease press a key to close this window");
            Console.ReadKey();
        }
    }
}
