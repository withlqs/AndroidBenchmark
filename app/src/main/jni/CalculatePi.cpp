//
// Created by lqs on 16-5-25.
//
#include <stdlib.h>
#include <pthread.h>

#include "com_lqs_androidbenchmark_BenchmarkJNI.h"


//pthread_mutex_t mutex;
//jint overThreadNumber;
//
//void increaseOverThreadNumber() {
//    pthread_mutex_lock(&mutex);
//    ++overThreadNumber;
//    pthread_mutex_unlock(&mutex);
//}


void *CalculatePi(void *periodPtr) {
    jint period = *(jint *) periodPtr;
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
}

void *singleThreadCalculatePi(void *periodPtr) {
    CalculatePi(periodPtr);
    pthread_exit(0);
}

JNIEXPORT void JNICALL
Java_com_lqs_androidbenchmark_BenchmarkJNI_singleThreadFloatBenchmark(JNIEnv *, jclass,
                                                                      jint period) {
    CalculatePi(&period);
}

JNIEXPORT void JNICALL
Java_com_lqs_androidbenchmark_BenchmarkJNI_singleThreadIntergerBenchmark(JNIEnv *, jclass,
                                                                         jint period) {
    srand(0);
    jint a = 0, b = 0;
    for (jint i = 0; i < period; i++) {
        if (rand() < rand()) {
            ++a;
        } else {
            ++b;
        }
    }
}

void setPolicyAndPriority(pthread_attr_t *attr) {
    pthread_attr_setschedpolicy(attr, SCHED_RR);
    sched_param param;
    param.__sched_priority = 99;
    pthread_attr_setschedparam(attr, &param);
}

JNIEXPORT jdouble JNICALL
Java_com_lqs_androidbenchmark_BenchmarkJNI_multiThreadBenchmark(JNIEnv *env, jclass,
                                                                jint threadNumber, jint period) {
    clock_t start, end;
//    overThreadNumber = 0;
    void *status;
    pthread_t threads[threadNumber];

    pthread_attr_t attr;
    pthread_attr_init(&attr);
    pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_JOINABLE);

    start = clock();
    for (int i = 0; i < threadNumber; i++) {
        pthread_create(threads + i, &attr, CalculatePi, &period);
    }
    for (int i = 0; i < threadNumber; i++) {
        pthread_join(threads[i], &status);
    }
    end = clock();

    pthread_attr_destroy(&attr);

    return (jdouble) (end - start) / CLOCKS_PER_SEC;
}

JNIEXPORT jstring JNICALL
Java_com_lqs_androidbenchmark_BenchmarkJNI_getDemoString(JNIEnv *env, jclass, jint) {
    return env->NewStringUTF("Test String from JNI, test 2");
}


