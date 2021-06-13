package org.sucram.currencyconverter.config

import io.javalin.Javalin
import org.eclipse.jetty.server.Server

class AppConfig {

    fun setup(): Javalin{

        val port: Int = System.getenv("PORT")?.toIntOrNull() ?: 7000
        val app = Javalin.create { config ->
            config.apply {
                enableWebjars()
                enableCorsForAllOrigins()
                server {
                    Server(port)
                }
            }
        }

        app.get("/") { ctx -> ctx.result("Hello World") }

        return app
    }

}