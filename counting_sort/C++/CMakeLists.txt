cmake_minimum_required(VERSION 2.8.12)

project(counting_sort)

set(CMAKE_VERBOSE_MAKEFILE off)

if (CMAKE_BINARY_DIR STREQUAL CMAKE_SOURCE_DIR)
    message(FATAL_ERROR "Source and build directories cannot be the same.")
endif()

# project-general includes
include_directories(SYSTEM ../../etc/include)
message(STATUS "Add system include path: ../../etc/include")

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -Wall -Wextra -Wpedantic")

include_directories(./include)
message(STATUS "Add include path: ./include")

enable_testing()
add_subdirectory(./src)
add_subdirectory(./test)
