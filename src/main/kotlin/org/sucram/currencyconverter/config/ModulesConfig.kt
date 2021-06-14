package org.sucram.currencyconverter.config

import TransactionRepository
import org.koin.dsl.module.module

private val configModules = module {

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