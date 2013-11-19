APP_STL := gnustl_static
# -Wno-long-long        disables the "long long warnings" for the OpenCV headers
# -Wno-unused-parameter allows virtual func defs that don't use all params
# -Wno-unknown-pragmas  allows OpenMP pragmas without complaint
APP_CPPFLAGS := -frtti -fexceptions -O3 -Wall -Wextra -pedantic -Wno-long-long -Wno-unused-parameter -Wno-unknown-pragmas -Wstrict-aliasing
APP_ABI := armeabi-v7a
APP_PLATFORM := android-8