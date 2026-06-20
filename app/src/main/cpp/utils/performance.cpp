#include "performance.h"

namespace mugiwara {

PerformanceMonitor::PerformanceMonitor() : isRunning_(false) {}

void PerformanceMonitor::start() {
    startTime_ = std::chrono::high_resolution_clock::now();
    isRunning_ = true;
}

void PerformanceMonitor::stop() {
    endTime_ = std::chrono::high_resolution_clock::now();
    isRunning_ = false;
}

double PerformanceMonitor::getElapsedMs() const {
    if (isRunning_) {
        auto now = std::chrono::high_resolution_clock::now();
        return std::chrono::duration<double, std::milli>(now - startTime_).count();
    }
    return std::chrono::duration<double, std::milli>(endTime_ - startTime_).count();
}

long long PerformanceMonitor::getCurrentTimestamp() {
    return std::chrono::duration_cast<std::chrono::milliseconds>(
        std::chrono::system_clock::now().time_since_epoch()).count();
}

} // namespace mugiwara
