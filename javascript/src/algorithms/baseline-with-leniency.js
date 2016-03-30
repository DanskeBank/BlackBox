'use strict'

const ACTIONS = {
  BUY: 'Buy',
  SELL: 'Sell',
  DO_NOTHING: 'DoNothing'
};

let average = (arr) => {
  return arr.reduce((prev, curr) => prev + curr, 0) / arr.length
}

let change = (arr) => {
  return arr.reduce((prev, curr, i) => arr[i-1] ? prev + Math.abs(curr - arr[i-1]) : 0, 0)
}

let trend = (arr) => {
  return arr.reduce((prev, curr, i) => arr[i-1] ? prev + (curr - arr[i-1]) : 0, 0)
}

export default {

  participant: 'Baseline With Leniency',
  getInstance: () => {

    let prices = []
    let bought

    let leniency = 1

    const BASELINE_LENGTH = 10

    return (price, date) => {

      prices.push(price);

      if (prices.length < BASELINE_LENGTH) {
        return ACTIONS.DO_NOTHING
      }

      if (bought !== undefined) {
        if (price - average(prices) < trend(prices) * change(prices) / prices.length * leniency) {
          bought = undefined
          prices = []
          return ACTIONS.SELL
        } else {
          leniency += .03
        }
      } else {
        if (price < average(prices)) {
          bought = price
          prices = prices.slice(-BASELINE_LENGTH)
          return ACTIONS.BUY
        }
      }

      return ACTIONS.DO_NOTHING
    }
  }
}
