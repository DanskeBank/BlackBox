'use strict';


const ACTIONS = {
    BUY: 'Buy',
    SELL: 'Sell',
    DO_NOTHING: 'DoNothing'
};

export default {
    participant: 'Henrik Gustafsson: Up and Down',
    getInstance: function () {

        /*
         * Could save state here you want to keep over the lifetime of the algorithm
         * for a specific instrument.
         */
        var holding         = false;
        var buyPrice        = -1;
        var maximum         = -1;
        var minimum         = 100000;
        var newMax          = true;
        var newMin          = true;
        var oldPrice        = -1;
        var up              = true;
        var down            = true;

        return function (price, date) {
            /*
            Simplest possible algorithm, if we are in the money and the price went down relative with yesterday, sell. 
            If the price went up relative to yesterday, buy. Performs relative well, the test sums to 1157. 
            */

            if (price > oldPrice){
                up = true;
                down = false;
            } else {
                up = false;
                down = true;
            }
            // Check for right time to sell
            if( holding ){
                // never sell if you bought it for more
                if( buyPrice < price ){
                    // If the priced droped, then sell
                    if( down ){
                        holding = false;
                        oldPrice = price;
                        return ACTIONS.SELL;
                    } 
                } 
            // Check when to buy
            } else {
                // If the priced started to go up, buy
                if( up ){
                    holding = true;
                    buyPrice = price;
                    oldPrice = price;
                    return ACTIONS.BUY;
                } 
            }
            oldPrice = price;
            return ACTIONS.DO_NOTHING;
        };
    }
};
