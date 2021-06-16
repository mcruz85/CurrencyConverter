package org.sucram.currencyconverter.web.controllers

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.javalin.Javalin
import org.eclipse.jetty.http.HttpStatus
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import org.sucram.currencyconverter.config.AppConfig
import org.sucram.currencyconverter.domain.Transaction
import org.sucram.currencyconverter.util.HttpUtil
import java.io.File

class TransactionControllerTest {


    companion object {

        @JvmStatic
        private lateinit var app: Javalin

        @JvmStatic
        private lateinit var http: HttpUtil

        @BeforeClass
        @JvmStatic
        fun start() {
            app =  AppConfig().setup().start();
            http = HttpUtil(app.port())

            val file = File("src/test/resources/request_brl_to_usd.json")
            val fileContent = file.readText()
            val jsonFile: JsonNode = ObjectMapper().readTree(fileContent)

            http.createTransaction(jsonFile)
        }

        @AfterClass
        @JvmStatic
        fun stop() {
            app.stop()
        }
    }

    @Test
    fun `get all transactions by user id`() {

        // Arrange
        val userId = 1000

        // Act
        val response = http.get<List<Transaction>>("/transactions?user=$userId")

        // Assert
        assertEquals(response.status, HttpStatus.OK_200)
        assertEquals(response.body?.size, 1)
    }


    @Test
    fun `get status code 400 from wrong currency symbol`() {

        // Arrange
        val file = File("src/test/resources/request_wrog_symbol.json")
        val fileContent = file.readText()
        val jsonFile: JsonNode = ObjectMapper().readTree(fileContent)

        // Act
        val response = http.createTransaction(jsonFile)

        // Assert
        assertEquals(response.status, HttpStatus.BAD_REQUEST_400)
    }


    @Test
    fun `get status code 400 from when from currency symbol blank`() {

        // Arrange
        val file = File("src/test/resources/request_from_currency_blank.json")
        val fileContent = file.readText()
        val jsonFile: JsonNode = ObjectMapper().readTree(fileContent)


        // Act
        val response = http.createTransaction(jsonFile)

        // Assert
        assertEquals(response.status, HttpStatus.BAD_REQUEST_400)
    }

    @Test
    fun `success convert currency`() {

        // Arrange
        val file = File("src/test/resources/request_usd_to_brl.json")
        val fileContent = file.readText()
        val jsonFile: JsonNode = ObjectMapper().readTree(fileContent)

        // Act
        val response = http.createTransaction(jsonFile)

        // Assert
        assertEquals(response.status, HttpStatus.CREATED_201)
    }


}