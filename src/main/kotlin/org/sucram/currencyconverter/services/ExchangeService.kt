package org.sucram.currencyconverter.services

import org.sucram.currencyconverter.api.ExchangeRatesAPIService


data class Conversion(val rate: Double, val result: Double)

class ExchangeService(private val exchangeRatesAPIService: ExchangeRatesAPIService) {


    fun convert(from: String, to: String, amount: Double): Conversion  {

        val response = exchangeRatesAPIService.loadData(symbols= "$from,$to").execute()

        if (!response.isSuccessful()) { RuntimeException("Fail 1!") }

        val body = response.body()

        if (body == null || body.success) { RuntimeException("Fail 2!") }

        val rates = body!!.rates

        val conversionRate = rates[to]?.div(rates[from]!!)
        val result = conversionRate?.times(amount)

        return Conversion(
            rate = conversionRate!!,
            result = result!!
        )
    }
}