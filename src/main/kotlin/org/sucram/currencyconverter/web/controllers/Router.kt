package org.sucram.currencyconverter.web.controllers

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import org.koin.standalone.KoinComponent

class Router(
    private val transactionController: TransactionController) : KoinComponent {

    fun register(app: Javalin) {
        app.routes {
            ApiBuilder.path("transactions") {
                ApiBuilder.post(transactionController::create)
                ApiBuilder.path(":user-id") {
                    ApiBuilder.get(transactionController::findByUser)
                }
            }
        }
    }
}