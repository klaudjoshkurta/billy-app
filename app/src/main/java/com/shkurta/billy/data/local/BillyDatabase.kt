package com.shkurta.billy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.shkurta.billy.domain.model.Entry
import com.shkurta.billy.domain.model.EntryType
import com.shkurta.billy.domain.model.PaymentFrequency

@Database(entities = [Entry::class], version = 2, exportSchema = false)
@TypeConverters(BillyTypeConverters::class)
abstract class BillyDatabase : RoomDatabase() {
    abstract val entryDao: EntryDao
}

class BillyTypeConverters {
    @TypeConverter
    fun fromEntryType(type: EntryType): String = type.name

    @TypeConverter
    fun toEntryType(name: String): EntryType = EntryType.valueOf(name)

    @TypeConverter
    fun fromFrequency(frequency: PaymentFrequency): String = frequency.name

    @TypeConverter
    fun toFrequency(name: String): PaymentFrequency = PaymentFrequency.valueOf(name)
}
