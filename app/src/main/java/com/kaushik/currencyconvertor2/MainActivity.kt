package com.kaushik.currencyconvertor2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.kaushik.currencyconvertor2.databinding.ActivityMainBinding
import com.kaushik.currencyconvertor2.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConvert.setOnClickListener{
            viewModel.convert(
                binding.fromCurrency.selectedItem.toString(),
                binding.toCurrency.selectedItem.toString(),
                binding.ipAmount.text.toString()
            )
        }

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event->
                when(event){
                    is MainViewModel.CurrencyEvent.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.resultText.text = event.errorText
                    }
                    is MainViewModel.CurrencyEvent.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MainViewModel.CurrencyEvent.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.resultText.text = event.resultText
                    }
                    else -> Unit
                }

            }
        }



    }
}