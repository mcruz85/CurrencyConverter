package org.sucram.currencyconverter.config

import TransactionRepository
import org.koin.dsl.module.module
import org.sucram.currencyconverter.api.ExchangeRatesAPIService
import org.sucram.currencyconverter.domain.service.TransactionService
import org.sucram.currencyconverter.domain.service.ExchangeService
import org.sucram.currencyconverter.domain.RateImpl
import org.sucram.currencyconverter.web.Router
import org.sucram.currencyconverter.web.controllers.TransactionController

private val configModules = module {

    single { Router(get()) }
    single { ExchangeRatesAPIService() }
    single { RateImpl(get()) }
    single { ExchangeService(get() ) }

    single { TransactionService(get(), get()) }
    single { TransactionController(get())}
    single { TransactionRepository(get()) }

    single {
        DBConfig(
            getProperty("jdbc.url"),
            getProperty("db.username"),
            getProperty("db.password")
        ).get()
    }
}

val allModules = listOf(configModules)