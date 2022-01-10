package com.example.unsplashphotos.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    collectTest()
}

fun collectTest() = runBlocking {
    val time = measureTimeMillis {
        getFlow().collect {
            println("Before"+ it)
            delay(3000)
            println(it)
        }
    }
    println(time)
}

fun getFlow() = flow {
    for (value in 1..3) {
        delay(1000)
        emit(value)
    }
}
