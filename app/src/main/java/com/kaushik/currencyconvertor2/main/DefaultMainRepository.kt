package com.kaushik.currencyconvertor2.main

import android.util.Log
import com.kaushik.currencyconvertor2.data.CurrencyApiService
import com.kaushik.currencyconvertor2.data.models.Currency
import com.kaushik.currencyconvertor2.utils.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val apiService: CurrencyApiService
): MainRepository {
    override suspend fun getCurrencyConversion(
        fromCurrency: String,
        toCurrency: String,
        amount: String
    ): Resource<Currency> {
        return try {
            val response = apiService.getCurrencyConversion(fromCurrency, toCurrency, amount)
            val result =  response.body()
            if (response.isSuccessful && result!=null){
                Log.d("success","got data :${result}" )
                Resource.Success(result)
            } else {
                Resource.Error("Unexpected error occurred")
            }
        } catch (e: Exception) {
            Log.d("error", e.localizedMessage)
            Resource.Error("e: ${e.localizedMessage}")
        }
    }
}