#include "bollinger_bands.h"
#include <cmath>

namespace mugiwara {

BollingerBandsIndicator::BollingerBandsIndicator(int period, double deviations)
    : period_(period), deviations_(deviations) {}

BollingerBands BollingerBandsIndicator::calculateBands(const std::vector<double>& prices) const {
    if (prices.size() < static_cast<size_t>(period_)) {
        double price = prices.empty() ? 0.0 : prices.back();
        return {price, price, price};
    }
    
    double sum = 0.0;
    for (size_t i = prices.size() - period_; i < prices.size(); ++i) {
        sum += prices[i];
    }
    double sma = sum / period_;
    
    double variance = 0.0;
    for (size_t i = prices.size() - period_; i < prices.size(); ++i) {
        variance += (prices[i] - sma) * (prices[i] - sma);
    }
    variance /= period_;
    double stdDev = std::sqrt(variance);
    
    return {
        sma + (stdDev * deviations_),
        sma,
        sma - (stdDev * deviations_)
    };
}

double BollingerBandsIndicator::calculate(const std::vector<double>& prices) const {
    BollingerBands bands = calculateBands(prices);
    double currentPrice = prices.empty() ? 0.0 : prices.back();
    
    if (bands.upper == bands.lower) return 0.5;
    
    return (currentPrice - bands.lower) / (bands.upper - bands.lower);
}

int BollingerBandsIndicator::getSignal(const std::vector<double>& prices) const {
    if (prices.size() < static_cast<size_t>(period_)) {
        return 0;
    }
    
    BollingerBands bands = calculateBands(prices);
    double currentPrice = prices.back();
    double prevPrice = prices[prices.size() - 2];
    
    if (prevPrice <= bands.lower && currentPrice > bands.lower) return 1;   // BUY
    if (prevPrice >= bands.upper && currentPrice < bands.upper) return -1;  // SELL
    return 0; // NEUTRAL
}

} // namespace mugiwara
