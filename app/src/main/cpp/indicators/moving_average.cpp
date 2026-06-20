#include "moving_average.h"

namespace mugiwara {

MovingAverageIndicator::MovingAverageIndicator(int period) : period_(period) {}

double MovingAverageIndicator::calculate(const std::vector<double>& prices) const {
    if (prices.size() < static_cast<size_t>(period_)) {
        return prices.empty() ? 0.0 : prices.back();
    }
    
    double sum = 0.0;
    for (size_t i = prices.size() - period_; i < prices.size(); ++i) {
        sum += prices[i];
    }
    
    return sum / period_;
}

int MovingAverageIndicator::getSignal(const std::vector<double>& prices) const {
    if (prices.size() < static_cast<size_t>(period_) + 1) {
        return 0;
    }
    
    double currentMA = calculate(prices);
    double prevMA = calculate(std::vector<double>(prices.begin(), prices.end() - 1));
    double currentPrice = prices.back();
    double prevPrice = prices[prices.size() - 2];
    
    if (currentPrice > currentMA && prevPrice <= prevMA) return 1;   // BUY
    if (currentPrice < currentMA && prevPrice >= prevMA) return -1;  // SELL
    return 0; // NEUTRAL
}

} // namespace mugiwara
