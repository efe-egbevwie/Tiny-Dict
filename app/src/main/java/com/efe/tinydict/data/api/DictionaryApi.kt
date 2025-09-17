package com.efe.tinydict.data.api

import com.efe.tinydict.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class DictionaryApi(
    private val client: HttpClient = httpClient,
    private val apiKey: String = BuildConfig.API_KEY
) {
    suspend fun lookupWord(word: String): EntriesResponse {
        return client.get("/api/v3/references/collegiate/json/$word?key=$apiKey")
            .body<EntriesResponse>()
    }
}