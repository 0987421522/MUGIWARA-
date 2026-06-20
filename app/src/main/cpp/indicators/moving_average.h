#ifndef MUGIWARA_MOVING_AVERAGE_H
#define MUGIWARA_MOVING_AVERAGE_H

#include <vector>

namespace mugiwara {

class MovingAverageIndicator {
public:
    explicit MovingAverageIndicator(int period = 20);
    double calculate(const std::vector<double>& prices) const;
    int getSignal(const std::vector<double>& prices) const;

private:
    int period_;
};

} // namespace mugiwara

#endif // MUGIWARA_MOVING_AVERAGE_H
