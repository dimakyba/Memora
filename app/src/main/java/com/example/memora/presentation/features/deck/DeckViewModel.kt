package com.example.memora.presentation.features.deck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memora.core.data.DeckDao
import com.example.memora.core.data.DeckEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeckViewModel(private val deckDao: DeckDao) : ViewModel() {

  private val _decks = MutableStateFlow<List<DeckEntity>>(emptyList())
  val decks: StateFlow<List<DeckEntity>> = _decks

  init {
    getAllDecks()
  }

  private fun getAllDecks() {
    viewModelScope.launch {
      _decks.value = deckDao.getAllDecks()
    }
  }

  fun addDeck(name: String) {
    viewModelScope.launch {
      val newDeck = DeckEntity(name = name)
      deckDao.insertDeck(newDeck)
      getAllDecks()
    }
  }

  fun renameDeck(deckId: Long, newName: String) {
    viewModelScope.launch {
      val deck = deckDao.getDeckById(deckId)
      if (deck != null) {
        val updatedDeck = deck.copy(name = newName)
        deckDao.updateDeck(updatedDeck)
        getAllDecks()
      }
    }
  }

  fun deleteDeck(deckId: Long) {
    viewModelScope.launch {
      val deck = deckDao.getDeckById(deckId)
      if (deck != null) {
        deckDao.deleteDeck(deck)
        getAllDecks()
      }
    }
  }

  suspend fun getCardCountForDeck(deckId: Long): Int {
    return deckDao.getCardCountForDeck(deckId)
  }
}
