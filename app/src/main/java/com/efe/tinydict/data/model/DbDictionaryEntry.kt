package com.efe.tinydict.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.efe.tinydict.domain.DictionaryEntry


@Entity(tableName = "english_dictionary")
data class DbDictionaryEntry(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id: Int,
    val word: String,
    val pos: String ? = null,
    val definition: String
)

fun DbDictionaryEntry.toDomainDictionaryEntry(): DictionaryEntry = DictionaryEntry(
    word = this.word,
    partOfSpeech = this.pos,
    definition = this.definition
)

