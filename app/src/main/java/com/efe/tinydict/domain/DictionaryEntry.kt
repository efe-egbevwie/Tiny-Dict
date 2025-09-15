package com.efe.tinydict.domain

data class DictionaryEntry(
    val word: String,
    val partOfSpeech: String? = null,
    val definition: String
)
