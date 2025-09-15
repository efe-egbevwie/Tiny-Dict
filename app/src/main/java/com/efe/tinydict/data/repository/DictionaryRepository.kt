package com.efe.tinydict.data.repository

import android.util.Log
import com.efe.tinydict.data.dao.DictionaryDao
import com.efe.tinydict.data.model.toDomainDictionaryEntry
import com.efe.tinydict.domain.DictionaryEntry


class DictionaryRepository(private val dictionaryDao: DictionaryDao) {
    private val tag = this.javaClass.name
    
    suspend fun getAllEntries(): List<DictionaryEntry>? {
        return try {
            dictionaryDao.getAllEntries().map { it.toDomainDictionaryEntry() }
        }catch (e: Exception){
            Log.e(tag, "error fetching all entries", e)
            null
        }
    }

    suspend fun getEntryByWord(word: String): DictionaryEntry? {
        return try {
            dictionaryDao.getEntryByWord(word)?.toDomainDictionaryEntry()
        }catch (e: Exception){
            Log.e(tag, "error fetching entry", e)
            null
        }
    }
}