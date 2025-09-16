package com.efe.tinydict

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.efe.tinydict.data.repository.DictionaryRepository
import com.efe.tinydict.domain.DictionaryEntry
import com.efe.tinydict.screens.QuickLookupDialog
import com.efe.tinydict.ui.theme.TinyDIctTheme

class QuickLookUpActivity : ComponentActivity() {
    private val dictionaryRepository: DictionaryRepository = TinyDictApplication.repository
    private val viewModelFactory =
        LookupViewModel.factory(dictionaryRepository = dictionaryRepository)
    private val viewModel: LookupViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setTheme(R.style.Theme_TinyDict_Dialog)
        this.setFinishOnTouchOutside(true)

        val wordToLookup: String = when {
            intent.hasExtra(Intent.EXTRA_PROCESS_TEXT) -> 
                intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)?.toString()?.trim() ?: ""

            intent.hasExtra(Intent.EXTRA_TEXT) -> 
                intent.getStringExtra(Intent.EXTRA_TEXT)?.trim() ?: ""
            else -> ""
        }
        viewModel.lookupWord(wordToLookup)

        setContent {
            val isLookingUp: Boolean by viewModel.isLoading.collectAsStateWithLifecycle()
            val lookupResult: DictionaryEntry? by viewModel.dictionaryEntryResult.collectAsStateWithLifecycle()

            TinyDIctTheme {
                BoxWithConstraints{
                    val constraints = this
                    val maxWidth = constraints.maxWidth
                    QuickLookupDialog(
                        isLoading = isLookingUp,
                        word = wordToLookup,
                        dictionaryEntry = lookupResult,
                        modifier = Modifier
                            .widthIn(min = maxWidth.div(2))
                    )
                }
            }
        }
    }
}
