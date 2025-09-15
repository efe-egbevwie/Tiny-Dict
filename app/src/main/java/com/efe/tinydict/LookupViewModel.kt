package com.efe.tinydict

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.efe.tinydict.data.repository.DictionaryRepository
import com.efe.tinydict.domain.DictionaryEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LookupViewModel(
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {
    val isLoading = MutableStateFlow(false)
    val dictionaryEntryResult = MutableStateFlow<DictionaryEntry?>(null)

    fun lookupWord(word: String) = viewModelScope.launch {
        if (word.isBlank()) return@launch
        isLoading.update { true }
        val result: DictionaryEntry? = dictionaryRepository.getEntryByWord(word)
        if (result != null) dictionaryEntryResult.update { result }
        isLoading.update { false }
    }

    companion object {
        fun factory(dictionaryRepository: DictionaryRepository): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    LookupViewModel(
                        dictionaryRepository = dictionaryRepository
                    )
                }
            }
        }
    }
}