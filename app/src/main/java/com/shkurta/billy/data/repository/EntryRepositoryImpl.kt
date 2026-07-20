package com.shkurta.billy.data.repository

import com.shkurta.billy.data.local.EntryDao
import com.shkurta.billy.domain.model.Entry
import com.shkurta.billy.domain.repository.EntryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EntryRepositoryImpl @Inject constructor(
    private val dao: EntryDao
) : EntryRepository {
    override suspend fun insertEntry(entry: Entry) {
        dao.insertEntry(entry)
    }

    override suspend fun deleteEntry(entry: Entry) {
        dao.deleteEntry(entry)
    }

    override fun getAllEntries(): Flow<List<Entry>> {
        return dao.getAllEntries()
    }
}
