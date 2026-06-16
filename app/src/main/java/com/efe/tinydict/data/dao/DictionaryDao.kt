package com.efe.tinydict.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.efe.tinydict.data.model.DbDictionaryEntry
@Dao
interface DictionaryDao {
    @Query("SELECT * FROM english_dictionary")
    suspend fun getAllEntries(): List<DbDictionaryEntry>

    @Query("SELECT * FROM english_dictionary WHERE word LIKE :word ")
    suspend fun getEntryByWord(word: String): List<DbDictionaryEntry?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: DbDictionaryEntry)

    @Query("DELETE FROM english_dictionary WHERE word == :word")
    suspend fun deleteByWord(word: String)

    @Transaction
    suspend fun replace(word: String, newEntry: DbDictionaryEntry) {
        deleteByWord(word)
        insert(newEntry)
    }
}