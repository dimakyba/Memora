package com.example.memora.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CardEntity::class, DeckEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun cardDao(): CardDao
  abstract fun deckDao(): DeckDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "memora_db"
        ).build()
        INSTANCE = instance
        instance
      }
    }
  }
}
