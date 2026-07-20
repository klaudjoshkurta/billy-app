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

import java.util.Calendar

@HiltViewModel
class NewEntryViewModel @Inject constructor(
    private val repository: EntryRepository
) : ViewModel() {

    private var currentEntryId: Int? = null

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

    var dueMonth by mutableStateOf<Int?>(null)
        private set

    var dueYear by mutableStateOf<Int?>(null)
        private set

    val isEditMode: Boolean get() = currentEntryId != null

    fun loadEntry(id: Int) {
        if (id == -1 || currentEntryId == id) return
        viewModelScope.launch {
            repository.getEntryById(id)?.let { entry ->
                currentEntryId = entry.id
                name = entry.name
                cost = entry.cost.toString()
                type = entry.type
                frequency = entry.frequency
                dueDay = entry.dueDay.toString()
                dueMonth = entry.dueMonth
                dueYear = entry.dueYear
            }
        }
    }

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
        if (newFrequency == PaymentFrequency.ONE_OFF) {
            val calendar = Calendar.getInstance()
            dueMonth = calendar.get(Calendar.MONTH)
            dueYear = calendar.get(Calendar.YEAR)
        } else {
            dueMonth = null
            dueYear = null
        }
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
                    id = currentEntryId ?: 0,
                    name = name,
                    cost = costDouble,
                    type = type,
                    frequency = frequency,
                    dueDay = dueDayInt,
                    dueMonth = dueMonth,
                    dueYear = dueYear
                )
            )
            onSuccess()
        }
    }

    fun deleteEntry(onSuccess: () -> Unit) {
        val id = currentEntryId ?: return
        viewModelScope.launch {
            repository.getEntryById(id)?.let {
                repository.deleteEntry(it)
                onSuccess()
            }
        }
    }
}
