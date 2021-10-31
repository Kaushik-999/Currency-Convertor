package com.kaushik.currencyconvertor2.data

import com.kaushik.currencyconvertor2.data.models.Currency
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrencyApiService {

    @Headers(
        Credentials.HEADER_1,
        Credentials.HEADER_2
    )
    @GET(Credentials.CONVERT_PATH)
    suspend fun getCurrencyConversion(
        @Query("from") fromCurrency: String,
        @Query("to") toCurrency: String,
        @Query("amount") amount: String
    ): Response<Currency>
}