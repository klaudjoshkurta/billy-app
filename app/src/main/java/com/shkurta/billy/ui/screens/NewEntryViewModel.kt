package com.shkurta.billy.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shkurta.billy.domain.model.Entry
import com.shkurta.billy.domain.model.EntryType
import com.shkurta.billy.domain.model.PaymentFrequency
import com.shkurta.billy.domain.repository.EntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewEntryViewModel @Inject constructor(
    private val repository: EntryRepository
) : ViewModel() {

    var name by mutableStateOf("")
        private set

    var cost by mutableStateOf("")
        private set

    var type by mutableStateOf(EntryType.BILL)
        private set

    var frequency by mutableStateOf(PaymentFrequency.MONTHLY)
        private set

    var dueDay by mutableStateOf("")
        private set

    fun onNameChange(newName: String) {
        name = newName
    }

    fun onCostChange(newCost: String) {
        cost = newCost
    }

    fun onTypeChange(newType: EntryType) {
        type = newType
    }

    fun onFrequencyChange(newFrequency: PaymentFrequency) {
        frequency = newFrequency
    }

    fun onDueDayChange(newDay: String) {
        if (newDay.isEmpty() || (newDay.toIntOrNull() != null && newDay.toInt() in 1..31)) {
            dueDay = newDay
        }
    }

    fun saveEntry(onSuccess: () -> Unit) {
        val costDouble = cost.toDoubleOrNull() ?: 0.0
        val dueDayInt = dueDay.toIntOrNull() ?: 1
        if (name.isBlank() || costDouble <= 0.0) return

        viewModelScope.launch {
            repository.insertEntry(
                Entry(
                    name = name,
                    cost = costDouble,
                    type = type,
                    frequency = frequency,
                    dueDay = dueDayInt
                )
            )
            onSuccess()
        }
    }
}
