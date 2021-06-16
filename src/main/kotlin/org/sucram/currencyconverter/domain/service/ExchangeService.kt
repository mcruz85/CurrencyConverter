package org.sucram.currencyconverter.domain.service

import org.apache.commons.lang3.EnumUtils
import org.slf4j.LoggerFactory
import org.sucram.currencyconverter.api.ExchangeRatesAPIService
import org.sucram.currencyconverter.domain.BusinessException
import org.sucram.currencyconverter.domain.Rate
import org.sucram.currencyconverter.domain.RateImpl
import org.sucram.currencyconverter.domain.Symbol
import org.sucram.currencyconverter.web.controllers.dto.ConversionDto


data class Conversion(val rate: Double, val result: Double)

class ExchangeService(val rate: RateImpl) {

    private val logger = LoggerFactory.getLogger(this::class.java.name)


    fun convert(from: String, to: String, amount: Double): Conversion {
       logger.info("convert from=$from, to=$to, amount=$amount")

        validate(from, to)

        val rates = rate.getRates(from, to)

        val conversionRate = rates[to]?.div(rates[from]!!)
        val result = conversionRate?.times(amount)

        return Conversion(
            rate = conversionRate!!,
            result = result!!
        )
    }

    private fun validate(from: String,  to: String) {
        isValidSymbol(from, "from")
        isValidSymbol(to, "to")
    }

    private fun isValidSymbol(symbol: String, field: String) {
        if (!EnumUtils.isValidEnum(Symbol::class.java, symbol)) { throw BusinessException("field '$field' with currency symbol no allowed") }
    }
}