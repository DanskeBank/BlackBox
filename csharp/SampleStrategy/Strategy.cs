namespace SampleStrategy
{
    using System;
    using BlackBox;
    using System.ComponentModel.Composition;

    [Export(typeof(IStrategy))]
    public class Strategy : IStrategy
    {
        public string UniqueName
        {
            get
            {
                return "TSP";
            }
        }

        private decimal previous1 = 0;
        private decimal previous2 = 0;

        /// <summary>
        /// Implement this method with your trading strategy
        /// You always buy one equity and sell one
        /// You can buy only one and then you need to sell it, meaning that more than one buy action would still keep the first bought equity
        /// This class is a long living one so you can contain state in it (like historical prices)
        /// You need to buy first in order to sell
        /// AND REMEMBER, trading is easy - buy low, sell high!
        /// </summary>
        /// <param name="price">Current price for Equity</param>
        /// <returns>You need to return one decission out of three possible - Buy, Sell or DoNothing</returns>
        public TradeAction Run(decimal price)
        {
            TradeAction action = TradeAction.DoNothing;

            if (price > previous1 && price > previous2)
            {
                action = TradeAction.Buy;
            }
            else if (price < previous1 && price < previous2)
            {
                action = TradeAction.Sell;
            }

            previous2 = previous1;
            previous1 = price;
            return action;
        }
    }
}
