package com.example.memora.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CardDao {
  @Query("SELECT * FROM cards WHERE deckId = :deckId")
  suspend fun getCardsByDeck(deckId: Int): List<CardEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCard(card: CardEntity)

  @Update
  suspend fun updateCard(card: CardEntity)

  @Delete
  suspend fun deleteCard(card: CardEntity)
}
