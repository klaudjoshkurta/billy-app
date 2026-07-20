package com.shkurta.billy.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shkurta.billy.domain.model.Entry
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: Entry)

    @Delete
    suspend fun deleteEntry(entry: Entry)

    @Query("SELECT * FROM entries ORDER BY id DESC")
    fun getAllEntries(): Flow<List<Entry>>
}
