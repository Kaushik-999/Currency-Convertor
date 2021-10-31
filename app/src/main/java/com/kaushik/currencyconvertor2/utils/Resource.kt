package com.kaushik.currencyconvertor2.utils

import com.kaushik.currencyconvertor2.data.models.Currency

sealed class Resource<T>(val data: Currency?, val message: String?) {
    class Success<T>(data: Currency): Resource<T>(data, null)
    class Error<T>(message: String): Resource<T>(null, message)
}