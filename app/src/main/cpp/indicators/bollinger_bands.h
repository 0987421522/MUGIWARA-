#ifndef MUGIWARA_BOLLINGER_BANDS_H
#define MUGIWARA_BOLLINGER_BANDS_H

#include <vector>

namespace mugiwara {

struct BollingerBands {
    double upper;
    double middle;
    double lower;
};

class BollingerBandsIndicator {
public:
    BollingerBandsIndicator(int period = 20, double deviations = 2.0);
    BollingerBands calculateBands(const std::vector<double>& prices) const;
    double calculate(const std::vector<double>& prices) const;
    int getSignal(const std::vector<double>& prices) const;

private:
    int period_;
    double deviations_;
};

} // namespace mugiwara

#endif // MUGIWARA_BOLLINGER_BANDS_H
