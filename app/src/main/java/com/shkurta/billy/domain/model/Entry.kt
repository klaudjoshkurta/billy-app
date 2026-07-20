package com.shkurta.billy.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val cost: Double,
    val type: EntryType,
    val frequency: PaymentFrequency,
    val dueDay: Int, // Day of the month (1-31)
    val dueMonth: Int? = null, // Optional for ONE_OFF
    val dueYear: Int? = null   // Optional for ONE_OFF
)

enum class EntryType {
    BILL, SUBSCRIPTION
}

enum class PaymentFrequency {
    MONTHLY, SIX_MONTHS, ANNUAL, ONE_OFF
}
