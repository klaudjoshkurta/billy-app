package com.shkurta.billy.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shkurta.billy.domain.model.Entry
import com.shkurta.billy.domain.repository.EntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.shkurta.billy.domain.model.PaymentFrequency
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: EntryRepository
) : ViewModel() {

    val entries: StateFlow<List<Entry>> = repository.getAllEntries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalMonthlyCost: StateFlow<Double> = entries.map { entryList ->
        entryList.sumOf { entry ->
            when (entry.frequency) {
                PaymentFrequency.MONTHLY -> entry.cost
                PaymentFrequency.SIX_MONTHS -> entry.cost / 6.0
                PaymentFrequency.ANNUAL -> entry.cost / 12.0
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    fun deleteEntry(entry: Entry) {
        viewModelScope.launch {
            repository.deleteEntry(entry)
        }
    }
}
