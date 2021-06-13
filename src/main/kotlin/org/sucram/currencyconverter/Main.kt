package org.sucram.currencyconverter

import org.sucram.currencyconverter.api.ExchangeRatesAPIService
import org.sucram.currencyconverter.config.AppConfig
import org.sucram.currencyconverter.services.ExchangeService

fun main() {
    AppConfig().setup().start()


    val exchangeService = ExchangeService(exchangeRatesAPIService = ExchangeRatesAPIService())

    val conversion = exchangeService.convert("BRL", "USD", 60.0)

    println("conversion $conversion")


}