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
                return "YOUR_SHORT_NAME_HERE";
            }
        }

        private bool _hasPosition = true;

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
            _hasPosition = !_hasPosition;
            if (_hasPosition)
                return TradeAction.Sell;

            return TradeAction.Buy;
            
        }
    }
}
