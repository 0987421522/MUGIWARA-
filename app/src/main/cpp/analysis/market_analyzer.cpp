#include "market_analyzer.h"
#include "../indicators/rsi.h"
#include "../indicators/macd.h"
#include "../indicators/moving_average.h"
#include "../indicators/bollinger_bands.h"
#include <cmath>

namespace mugiwara {

MarketAnalyzer::MarketAnalyzer() {}

std::vector<Signal> MarketAnalyzer::analyzeMarket(const std::string& symbol, const std::vector<double>& prices) {
    std::vector<Signal> signals;
    
    if (prices.size() < 50) {
        return signals;
    }
    
    RSIIndicator rsi(14);
    MACDIndicator macd(12, 26, 9);
    MovingAverageIndicator ma20(20);
    MovingAverageIndicator ma50(50);
    BollingerBandsIndicator bb(20, 2.0);
    
    int rsiSignal = rsi.getSignal(prices);
    int macdSignal = macd.getSignal(prices);
    int maSignal = ma20.getSignal(prices);
    int bbSignal = bb.getSignal(prices);
    
    std::vector<int> signalList = {rsiSignal, macdSignal, maSignal, bbSignal};
    int buyCount = 0;
    int sellCount = 0;
    
    for (int sig : signalList) {
        if (sig == 1) buyCount++;
        else if (sig == -1) sellCount++;
    }
    
    double confidence = static_cast<double>(std::max(buyCount, sellCount)) / signalList.size();
    double strength = calculateStrength(prices, rsi.calculate(prices), macd.calculate(prices), ma20.calculate(prices));
    
    if (confidence >= 0.6 && strength >= 0.5) {
        Signal signal;
        signal.symbol = symbol;
        signal.direction = (buyCount > sellCount) ? 1 : -1;
        signal.confidence = confidence;
        signal.strength = strength;
        signal.entryPrice = prices.back();
        
        double atr = calculateATR(prices, 14);
        if (signal.direction == 1) {
            signal.stopLoss = signal.entryPrice - (atr * 1.5);
            signal.takeProfit = signal.entryPrice + (atr * 3.0);
        } else {
            signal.stopLoss = signal.entryPrice + (atr * 1.5);
            signal.takeProfit = signal.entryPrice - (atr * 3.0);
        }
        
        signals.push_back(signal);
    }
    
    return signals;
}

std::vector<Signal> MarketAnalyzer::analyzeAllMarkets(const std::vector<std::pair<std::string, std::vector<double>>>& priceData) {
    std::vector<Signal> allSignals;
    
    for (const auto& data : priceData) {
        auto signals = analyzeMarket(data.first, data.second);
        allSignals.insert(allSignals.end(), signals.begin(), signals.end());
    }
    
    return allSignals;
}

double MarketAnalyzer::calculateTrend(const std::vector<double>& prices) {
    if (prices.size() < 20) return 0.0;
    
    double recent = 0.0;
    double older = 0.0;
    
    for (size_t i = prices.size() - 10; i < prices.size(); ++i) {
        recent += prices[i];
    }
    recent /= 10;
    
    for (size_t i = prices.size() - 20; i < prices.size() - 10; ++i) {
        older += prices[i];
    }
    older /= 10;
    
    if (older == 0.0) return 0.0;
    return (recent - older) / older;
}

double MarketAnalyzer::calculateVolatility(const std::vector<double>& prices) {
    if (prices.size() < 2) return 0.0;
    
    double sum = 0.0;
    for (size_t i = 1; i < prices.size(); ++i) {
        sum += std::abs(prices[i] - prices[i - 1]) / prices[i - 1];
    }
    
    return sum / (prices.size() - 1);
}

double MarketAnalyzer::calculateATR(const std::vector<double>& prices, int period) {
    if (prices.size() < static_cast<size_t>(period) + 1) return 0.0;
    
    double sum = 0.0;
    for (size_t i = prices.size() - period; i < prices.size(); ++i) {
        sum += std::abs(prices[i] - prices[i - 1]);
    }
    
    return sum / period;
}

double MarketAnalyzer::calculateStrength(const std::vector<double>& prices, double rsi, double macd, double ma) {
    if (prices.size() < 2) return 0.0;
    
    double trend = std::abs(calculateTrend(prices));
    double volatility = calculateVolatility(prices);
    double momentum = std::abs(rsi - 50.0) / 50.0;
    
    double trendStrength = std::min(trend, 1.0);
    double momentumStrength = momentum;
    double volatilityFactor = (volatility > 0.001) ? 1.0 : 0.5;
    
    return (trendStrength * 0.4 + momentumStrength * 0.4 + volatilityFactor * 0.2);
}

} // namespace mugiwara
