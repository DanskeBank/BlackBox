using System;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace StrategyTests.TestHelpers
{

    public class JsonMarketDataProvider
    {
        readonly string _name;
        readonly TradingSeries _priceSeries;

        public JsonMarketDataProvider(string name, string json)
        {
            if (string.IsNullOrWhiteSpace(json))
            {
                throw new ArgumentNullException(nameof(json));
            }
            _name = name;
            _priceSeries = JsonConvert.DeserializeObject<TradingSeries>(json);
        }

        public string Name
        {
            get { return _name; }
        }

        public string InstrumentName
        {
            get { return _priceSeries.Ticker; }
        }

        public IEnumerable<PriceTrade> GetPrices()
        {
            return _priceSeries.Prices;
        }
    }

}
