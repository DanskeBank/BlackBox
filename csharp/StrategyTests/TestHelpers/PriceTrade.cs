using System;
using BlackBox;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace StrategyTests.TestHelpers
{
    public class PriceTrade
    {
        public DateTime Date { get; set; }
        public decimal Value { get; set; }
        [JsonConverter(typeof(StringEnumConverter))]
        public TradeAction Action { get; set; }
    }
}
