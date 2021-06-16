package org.sucram.currencyconverter.api

import org.sucram.currencyconverter.api.dto.LatestRateResponseDTO
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExchangeRatesAPIService {

    private val BASE_URL = "http://api.exchangeratesapi.io/v1/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ExchangeRatesAPI::class.java)


    fun loadData(symbols: String ="BRL,USD,EUR,JPY"): Call<LatestRateResponseDTO> {
        return api.getData(symbols= symbols)
    }

}