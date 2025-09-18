package com.efe.tinydict.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.efe.tinydict.domain.Definition
import com.efe.tinydict.domain.DictionaryEntry
import kotlinx.serialization.json.Json


@Entity(tableName = "english_dictionary")
data class DbDictionaryEntry(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id: Int = 0,
    val word: String,
    val definitions: String
)

fun DbDictionaryEntry.toDomainDictionaryEntry(jsonDecoder: Json): DictionaryEntry {
    val definitions = jsonDecoder.decodeFromString<List<Definition>>(this.definitions)

    return DictionaryEntry(
        word = this.word,
        definition = definitions
    )
}

