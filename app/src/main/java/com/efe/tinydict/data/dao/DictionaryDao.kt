package com.efe.tinydict.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.efe.tinydict.data.model.DbDictionaryEntry
@Dao
interface DictionaryDao {
    @Query("SELECT * FROM english_dictionary")
    suspend fun getAllEntries(): List<DbDictionaryEntry>

    @Query("SELECT * FROM english_dictionary WHERE word LIKE :word LIMIT 1")
    suspend fun getEntryByWord(word: String): DbDictionaryEntry?

    @Insert
    suspend fun insert(entry: DbDictionaryEntry)
}