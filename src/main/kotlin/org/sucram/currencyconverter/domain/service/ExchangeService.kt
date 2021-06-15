package org.sucram.currencyconverter.domain.service

import org.slf4j.LoggerFactory
import org.sucram.currencyconverter.api.ExchangeRatesAPIService
import org.sucram.currencyconverter.domain.BusinessException


data class Conversion(val rate: Double, val result: Double)

class ExchangeService(private val exchangeRatesAPIService: ExchangeRatesAPIService) {

    private val logger = LoggerFactory.getLogger(this::class.java.name)


    fun convert(from: String, to: String, amount: Double): Conversion {
       logger.info("convert from=$from, to=$to, amount=$amount")

        val response = exchangeRatesAPIService.loadData(symbols= "$from,$to").execute()

        if (!response.isSuccessful) { throw BusinessException("External api unsuccessful call. $response") }

        val body = response.body()

        if (body == null || !body.success) { throw BusinessException("External api unsuccessful call. $response") }

        val rates = body.rates

        val conversionRate = rates[to]?.div(rates[from]!!)
        val result = conversionRate?.times(amount)

        return Conversion(
            rate = conversionRate!!,
            result = result!!
        )
    }
}