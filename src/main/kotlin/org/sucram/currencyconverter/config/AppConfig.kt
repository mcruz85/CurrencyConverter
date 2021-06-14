package org.sucram.currencyconverter.config

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import org.eclipse.jetty.server.Server
import org.koin.core.KoinProperties
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import org.sucram.currencyconverter.web.controllers.Router
import org.sucram.currencyconverter.web.controllers.TransactionController
import java.text.SimpleDateFormat

class AppConfig: KoinComponent {

    private val router: Router by inject()



    fun setup(): Javalin{

        StandAloneContext.startKoin(
            allModules,
            KoinProperties(true, true)
        )

        this.configureMapper()
        val port: Int = System.getenv("PORT")?.toIntOrNull() ?: 7000
        val app = Javalin.create { config ->
            config.apply {
                enableWebjars()
                enableCorsForAllOrigins()
                addStaticFiles("/swagger")
                addSinglePageRoot("","/swagger/swagger-ui.html")
                server {
                    Server(port)
                }
            }
        }.events {
            it.serverStopping {
                StandAloneContext.stopKoin()
            }
        }

       router.register(app)
        return app
    }

    private fun configureMapper() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        JavalinJackson.configure(
            jacksonObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(dateFormat)
                .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)
        )
    }

}