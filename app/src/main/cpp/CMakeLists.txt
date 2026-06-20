cmake_minimum_required(VERSION 3.22.1)

project("mugiwara_native")

add_library(
    mugiwara_native
    SHARED
    native-lib.cpp)

find_library(
    log-lib
    log)

target_link_libraries(
    mugiwara_native
    ${log-lib})
