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
    participant: 'Lars Alexander Engström',
    getInstance: function () {

        /*
         * Could save state here you want to keep over the lifetime of the algorithm
         * for a specific instrument.
         */
        var callsToMe = 0;
        	
        return function (price) {
            //NOTE: Since this function will be called around 7.000 tiems for an instrument the line
            //below is really causing a lot of spam :)
            //console.log(`Number of calls: ${++callsToMe}`, date, price);
            return getRandomAction();
        };
    }
};
