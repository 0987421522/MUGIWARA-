#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_mugiwara_utils_NativeUtils_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "MUGIWARA Native Engine";
    return env->NewStringUTF(hello.c_str());
}
