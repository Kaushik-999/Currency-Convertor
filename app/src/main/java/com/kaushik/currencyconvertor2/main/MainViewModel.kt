package com.kaushik.currencyconvertor2.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaushik.currencyconvertor2.utils.DispatcherProvider
import com.kaushik.currencyconvertor2.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatcher: DispatcherProvider
    ):ViewModel() {

    // Events to track the UI State
    sealed class CurrencyEvent{
        data class Success(val resultText: String): CurrencyEvent()
        data class Error(val errorText: String): CurrencyEvent()
        object Loading: CurrencyEvent()
        object Empty: CurrencyEvent()
    }

    // State Flow for UiState
    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    // to be used in main activity
    val conversion = _conversion

    // function to be called from Main Activity
    fun convert(
        fromCurrency: String,
        toCurrency: String,
        amount: String
    ){

        val fromAmount = amount.toFloatOrNull()
        Log.d("ViewModel", "fromAmount = $fromAmount")

        if(fromAmount == null) {
            _conversion.value = CurrencyEvent.Error("Invalid Number")
            return
        }

        viewModelScope.launch(dispatcher.IO) {
            _conversion.value = CurrencyEvent.Loading

                // parsing the response to fit ui
                when(val response = repository.getCurrencyConversion(fromCurrency, toCurrency, amount)) {
                    is Resource.Error -> _conversion.value = CurrencyEvent.Error(response.message!!)
                    is Resource.Success -> {
                        val convertedAmount = response.data!!.amount
                        _conversion.value = CurrencyEvent.Success(
                            "$amount $fromCurrency = $convertedAmount $toCurrency"
                        )
                    }
                }


        }

    }




}