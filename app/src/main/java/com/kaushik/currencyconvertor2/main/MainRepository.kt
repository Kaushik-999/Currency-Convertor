package com.kaushik.currencyconvertor2.main

import com.kaushik.currencyconvertor2.data.models.Currency
import com.kaushik.currencyconvertor2.utils.Resource

// interface is used here as
// during tesing a fake repo can be used and
// while deploying the DeafultMainRepo can be used
interface MainRepository {
    suspend fun getCurrencyConversion(
        fromCurrency: String,
        toCurrency: String,
        amount: String
    ): Resource<Currency> // Resource wrapper class is used to track the success and error state
}