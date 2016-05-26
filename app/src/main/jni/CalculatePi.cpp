//
// Created by lqs on 16-5-25.
//
#include <stdlib.h>

#include "com_lqs_androidbenchmark_BenchmarkJNI.h"

JNIEXPORT jint JNICALL
Java_com_lqs_androidbenchmark_BenchmarkJNI_CalculatePi(JNIEnv *env, jclass, jint period) {
    jdouble x, y;
    const jdouble one = 1.0;
    const jdouble r = one;
    jint inIt = 0;
    srand(0);
    for (jint i = 0; i < period; i++) {
        x = (rand() * one) / RAND_MAX;
        y = (rand() * one) / RAND_MAX;
        if (x * x + y * y < r) {
            ++inIt;
        }
    }
    return inIt;

}

JNIEXPORT jstring JNICALL
Java_com_lqs_androidbenchmark_BenchmarkJNI_getDemoString(JNIEnv *env, jclass, jint) {
    return env->NewStringUTF("Test String from JNI, test 2");
}


