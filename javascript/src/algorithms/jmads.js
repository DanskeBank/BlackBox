'use strict';

/*
 * NOTE: Don't forget to add the algorithm to the list of to be run inside application.js
 */

const ACTIONS = {
    BUY: 'Buy',
    SELL: 'Sell',
    DO_NOTHING: 'DoNothing'
};

function getRandomNumber(min, max) {
    return Math.floor(Math.random() * (max - min + 1) - min);
}

function getRandomAction() {
    var randomNumber = getRandomNumber(1, 100);

    if (randomNumber < 33) {
        return ACTIONS.BUY;
    } else if (randomNumber < 66) {
        return ACTIONS.SELL;
    } else {
        return ACTIONS.DO_NOTHING;
    }
}

export default {
    /*
     * The interface of the algorithm must contain a string called 'participant' that is the name of the.... participant
     * in the competition and also a getInstance function that acts as a factory function for the algorithm, returning
     * another function that will be run for each price tick for a given ticker, any state should be saved inside that
     * function to avoid colliding with state relating to other tickers.
     */
    participant: 'Jesper Madsen',
    getInstance: function () {
		    
        var boughtAt = 0; //Price we last bought stock at
        var maxPrice = 9007199254740991; //Max price we want to pay. Defaulted to max integer value in Javascript
        var ownStock = false; //Do we own stock?
        
        var endMonth = 0; //Price at the end of the previous month
        var trend = [[],[],[],[],[],[],[],[],[],[],[],[]]; //Trend window - For each month, keeps track of whether the previous <trendYears> years have had a positive gain from last business day of month till first of next month
        var trendYears = 3; //Number of years to keep in sliding trend window
        var firstTradingDay = false; //Is this the first trading day of the month?
        
        function sum(array) {
			var total = 0;
			for(var i=0,n=array.length; i<n; ++i)
			{
				total += array[i];
			}
			return total;
		}		
		
		function updateTrendWindow(month, difference) {
			var lastMonth = month-1;
			if(lastMonth == -1) lastMonth = 11;
			if(difference > 0) { //Positive gain
				trend[lastMonth].push(1);
			} else if(difference < 0) { //Negative gain (aka loss)
				trend[lastMonth].push(-1);
			} else {
				trend[lastMonth].push(0); //No gain or loss
			}
			if(trend[lastMonth].length > trendYears) { //Remove entries older than <trendYears> years
					 trend[lastMonth].splice(0,1);
			}
		}

        return function (price, date) {
			var d = new Date(date);
			var month = d.getMonth();
			if(d.getDate() >= 21) { //We only allow BUY actions from the 20th and onwards
				endMonth = price;
				firstTradingDay = true;
					
				var total = sum(trend[month]);
				if(total >= 0) {
					if(!ownStock && price < maxPrice) {
						ownStock = true;
						boughtAt = price;
						return ACTIONS.BUY;
					}
				}
			} else if(d.getDate() <= 5) { //We only allow SELL ations from untill the 6th of each month
				if(firstTradingDay) {
					firstTradingDay = false;
					updateTrendWindow(month, price-endMonth);

				}
				if(price > boughtAt && ownStock) {
					maxPrice = price;
					ownStock = false;
					return ACTIONS.SELL;
				}
			}
			if(d.getDate() == 15) { //The max price we want to pay to buy is the price of the stock on the 16th
				maxPrice = price;
			}
			return ACTIONS.DO_NOTHING;
        };
    }
};
