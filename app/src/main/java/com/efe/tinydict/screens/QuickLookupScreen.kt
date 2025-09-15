package com.efe.tinydict.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.efe.tinydict.domain.DictionaryEntry

@Composable
fun QuickLookupDialog(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    word: String? = null,
    dictionaryEntry: DictionaryEntry? = null,
){
    when{
        isLoading  && word != null -> SearchingForDefinition(word = word, modifier = modifier)
        word == null ||word.isBlank()  -> NoWordSelectedItem(modifier = modifier)
        dictionaryEntry == null  -> NoDefinitionFound(word = word, modifier = modifier)
        else -> DefinitionItem(dictionaryEntry = dictionaryEntry, modifier = modifier)
    }
}