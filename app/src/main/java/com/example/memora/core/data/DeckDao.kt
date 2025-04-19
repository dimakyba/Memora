package com.example.memora.core.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DeckDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertDeck(deck: DeckEntity): Long

  @Update
  suspend fun updateDeck(deck: DeckEntity)

  @Delete
  suspend fun deleteDeck(deck: DeckEntity)

  @Query("SELECT * FROM decks WHERE id = :deckId")
  suspend fun getDeckById(deckId: Long): DeckEntity?

  @Query("SELECT * FROM decks")
  suspend fun getAllDecks(): List<DeckEntity>

  @Query("SELECT COUNT(*) FROM cards WHERE deckId = :deckId")
  suspend fun getCardCountForDeck(deckId: Long): Int
}


