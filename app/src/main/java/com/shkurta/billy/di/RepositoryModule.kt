package com.shkurta.billy.di

import com.shkurta.billy.data.repository.EntryRepositoryImpl
import com.shkurta.billy.domain.repository.EntryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEntryRepository(
        entryRepositoryImpl: EntryRepositoryImpl
    ): EntryRepository
}
