package org.sucram.currencyconverter.web.controllers

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import org.koin.standalone.KoinComponent

class Router(
    private val transactionController: TransactionController) : KoinComponent {

    fun register(app: Javalin) {
        app.routes {
            path("transactions") {
                post(transactionController::create)
            }

            path("users") {
                path(":user-id/transactions") {
                    get(transactionController::findByUser)
                }
            }
        }
    }
}