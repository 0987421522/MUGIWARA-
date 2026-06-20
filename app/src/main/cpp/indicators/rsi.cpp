#include "rsi.h"
#include <cmath>

namespace mugiwara {

RSIIndicator::RSIIndicator(int period) : period_(period) {}

double RSIIndicator::calculate(const std::vector<double>& prices) const {
    if (prices.size() < static_cast<size_t>(period_) + 1) {
        return 50.0;
    }

    double gains = 0.0;
    double losses = 0.0;

    for (size_t i = prices.size() - period_; i < prices.size(); ++i) {
        double change = prices[i] - prices[i - 1];
        if (change > 0) {
            gains += change;
        } else {
            losses += std::abs(change);
        }
    }

    double avg_gain = gains / period_;
    double avg_loss = losses / period_;

    if (avg_loss == 0.0) {
        return 100.0;
    }

    double rs = avg_gain / avg_loss;
    return 100.0 - (100.0 / (1.0 + rs));
}

int RSIIndicator::getSignal(const std::vector<double>& prices) const {
    double rsi = calculate(prices);
    if (rsi > 70) return -1; // SELL
    if (rsi < 30) return 1;  // BUY
    return 0;                 // NEUTRAL
}

} // namespace mugiwara
