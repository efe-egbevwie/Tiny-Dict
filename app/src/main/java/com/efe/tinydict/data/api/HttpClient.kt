package com.efe.tinydict.data.api

import android.util.Log
import com.efe.tinydict.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val httpClient = HttpClient(CIO) {
    defaultRequest {
        url("https://www.dictionaryapi.com/")
    }

    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger{
            override fun log(message: String) {
                if(BuildConfig.DEBUG){
                    Log.d("Http Client", message)
                }
            }
        }
    }

    install(ContentNegotiation) {
        json(json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        })
    }
}