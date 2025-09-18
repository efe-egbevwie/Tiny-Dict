package com.efe.tinydict.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.snap
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.efe.tinydict.domain.Definition
import com.efe.tinydict.domain.DictionaryEntry
import com.efe.tinydict.ui.theme.TinyDIctTheme
import kotlinx.coroutines.launch

@Composable
fun DefinitionItem(
    dictionaryEntry: DictionaryEntry,
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(pageCount = {
        dictionaryEntry.definition?.size ?: 1
    })
) {
    TinyDictCard(
        modifier = modifier.animateContentSize(animationSpec = snap())
    ) {
        Text(
            text = dictionaryEntry.word,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        HorizontalPager(state = pagerState) { page: Int ->
            Column {
                Text(
                    text = dictionaryEntry.definition?.get(page)?.functionalLabel.orEmpty(),
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(6.dp))

                SelectionContainer {
                    Text(
                        text = dictionaryEntry.definition?.get(page)?.definition ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }

        val scope = rememberCoroutineScope()

        Spacer(modifier = Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            PagerIndicator(
                currentIndex = pagerState.currentPage,
                pageCount = pagerState.pageCount,
                modifier = Modifier,
                onClick = { index ->
                    scope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "TinyDict",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Black,
            )
        }

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

@Composable
private fun PagerIndicator(
    currentIndex: Int,
    pageCount: Int,
    modifier: Modifier = Modifier,
    onClick: (index: Int) -> Unit
) {
    if (pageCount <= 1) return
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(pageCount) { index ->
            PagerIndicatorCircle(
                selected = currentIndex == index,
                modifier = Modifier
                    .size(8.dp)
                    .clickable {
                        onClick(index)
                    }
            )
        }
    }
}

@Composable
private fun PagerIndicatorCircle(selected: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .then(Modifier)
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = CircleShape
            )
            .background(
                if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary
            )
    )
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
                definition = listOf(
                    Definition(
                        functionalLabel = "n",
                        definition = "The time of light, or interval between one night and the next; the time between sunrise and sunset, or from dawn to darkness; hence, the light; sunshine."
                    ),
                    Definition(
                        functionalLabel = "n",
                        definition = "The time of light, or interval between one night and the next; the time between sunrise and sunset, or from dawn to darkness; hence, the light; sunshine."
                    )
                )
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