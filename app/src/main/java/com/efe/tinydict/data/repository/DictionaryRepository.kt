package com.efe.tinydict.data.repository

import android.util.Log
import com.efe.tinydict.BuildConfig
import com.efe.tinydict.data.api.DictionaryApi
import com.efe.tinydict.data.api.toDomainDictionaryEntry
import com.efe.tinydict.data.dao.DictionaryDao
import com.efe.tinydict.data.model.toDomainDictionaryEntry
import com.efe.tinydict.domain.DictionaryEntry


class DictionaryRepository(
    private val dictionaryDao: DictionaryDao,
    private val dictionaryApi: DictionaryApi
) {
    private val tag = this.javaClass.name

    suspend fun getAllEntries(): List<DictionaryEntry>? {
        return try {
            dictionaryDao.getAllEntries().map { it.toDomainDictionaryEntry() }
        } catch (e: Exception) {
            Log.e(tag, "error fetching all entries", e)
            null
        }
    }

    suspend fun getEntryByWord(word: String): DictionaryEntry? {
        return try {
            val localResult: DictionaryEntry? = dictionaryDao.getEntryByWord(word)?.toDomainDictionaryEntry()
            if (localResult != null) {
                return localResult
            } else {
                val remoteResult: DictionaryEntry =
                    dictionaryApi.lookupWord(word).map { it.toDomainDictionaryEntry(word) }.first()
                 return remoteResult
            }
        } catch (e: Exception) {
            if (BuildConfig.DEBUG){
                Log.e(tag, "error fetching entry", e)
            }
            null
        }
    }
}