package org.sucram.currencyconverter.domain.service

import TransactionRepository
import org.slf4j.LoggerFactory
import org.sucram.currencyconverter.domain.Transaction
import org.sucram.currencyconverter.services.ExchangeService
import org.sucram.currencyconverter.web.controllers.dto.ConversionDto

class TransactionService(private val exchangeService: ExchangeService, private val transactionRepository: TransactionRepository) {

    private val logger = LoggerFactory.getLogger(this::class.java.name)

    fun convert(conversionDto: ConversionDto): Transaction {
        logger.info("convert $conversionDto")

        val conversion = exchangeService.convert(conversionDto.from, conversionDto.to, conversionDto.amount);

        val transaction = Transaction(
            userId = conversionDto.userId,
            originCurrency = conversionDto.from,
            originAmount = conversionDto.amount,
            destinationCurrency = conversionDto.to,
            destinationAmount = conversion.result,
            exchangeRate = conversion.rate
        )

        val transactionId = transactionRepository.create(transaction)
        return transaction.copy(id = transactionId)
    }


    fun findByUser(userId: Long):List<Transaction> {
        return transactionRepository.findByUserId(userId)
    }
}

