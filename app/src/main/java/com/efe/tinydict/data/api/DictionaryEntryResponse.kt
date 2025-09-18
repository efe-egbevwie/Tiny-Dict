package com.efe.tinydict.data.api

import com.efe.tinydict.domain.Definition
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias EntriesResponse = List<Entry>

@Serializable
data class Entry(
    @SerialName("fl")
    val functionLabel: String? = null,
    @SerialName("shortdef")
    val shortDefinitions: List<String>? = null,
)

fun Entry.toDomainDefinition(): Definition = Definition(
    functionalLabel = this.functionLabel,
    definition = this.shortDefinitions?.joinToString("\n")
)
