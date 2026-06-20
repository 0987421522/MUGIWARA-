#ifndef MUGIWARA_MACD_H
#define MUGIWARA_MACD_H

#include <vector>

namespace mugiwara {

class MACDIndicator {
public:
    MACDIndicator(int fastPeriod = 12, int slowPeriod = 26, int signalPeriod = 9);
    double calculate(const std::vector<double>& prices) const;
    int getSignal(const std::vector<double>& prices) const;

private:
    double calculateEMA(const std::vector<double>& prices, int period) const;
    double calculateSignal(const std::vector<double>& prices) const;

    int fastPeriod_;
    int slowPeriod_;
    int signalPeriod_;
};

} // namespace mugiwara

#endif // MUGIWARA_MACD_H
