package com.efe.tinydict.data.repository

import android.util.Log
import com.efe.tinydict.BuildConfig
import com.efe.tinydict.data.api.DictionaryApi
import com.efe.tinydict.data.api.EntriesResponse
import com.efe.tinydict.data.api.Entry
import com.efe.tinydict.data.api.toDomainDefinition
import com.efe.tinydict.data.dao.DictionaryDao
import com.efe.tinydict.data.model.DbDictionaryEntry
import com.efe.tinydict.data.model.toDomainDictionaryEntry
import com.efe.tinydict.domain.Definition
import com.efe.tinydict.domain.DictionaryEntry
import kotlinx.serialization.json.Json


class DictionaryRepository(
    private val dictionaryDao: DictionaryDao,
    private val dictionaryApi: DictionaryApi,
    private val jsonSerializer: Json
) {
    private val tag = this.javaClass.name

    suspend fun getAllEntries(): List<DictionaryEntry>? {
        return try {
            dictionaryDao.getAllEntries().map { it.toDomainDictionaryEntry(jsonSerializer) }
        } catch (e: Exception) {
            Log.e(tag, "error fetching all entries", e)
            null
        }
    }

    suspend fun getEntryByWord(word: String): DictionaryEntry? {
        return try {
            val localResult: DictionaryEntry? = getLocallySavedEntry(word)
            return localResult ?: getEntryFromDictionaryApi(word)
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                Log.e(tag, "error fetching entry", e)
            }
            null
        }
    }

    private suspend fun getLocallySavedEntry(word: String): DictionaryEntry? {
        return dictionaryDao.getEntryByWord(word)
            ?.toDomainDictionaryEntry(jsonDecoder = jsonSerializer)
    }

    private suspend fun getEntryFromDictionaryApi(word: String): DictionaryEntry {
        val apiResult: EntriesResponse = dictionaryApi.lookupWord(word)
        if (apiResult.isNotEmpty()) {
            saveEntryFromDictionaryApi(word = word, response = apiResult)
        }
        return DictionaryEntry(
            word = word,
            definition = apiResult.map { it.toDomainDefinition() }
        )
    }

    private suspend fun saveEntryFromDictionaryApi(word: String, response: EntriesResponse) {
        val definitions: List<Definition> = response.map { entry: Entry ->
            entry.toDomainDefinition()
        }
        val dbDictionaryEntry = DbDictionaryEntry(
            word = word,
            definitions = jsonSerializer.encodeToString(definitions)
        )
        dictionaryDao.insert(dbDictionaryEntry)
    }
}