package org.sucram.currencyconverter.domain

import org.sucram.currencyconverter.api.ExchangeRatesAPIService

class RateImpl(val exchangeRatesAPI: ExchangeRatesAPIService): Rate {

    override fun getRates(from: String, to: String): Map<String, Double> {

        val response = exchangeRatesAPI.loadData(symbols= "$from,$to").execute()

        if (!response.isSuccessful) { throw BusinessException("External api unsuccessful call. $response") }

        val body = response.body()

        if (body == null || !body.success) { throw BusinessException("External api unsuccessful call. $response") }

       return body.rates
    }

}