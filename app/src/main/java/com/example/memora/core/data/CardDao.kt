package com.example.memora.core.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CardDao {
  @Query("SELECT * FROM cards WHERE deckId = :deckId")
  suspend fun getCardsByDeck(deckId: Long): List<CardEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCard(card: CardEntity)

  @Update
  suspend fun updateCard(card: CardEntity)

  @Delete
  suspend fun deleteCard(card: CardEntity)

  @Query("SELECT * FROM cards WHERE id = :cardId")
  suspend fun getCardById(cardId: Long): CardEntity?
}

