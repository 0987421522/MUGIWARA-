# MUGIWARA 🏴‍☠️

Advanced Automated Trading Platform for MetaTrader 5

## 🎯 Features

- **Multi-Account Support**: Manage multiple trading accounts simultaneously
- **Market Analysis**: Real-time technical analysis with 5 indicators (RSI, MACD, MA, Bollinger Bands)
- **AI Signal Filtering**: Intelligent signal evaluation with confidence scoring
- **Auto Trading**: Fully automated trade execution based on signals
- **Risk Management**: Advanced risk controls with daily limits and position sizing
- **Money Management**: Kelly Criterion, Martingale, and Compound strategies
- **Market Hours Detection**: Automatic market open/close detection for all asset classes
- **Trailing Stop**: Dynamic stop-loss adjustment
- **Notifications**: Real-time alerts for trades, profits, and market events
- **Dark Theme**: Material Design 3 with custom dark theme
- **Native Performance**: C++ NDK components for high-performance calculations

## 📊 Supported Markets

| Market | Status |
|--------|--------|
| Forex | ✅ |
| Gold | ✅ |
| Silver | ✅ |
| Crypto | ✅ |
| Indices | ✅ |
| Stocks | ✅ |

## 🏗️ Architecture

```
MUGIWARA/
├── app/
│   ├── src/main/
│   │   ├── java/com/mugiwara/
│   │   │   ├── data/          # Data Layer
│   │   │   ├── domain/        # Domain Layer
│   │   │   ├── presentation/  # Presentation Layer
│   │   │   ├── service/       # Services
│   │   │   ├── di/            # Dependency Injection
│   │   │   └── utils/         # Utilities
│   │   ├── cpp/               # C++ NDK Components
│   │   └── res/               # Android Resources
│   └── build.gradle.kts
├── gradle/
└── .github/workflows/
```

## 🛠️ Tech Stack

- **Kotlin** - Primary language
- **Java** - Secondary language
- **C++** - Native performance components
- **Android NDK** - Native development
- **MVVM** - Architecture pattern
- **Clean Architecture** - Code organization
- **Hilt** - Dependency injection
- **Room** - Local database
- **Retrofit** - Network client
- **OkHttp** - HTTP client
- **WorkManager** - Background processing
- **Material Design 3** - UI design
- **Jetpack Compose** - UI toolkit

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 34
- Android NDK 25.2.9519653
- JDK 17

### Build

```bash
# Clone the repository
git clone https://github.com/yourusername/MUGIWARA.git

# Build the project
./gradlew assembleDebug

# Or build release
./gradlew assembleRelease
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👨‍💻 Developer

**LO - Abdul Wadud Al-Ali**

Android Developer, Language Engineer, and Cybersecurity Researcher

### 📱 Connect with me

- **WhatsApp**: https://wa.me/963983351431
- **YouTube**: https://youtube.com/channel/UCOBlDXzjPFlfuCkEbMCVfeA?si=mtbkh4dD6XtOI5pB
- **YouTube 2**: https://youtube.com/channel/UCn7r4n8-1VGwHTLCkYXUErA?si=EOHsdo-jjB8aN2ph
- **Instagram**: https://www.instagram.com/mugiwara.511
- **Facebook**: https://www.facebook.com/share/18q3yjA4tX/

---

*تم إنشاء هذا المشروع بحب وإتقان. MUGIWARA ليس مجرد تطبيق، بل مشروع احترافي يهدف إلى بناء منصة تداول آلي متقدمة باستخدام أفضل ممارسات هندسة البرمجيات.*
