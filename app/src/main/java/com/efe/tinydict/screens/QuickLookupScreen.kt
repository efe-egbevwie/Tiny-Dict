package com.efe.tinydict.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.snap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    Column (modifier = modifier.animateContentSize(animationSpec = snap())){
        when{
            isLoading  && word != null -> SearchingForDefinition(word = word, modifier = Modifier)
            word == null ||word.isBlank()  -> NoWordSelectedItem(modifier = Modifier)
            dictionaryEntry == null  -> NoDefinitionFound(word = word, modifier = Modifier)
            else -> DefinitionItem(dictionaryEntry = dictionaryEntry, modifier = Modifier.verticalScroll(state = rememberScrollState()))
        }
    }
}