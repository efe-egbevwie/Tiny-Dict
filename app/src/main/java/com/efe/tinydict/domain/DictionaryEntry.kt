package com.efe.tinydict.domain


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DictionaryEntry(
    val word: String,
    val definition: List<Definition>? = emptyList()
)

@Serializable
data class Definition(
    val definition: String? = null,
    @SerialName("function")
    val functionalLabel: String? = null
)
