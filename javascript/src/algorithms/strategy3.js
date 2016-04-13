'use strict';


const ACTIONS = {
    BUY: 'Buy',
    SELL: 'Sell',
    DO_NOTHING: 'DoNothing'
};

function sign (number) {
    return (number && number / Math.abs(number))
}

export default {
    participant: 'Henrik Gustafsson: Golden-Death Cross',
    getInstance: function () {

        /*
        * Could save state here you want to keep over the lifetime of the algorithm
        * for a specific instrument.
        */
        var holding         = false;
        var buyPrice        = -1;
        
        var shortEMA        = 0;
        var longEMA         = 0;
        var oldSign         = 0;
        var newSign         = 0;
        var act             = false;

        const shortFactor   = 2 / 51;
        const longFactor    = 2 / 201;

        return function (price, date) {

            /* 
            A classic trading algorithm, Golden and Death Cross. Looks at two EMA of different 
            lengths, here 50 and 200 days and trades when the difference between them switches 
            sign. 
            Outperforms the two other models and the test sums to 1721.
            */

            oldSign = sign(shortEMA - longEMA);

            shortEMA    = (1 - shortFactor) * price + shortFactor * shortEMA;
            longEMA     = (1 - longFactor) * price + longFactor * longEMA;

            newSign = sign(shortEMA - longEMA);

            if((newSign * oldSign) < 0 ){
                act = true;
            }

            if( act ){
                // Check for right time to sell
                if( holding ){
                    // Checking direction of crossing
                    if( newSign > 0 ){
                        holding = false;
                        return ACTIONS.SELL;
                    } 
                // Check when to buy
                } else {
                    // Checking direciton of crossing
                    if( newSign < 0 ){
                        holding = true;
                        buyPrice = price;
                        return ACTIONS.BUY;
                    } 
                }
            }

            return ACTIONS.DO_NOTHING;
    };
}
};
