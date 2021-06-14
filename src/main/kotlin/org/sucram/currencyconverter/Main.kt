package org.sucram.currencyconverter

import org.h2.tools.Server
import org.sucram.currencyconverter.config.AppConfig


fun main() {
    Server.createWebServer().start()
    AppConfig().setup().start();
}