# allow googletest includes as system includes <this/that>
include_directories(SYSTEM /import/local/soft/include)
message(STATUS "Add system include path: /import/local/soft/include")

# extend cmake search path: /lib appended to all paths
set(CMAKE_PREFIX_PATH /import/local/soft)
message(STATUS "Add local include/libs prefix: ${CMAKE_PREFIX_PATH}")

# find the googletest libraries
find_library(LIB_GTEST libgtest.a)
find_library(LIB_GTEST_MAIN libgtest_main.a)

# googletest needs threads
find_package(Threads REQUIRED)

# set a common variable to hold them all
link_libraries(${LIB_GTEST} ${LIB_GTEST_MAIN} pthread)
message(STATUS "Add Googletest lib: ${LIB_GTEST}")
message(STATUS "Add Googletest lib: ${LIB_GTEST_MAIN}")
message(STATUS "Add Threads lib: ${CMAKE_THREAD_LIBS_INIT}")

# the tests
foreach (target
    algorithm
    counting_sort_array_int
    counting_sort_array_long
    counting_sort_carray_int
    counting_sort_carray_long
    counting_sort_vector_int
    counting_sort_vector_long
    counting_sort_stability
    )
    message(STATUS "Adding test: ${target}")
    set(tester ${target}_test)
    unset(sources)
    list(APPEND sources ${tester}.cc)
    if (EXISTS ../src/${target}.cc)
        list(APPEND sources ../src/${target}.cc)
    endif()
    add_executable(${tester} ${sources})
    add_test(NAME ${tester} COMMAND ${tester})
endforeach()
