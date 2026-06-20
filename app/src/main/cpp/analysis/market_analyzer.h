#ifndef MUGIWARA_MARKET_ANALYZER_H
#define MUGIWARA_MARKET_ANALYZER_H

#include <vector>
#include <string>

namespace mugiwara {

struct Signal {
    std::string symbol;
    int direction; // 1 = BUY, -1 = SELL, 0 = NEUTRAL
    double confidence;
    double strength;
    double entryPrice;
    double stopLoss;
    double takeProfit;
};

class MarketAnalyzer {
public:
    MarketAnalyzer();
    std::vector<Signal> analyzeMarket(const std::string& symbol, const std::vector<double>& prices);
    std::vector<Signal> analyzeAllMarkets(const std::vector<std::pair<std::string, std::vector<double>>>& priceData);

private:
    double calculateTrend(const std::vector<double>& prices);
    double calculateVolatility(const std::vector<double>& prices);
    double calculateATR(const std::vector<double>& prices, int period);
    double calculateStrength(const std::vector<double>& prices, double rsi, double macd, double ma);
};

} // namespace mugiwara

#endif // MUGIWARA_MARKET_ANALYZER_H
