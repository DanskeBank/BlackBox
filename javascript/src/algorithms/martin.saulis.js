'use strict';

const ACTIONS = {
    BUY: 'Buy',
    SELL: 'Sell',
    DO_NOTHING: 'DoNothing'
};

export default {
    participant: 'Martin Saulis',
    getInstance: function () {
        var callsToMe = 0;
        var prices = [];

        var coeff = null;

        return function (price, date){
            var action = ACTIONS.DO_NOTHING;

            // initialise the initial coefficient
            if(!coeff){
              coeff = price * 0.01;
            }

            prices.push(price);
            var diff = price - prices[0];

            if(diff > coeff){
              action = ACTIONS.BUY;
              // clear previous prices and add the current, to be used in next tick
              prices = [price];
            } else if(diff < - coeff * 0.5){
              action = ACTIONS.SELL;
              // clear previous prices and add the current, to be used in next tick
              prices = [price];
            }

            return action;
        };
    }
};
