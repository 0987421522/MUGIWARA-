#ifndef MUGIWARA_PERFORMANCE_H
#define MUGIWARA_PERFORMANCE_H

#include <chrono>

namespace mugiwara {

class PerformanceMonitor {
public:
    PerformanceMonitor();
    void start();
    void stop();
    double getElapsedMs() const;
    static long long getCurrentTimestamp();

private:
    std::chrono::high_resolution_clock::time_point startTime_;
    std::chrono::high_resolution_clock::time_point endTime_;
    bool isRunning_;
};

} // namespace mugiwara

#endif // MUGIWARA_PERFORMANCE_H
