package com.kaushik.currencyconvertor2.utils

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val MAIN: CoroutineDispatcher
    val IO: CoroutineDispatcher
    val DEFAULT: CoroutineDispatcher
    val UNCONFINED: CoroutineDispatcher
}