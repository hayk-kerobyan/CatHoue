package com.example.cathouse.di

import androidx.room.Room
import com.example.cathouse.common.dispatchers.DefaultDispatcherProvider
import com.example.cathouse.common.dispatchers.DispatcherProvider
import com.example.cathouse.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    single<DispatcherProvider> {
        DefaultDispatcherProvider()
    }

}