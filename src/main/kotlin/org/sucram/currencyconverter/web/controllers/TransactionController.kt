package org.sucram.currencyconverter.web.controllers

import io.javalin.http.Context
import org.slf4j.LoggerFactory
import org.sucram.currencyconverter.domain.service.TransactionService
import org.sucram.currencyconverter.web.controllers.dto.ConversionDto

class TransactionController(private val transactionService: TransactionService) {

    private val logger = LoggerFactory.getLogger(this::class.java.name)

    fun create(ctx: Context) {

        logger.info("Received request for create transaction")

        ctx.bodyValidator<ConversionDto>()
            .check({ !it.from.isNullOrBlank() }, "'from' must not be null or blank")
            .check({ !it.to.isNullOrBlank() }, "'to' must not be null or blank")
            .check({ !it.amount.isNaN() }, "'amount' must not be null or blank")
            .get().also { conversionDto ->

                logger.info(conversionDto.toString())
                transactionService.convert(conversionDto).apply {
                    ctx.json(this).status(201)
                }
            }
    }


    fun findByUser(ctx: Context) {
        logger.info("Received request for transactions by user")
        ctx.json(transactionService.findByUser(ctx.pathParam("user-id").toLong())!!)

    }
}