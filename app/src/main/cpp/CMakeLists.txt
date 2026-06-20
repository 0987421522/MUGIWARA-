cmake_minimum_required(VERSION 3.22.1)

project("mugiwara_native")

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Optimization flags
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -O3 -ffast-math -funroll-loops")
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -O3")

add_library(
    mugiwara_native
    SHARED
    native-lib.cpp
    indicators/rsi.cpp
    indicators/macd.cpp
    indicators/moving_average.cpp
    indicators/bollinger_bands.cpp
    analysis/market_analyzer.cpp
    utils/performance.cpp)

find_library(
    log-lib
    log)

target_link_libraries(
    mugiwara_native
    ${log-lib})
