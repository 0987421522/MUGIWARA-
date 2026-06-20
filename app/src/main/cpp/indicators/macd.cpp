#include "macd.h"
#include <vector>

namespace mugiwara {

MACDIndicator::MACDIndicator(int fastPeriod, int slowPeriod, int signalPeriod)
    : fastPeriod_(fastPeriod), slowPeriod_(slowPeriod), signalPeriod_(signalPeriod) {}

double MACDIndicator::calculateEMA(const std::vector<double>& prices, int period) const {
    double multiplier = 2.0 / (period + 1);
    double ema = 0.0;
    
    for (int i = 0; i < period && i < static_cast<int>(prices.size()); ++i) {
        ema += prices[i];
    }
    ema /= period;
    
    for (size_t i = period; i < prices.size(); ++i) {
        ema = (prices[i] - ema) * multiplier + ema;
    }
    
    return ema;
}

double MACDIndicator::calculate(const std::vector<double>& prices) const {
    if (prices.size() < static_cast<size_t>(slowPeriod_)) {
        return 0.0;
    }
    
    double emaFast = calculateEMA(prices, fastPeriod_);
    double emaSlow = calculateEMA(prices, slowPeriod_);
    
    return emaFast - emaSlow;
}

double MACDIndicator::calculateSignal(const std::vector<double>& prices) const {
    std::vector<double> macdLine;
    
    for (size_t i = slowPeriod_; i < prices.size(); ++i) {
        std::vector<double> subPrices(prices.begin(), prices.begin() + i + 1);
        macdLine.push_back(calculate(subPrices));
    }
    
    if (macdLine.size() < static_cast<size_t>(signalPeriod_)) {
        return 0.0;
    }
    
    double sum = 0.0;
    for (size_t i = macdLine.size() - signalPeriod_; i < macdLine.size(); ++i) {
        sum += macdLine[i];
    }
    
    return sum / signalPeriod_;
}

int MACDIndicator::getSignal(const std::vector<double>& prices) const {
    if (prices.size() < static_cast<size_t>(slowPeriod_) + signalPeriod_ + 1) {
        return 0;
    }
    
    double currentMACD = calculate(prices);
    double currentSignal = calculateSignal(prices);
    
    std::vector<double> prevPrices(prices.begin(), prices.end() - 1);
    double prevMACD = calculate(prevPrices);
    double prevSignal = calculateSignal(prevPrices);
    
    if (currentMACD > currentSignal && prevMACD <= prevSignal) return 1;   // BUY
    if (currentMACD < currentSignal && prevMACD >= prevSignal) return -1;  // SELL
    return 0; // NEUTRAL
}

} // namespace mugiwara
