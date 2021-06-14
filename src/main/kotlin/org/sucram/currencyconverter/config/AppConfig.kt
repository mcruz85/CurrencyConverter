package org.sucram.currencyconverter.config

import io.javalin.Javalin
import org.eclipse.jetty.server.Server
import org.koin.core.KoinProperties
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import org.sucram.currencyconverter.web.controllers.Router
import org.sucram.currencyconverter.web.controllers.TransactionController

class AppConfig: KoinComponent {

    private val router: Router by inject()



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

       router.register(app)

        return app
    }

}