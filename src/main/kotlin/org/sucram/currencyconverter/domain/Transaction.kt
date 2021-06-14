package org.sucram.currencyconverter.domain

import java.util.*

data class TransactionDTO(val transaction: Transaction? = null)

data class Transaction(
    val id: Long?=null,
    val userId: Long,
    val originCurrency: String,
    val originAmount: Double,
    val destinationCurrency: String,
    val destinationAmount: Double,
    val exchangeRate: Double,
    val date: Date = Date()
)
