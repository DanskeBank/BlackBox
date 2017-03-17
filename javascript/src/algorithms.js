'use strict';

import path from 'path';

import Q from 'q';

import fileUtils from './fileUtils.js';

const ALGORITHMS_DIRECTORY = path.join(__dirname, 'algorithms');
const ALLOWED_ACTIONS = {
    BUY: 'Buy',
    SELL: 'Sell',
    DO_NOTHING: 'DoNothing'
};

function loadAlgorithms() {
    return fileUtils.getFileList({directory: ALGORITHMS_DIRECTORY, extension: '.js', fullPath: true})
        .then(function (algorithmPaths) {
            return algorithmPaths.map(function (algorithmPath) {
                return require(algorithmPath);
            });
        });
}

function verifyAlgorithmInterface(algorithm) {
    return new Q(function (resolve, reject) {
        if (typeof algorithm.participant !== 'string' || !algorithm.participant) {
            reject('Algorithm does not supply a valid "participant" property');
            return;
        }

        if (typeof algorithm.getInstance !== 'function') {
            reject('Algorithm does not supply a valid "getInstance" function');
            return;
        }

        var algorithmInstance = algorithm.getInstance();

        if (typeof algorithmInstance !== 'function') {
            reject('"getInstance" function did not return a function');
            return;
        }

        resolve();
    });
}

function runAlgorithm(instruments, algorithm) {
    return verifyAlgorithmInterface(algorithm).then(function () {
        return Q.all(instruments.map(function (instrument) {
            return new Q.Promise(function (resolve/*,reject*/) {
                var ticker = instrument.Ticker;
                var prices = instrument.Prices;

                var algorithmInstance = algorithm.getInstance();
                var actions = [];

                var pnl = 0;
                var openPosition = null;
                var buyCount = 0;
                var sellCount = 0;
                var doNothingCount = 0;

                for (let i = 0; i < prices.length; i++) {
                    let price = prices[i].Value;
                    let date = prices[i].Date;

                    let action = algorithmInstance(price);

                    switch (action) {
                        case ALLOWED_ACTIONS.BUY:
                            buyCount++;

                            if (openPosition === null) {
                                openPosition = price;
                            }

                            break;
                        case ALLOWED_ACTIONS.SELL:
                            sellCount++;

                            if (openPosition !== null) {
                                if (openPosition >= price) {
                                    pnl = pnl + (price - openPosition);
                                } else {
                                    pnl = pnl - (openPosition - price);
                                }

                                openPosition = null;
                            }

                            break;
                        case ALLOWED_ACTIONS.DO_NOTHING:
                            doNothingCount++;
                            break;
                        default:
                            let error = `--- Algorithm by ${algorithm.participant} for ${ticker}
                            provided and unsupported action, action was: ${action}---`;

                            console.log(error);
                            process.exit(1);

                            break;
                    }

                    actions.push({
                        Date: prices[i].Date,
                        Value: prices[i].Value,
                        Action: action
                    });
                }

                var totalActionsCount = buyCount + sellCount + doNothingCount;

                console.log(`--- Done running algorithm by ${algorithm.participant} for ${ticker} ---`);
                console.log('Actions taken');
                console.log('-> Buy:', buyCount);
                console.log('-> Sell:', sellCount);
                console.log('-> Do nothing:', doNothingCount);
                console.log('-> Total actions', buyCount + sellCount + doNothingCount);
                console.log('-> Number of actions matches number of price ticks?', totalActionsCount === prices.length);

                resolve({
                    participant: algorithm.participant,
                    ticker: ticker,
                    actions: actions
                });
            });
        }));
    });
}

export default {
    run: function (instruments) {
        return loadAlgorithms().then(function (algorithms) {
            //TODO: Use allSettled() to not "throw" on inspection failure.. but instead handle it.
            return Q.all(algorithms.map(runAlgorithm.bind(null, instruments)));
        });
    }
};
