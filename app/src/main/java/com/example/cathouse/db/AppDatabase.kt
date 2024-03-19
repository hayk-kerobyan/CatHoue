package com.example.cathouse.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cathouse.features.cats.layers.data.model.local.CatDb
import com.example.cathouse.features.cats.layers.data.datasource.local.CatDao
import com.example.cathouse.db.converters.DateConverter
import com.example.cathouse.db.converters.StringListConverter
import com.example.cathouse.features.cats.layers.data.model.local.CatRemoteKey

@Database(
    entities = [CatDb::class, CatRemoteKey::class],
    version = 1
)
@TypeConverters(value = [DateConverter::class, StringListConverter::class])
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "app_db"
    }
    abstract fun catDao(): CatDao
}