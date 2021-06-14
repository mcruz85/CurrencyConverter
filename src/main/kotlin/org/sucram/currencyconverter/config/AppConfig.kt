package org.sucram.currencyconverter.config

import io.javalin.Javalin
import org.eclipse.jetty.server.Server
import org.koin.core.KoinProperties
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext

class AppConfig: KoinComponent {

    fun setup(): Javalin{

        StandAloneContext.startKoin(
            allModules,
            KoinProperties(true, true)
        )

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