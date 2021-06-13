package org.sucram.currencyconverter.api.dto

data class LatestRateResponseDTO(
    var success: Boolean,
    var timestamp: Long,
    var base: String,
    var date: String,
    var rates: Map<String, Double>
)