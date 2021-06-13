package org.sucram.currencyconverter.api

import org.sucram.currencyconverter.api.dto.LatestRateResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesAPI {

    @GET("latest")
    fun getData(
        @Query("access_key") key: String = "970e8d992abed2cfa699f51a9fb01c94",
        @Query("symbols") symbols: String = "BRL,USD,EUR,JPY",
        @Query("format") format: String = "1"
    ) : Call<LatestRateResponseDTO>
}