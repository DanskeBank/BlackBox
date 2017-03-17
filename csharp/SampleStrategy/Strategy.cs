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
                return "Mr McTrader";
            }
        }

        private bool _hasPosition = true;


        /// <summary>
        /// Implement this method with your trading strategy :)
        /// You have three options: Buy, DoNothing or Sell.
        /// You always buy one equity and sell one.
        /// If you buy when you are already holding the stock, nothing will happend as you already own the equity.
        /// The same thing goes for selling. Nothing will happend if you sell an equity you do not currently hold.
        /// Remember to always rebuild this project before testing with the StrategyTests project :) Instructions for that beneath.
        /// 
        /// Before submitting, please test your strategy:
        /// 1. Right click the project this file is a part of and press "Build". If you receive any errors you will need to fix these and
        /// redo this step :)
        /// 2. Now for the testing. Right click the StrategyTests project and press "Set as StartUp Project".
        /// 
        /// The file to commit your strategy through is found in:
        /// 1. Right click the project and choose "Open folder in File Explorer".
        /// 2. Navigate into bin/Debug.
        /// 3. The file is the project name for this file(your strategy) with the file ending ".dll"
        /// 3. Press F5 and receive statistics of the amount of Buys, DoNothing and the amount of times your strategy sold the equity.
        /// 
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
