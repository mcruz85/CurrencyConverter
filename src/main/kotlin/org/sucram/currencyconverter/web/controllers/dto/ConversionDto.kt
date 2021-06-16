package org.sucram.currencyconverter.web.controllers.dto

data class ConversionDto(
    var from: String,
    val to: String,
    val amount: Double,
    val userId: Long? = null
)