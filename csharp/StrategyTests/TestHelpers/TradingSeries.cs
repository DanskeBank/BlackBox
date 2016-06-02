using System.Collections.Generic;

namespace StrategyTests.TestHelpers
{
    class TradingSeries
    {
        public string Ticker { get; set; }
        public List<PriceTrade> Prices { get; set; }
    }
}
