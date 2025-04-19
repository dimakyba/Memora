package com.example.memora.presentation.features.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memora.algorithms.AlgorithmType
import com.example.memora.algorithms.FixedIntervalAlgorithm
import com.example.memora.algorithms.RepetitionAlgorithm
import com.example.memora.core.data.CardDao
import com.example.memora.core.data.CardEntity
import com.example.memora.core.data.DeckDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardViewModel(
  private val cardDao: CardDao,
  private val deckDao: DeckDao,
) : ViewModel() {

  private val _cards = MutableStateFlow<List<CardEntity>>(emptyList())
  val cards: StateFlow<List<CardEntity>> = _cards

  private val _deckName = MutableStateFlow<String?>(null)
  val deckName: StateFlow<String?> = _deckName

  private val repetitionAlgorithm: RepetitionAlgorithm = FixedIntervalAlgorithm()

  fun getCardsForDeck(deckId: Long) {
    viewModelScope.launch {
      _cards.value = cardDao.getCardsByDeck(deckId)
    }
  }

  fun getDeckName(deckId: Long) {
    viewModelScope.launch {
      val deck = deckDao.getDeckById(deckId)
      _deckName.value = deck?.name
    }
  }

  fun addCard(deckId: Long, name: String, content: String, algorithm: AlgorithmType) {
    viewModelScope.launch {
      val newCard = CardEntity(
        deckId = deckId,
        name = name,
        content = content,
        lastReviewDate = System.currentTimeMillis(),
        nextReviewDate = repetitionAlgorithm.getNextReviewDate(0),
        algorithm = algorithm,
        reviewCount = 0
      )
      cardDao.insertCard(newCard)
      getCardsForDeck(deckId)
    }
  }

  fun learnCard(cardId: Long) {
    viewModelScope.launch {
      val card = cardDao.getCardById(cardId)
      if (card != null) {
        val algorithm = card.getRepetitionAlgorithm()
        val updatedCard = card.copy(
          reviewCount = card.reviewCount + 1,
          lastReviewDate = System.currentTimeMillis(), // Обновление даты последнего повторения
          nextReviewDate = algorithm.getNextReviewDate(card.reviewCount + 1)
        )
        cardDao.updateCard(updatedCard) // Сохранение обновлённой карточки в базе данных
        getCardsForDeck(card.deckId) // Обновляем список карточек
      }
    }
  }

  fun deleteCard(cardId: Long) {
    viewModelScope.launch {
      val card = cardDao.getCardById(cardId)
      if (card != null) {
        cardDao.deleteCard(card)
        getCardsForDeck(card.deckId) // Обновляем список карточек
      }
    }
  }


}

