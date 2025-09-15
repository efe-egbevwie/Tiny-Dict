package com.efe.tinydict.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.efe.tinydict.data.dao.DictionaryDao
import com.efe.tinydict.data.model.DbDictionaryEntry

@Database(entities = [DbDictionaryEntry::class], version = 1, exportSchema = false)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao

    companion object {
        private const val DATABASE_NAME = "dict.sqlite"
        fun getInstance(context: Context): DictionaryDatabase {
            return Room.databaseBuilder(
                context = context.applicationContext,
                klass = DictionaryDatabase::class.java,
                name = DATABASE_NAME
            )
                .createFromAsset(DATABASE_NAME)
                .build()
        }
    }
}