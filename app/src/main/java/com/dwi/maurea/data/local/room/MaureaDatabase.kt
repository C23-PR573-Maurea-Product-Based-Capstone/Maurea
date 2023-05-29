package com.dwi.maurea.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dwi.maurea.data.local.entitiy.ProfileEntity

@Database(entities = [ProfileEntity::class], version = 1, exportSchema = false)
abstract class MaureaDatabase: RoomDatabase() {
    abstract fun maureaDao(): MaureaDao

    companion object {
        @Volatile
        private var INSTANCE: MaureaDatabase? = null

        fun getInstance(context: Context): MaureaDatabase = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                MaureaDatabase::class.java,
                "Maurea.db"
            ).build().apply {
                INSTANCE = this
            }
        }
    }
}