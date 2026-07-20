package com.shkurta.billy.di

import android.content.Context
import androidx.room.Room
import com.shkurta.billy.data.local.BillyDatabase
import com.shkurta.billy.data.local.EntryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BillyDatabase {
        return Room.databaseBuilder(
            context,
            BillyDatabase::class.java,
            "billy_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideEntryDao(db: BillyDatabase): EntryDao {
        return db.entryDao
    }
}
