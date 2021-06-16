package org.sucram.currencyconverter.domain

interface Rate {
    fun getRates(from: String, to: String): Map<String, Double>
}