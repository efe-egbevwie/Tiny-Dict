package com.efe.tinydict

import android.app.Application
import com.efe.tinydict.data.api.DictionaryApi
import com.efe.tinydict.data.database.DictionaryDatabase
import com.efe.tinydict.data.repository.DictionaryRepository
import kotlinx.serialization.json.Json


class TinyDictApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        database = DictionaryDatabase.getInstance(this)
    }

    companion object {
        private lateinit var database: DictionaryDatabase
        private val dictionaryApi = DictionaryApi()

        val jsonSerializer = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }
        val repository by lazy {
            DictionaryRepository(
                dictionaryDao = database.dictionaryDao(),
                dictionaryApi = dictionaryApi,
                jsonSerializer = jsonSerializer
            )
        }
    }
}