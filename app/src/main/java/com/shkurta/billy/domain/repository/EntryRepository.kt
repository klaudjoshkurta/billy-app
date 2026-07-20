package com.shkurta.billy.domain.repository

import com.shkurta.billy.domain.model.Entry
import kotlinx.coroutines.flow.Flow

interface EntryRepository {
    suspend fun insertEntry(entry: Entry)
    suspend fun deleteEntry(entry: Entry)
    suspend fun getEntryById(id: Int): Entry?
    fun getAllEntries(): Flow<List<Entry>>
}
