//
// Created by lqs on 16-5-25.
//

#include "com_lqs_androidbenchmark_BenchmarkJNI.h"

JNIEXPORT void JNICALL
Java_com_lqs_androidbenchmark_BenchmarkJNI_CalculatePi(JNIEnv *env, jclass, jint) {
}

JNIEXPORT jstring JNICALL
Java_com_lqs_androidbenchmark_BenchmarkJNI_getDemoString(JNIEnv *env, jclass, jint) {
    return env->NewStringUTF("Test String from JNI");
}


