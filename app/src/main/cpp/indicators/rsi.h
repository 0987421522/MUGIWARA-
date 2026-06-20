#ifndef MUGIWARA_RSI_H
#define MUGIWARA_RSI_H

#include <vector>

namespace mugiwara {

class RSIIndicator {
public:
    explicit RSIIndicator(int period = 14);
    double calculate(const std::vector<double>& prices) const;
    int getSignal(const std::vector<double>& prices) const;

private:
    int period_;
};

} // namespace mugiwara

#endif // MUGIWARA_RSI_H
