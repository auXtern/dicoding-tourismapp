package com.abdullah.tourismapp.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [com.abdullah.tourismapp.core.data.source.local.entity.TourismEntity::class], version = 1, exportSchema = false)
abstract class TourismDatabase : RoomDatabase() {

    abstract fun tourismDao(): com.abdullah.tourismapp.core.data.source.local.room.TourismDao

    companion object {
        @Volatile
        private var INSTANCE: com.abdullah.tourismapp.core.data.source.local.room.TourismDatabase? = null

        fun getInstance(context: Context): com.abdullah.tourismapp.core.data.source.local.room.TourismDatabase =
            com.abdullah.tourismapp.core.data.source.local.room.TourismDatabase.Companion.INSTANCE
                ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                com.abdullah.tourismapp.core.data.source.local.room.TourismDatabase::class.java,
                "Tourism.db"
            )
                .fallbackToDestructiveMigration()
                .build()
            com.abdullah.tourismapp.core.data.source.local.room.TourismDatabase.Companion.INSTANCE = instance
            instance
        }
    }
}