'use strict';


const ACTIONS = {
    BUY: 'Buy',
    SELL: 'Sell',
    DO_NOTHING: 'DoNothing'
};

function mean(hist) {
    var sum = 0;
    for( var i = 0; i < hist.length; i++) {
        sum += hist[i];
    }
    return sum / hist.length;
}

function slopeDirection(hist) {
    var sum = 0;
    var meanvalue = mean(hist)
    for(var i = 0 ; i < hist.length; i++) {
        sum += ( hist[i] - meanvalue) * ( i + 1 );
    }
    return (sum && sum / Math.abs(sum));
}

export default {
    participant: 'Henrik Gustafsson2',
    getInstance: function () {

        /*
        * Could save state here you want to keep over the lifetime of the algorithm
        * for a specific instrument.
        */
        var holding         = false;
        var buyPrice        = -1;
        var hist            = [];
        var lastSlope       = 0;
        var slope           = 0;

        const histLength    = 4

        return function (price, date) {

            /* This method is based on finding local max and min. 
                The method used is fitting a first order polynomial to the last n-values
                and when this switches sign we have observed a local minima, the sign of 
                the value before says if it is a minima or a maxima.
            */
            if(hist.length < histLength){
                hist.push(price);
            } else {
                hist = hist.slice(1);
                hist.push(price);
            }
            slope = slopeDirection(hist)
            if( hist.length == histLength ){
                // Check for right time to sell
                if( holding ){
                    // never sell if you bought it for more
                    if( buyPrice < price ){
                        // Checking for local maxima
                        if( lastSlope >= 0 && (slope * lastSlope) <= 0){
                            holding = false;
                            lastSlope = slope;
                            return ACTIONS.SELL;
                        } 
                    } 
                // Check when to buy
                } else {
                    // Checking for local minima
                    if( lastSlope <= 0 && (slope * lastSlope) <= 0 ){
                        holding = true;
                        buyPrice = price;
                        lastSlope = slope;
                        return ACTIONS.BUY;
                    } 
                }
            }

            lastSlope = slope;
            return ACTIONS.DO_NOTHING;
    };
}
};
