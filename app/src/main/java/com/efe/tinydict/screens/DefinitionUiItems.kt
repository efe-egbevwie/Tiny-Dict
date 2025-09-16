package com.efe.tinydict.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.efe.tinydict.domain.DictionaryEntry
import com.efe.tinydict.ui.theme.TinyDIctTheme

@Composable
fun DefinitionItem(
    dictionaryEntry: DictionaryEntry,
    modifier: Modifier = Modifier
) {
    TinyDictCard(
        modifier = modifier.animateContentSize()
    ) {
        Text(
            text = dictionaryEntry.word,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = dictionaryEntry.partOfSpeech.orEmpty(),
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.secondary
        )

        SelectionContainer {
            Text(
                text = dictionaryEntry.definition,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Text(
            text = "TinyDict",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Black,
            modifier = Modifier.align(Alignment.End)
        )
    }
}


@Composable
fun NoDefinitionFound(word: String, modifier: Modifier = Modifier) {
    TinyDictCard(modifier = modifier) {
        Text(
            text = word,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "No Definition found",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            text = "TinyDict",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Black,
            modifier = Modifier.align(Alignment.End)
        )
    }
}


@Composable
fun NoWordSelectedItem(modifier: Modifier = Modifier) {
    TinyDictCard(modifier = modifier) {
        Text(
            text = "No word selected",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            text = "TinyDict",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Black,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun SearchingForDefinition(word: String, modifier: Modifier = Modifier) {
    TinyDictCard(modifier = modifier) {
        Text(
            text = word,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Searching for definition...",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            text = "TinyDict",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Black,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
private fun TinyDictCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            content()
        }
    }
}

private val itemModifier =
    Modifier
        .padding(6.dp)

@PreviewLightDark
@Composable
private fun DefinitionPreview() {
    TinyDIctTheme {
        DefinitionItem(
            modifier = itemModifier,
            dictionaryEntry = DictionaryEntry(
                word = "Day",
                partOfSpeech = "n",
                definition = "The time of light, or interval between one night and the next; the time between sunrise and sunset, or from dawn to darkness; hence, the light; sunshine."
            )
        )
    }
}

@PreviewLightDark
@Composable
private fun SearchingForDefinitionPreview() {
    TinyDIctTheme {
        SearchingForDefinition(word = "Aggression", modifier = itemModifier)
    }
}

@PreviewLightDark
@Composable
private fun NoDefinitionFoundPreview() {
    TinyDIctTheme {
        NoDefinitionFound(word = "NonExistentWord", modifier = itemModifier)
    }
}

@PreviewLightDark
@Composable
private fun NoWordSelectedPreview() {
    TinyDIctTheme {
        NoWordSelectedItem(modifier = itemModifier)
    }
}