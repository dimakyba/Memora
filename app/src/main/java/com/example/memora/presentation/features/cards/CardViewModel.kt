package com.example.memora.presentation.features.cards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.memora.core.algorithms.*
import com.example.memora.core.data.CardDao
import com.example.memora.core.data.CardEntity
import com.example.memora.core.data.DeckDao
import com.example.memora.core.notifications.NotificationScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardViewModel(
  application: Application,
  private val cardDao: CardDao,
  private val deckDao: DeckDao,
) : AndroidViewModel(application) {

  private val notificationScheduler = NotificationScheduler(application)

  private val _cards = MutableStateFlow<List<CardEntity>>(emptyList())
  val cards: StateFlow<List<CardEntity>> = _cards

  private val _currentCard = MutableStateFlow<CardEntity?>(null)
  val currentCard: StateFlow<CardEntity?> = _currentCard

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
      _deckName.value = deckDao.getDeckById(deckId)?.name
    }
  }

  fun loadCard(cardId: Long) {
    viewModelScope.launch {
      _currentCard.value = cardDao.getCardById(cardId)
    }
  }

  fun processLearningFeedback(cardId: Long, feedback: LearningFeedback) {
    viewModelScope.launch {
      val card = cardDao.getCardById(cardId)
      card?.let {
        val updatedCard = feedback.processReview(it)
        cardDao.updateCard(updatedCard)

        // Schedule notification for next review
        val now = System.currentTimeMillis()
        val delay = updatedCard.nextReviewDate - now
        if (delay > 0) {
          notificationScheduler.scheduleReviewNotification(cardId, delay)
        }

        getCardsForDeck(it.deckId)
      }
    }
  }

  fun cancelNotification(cardId: Long) {
    notificationScheduler.cancelReviewNotification(cardId)
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

  fun updateCard(cardId: Long, name: String, content: String, algorithm: AlgorithmType) {
    viewModelScope.launch {
      val existingCard = cardDao.getCardById(cardId)
      if (existingCard != null) {
        val updatedCard = existingCard.copy(
          name = name,
          content = content,
          algorithm = algorithm
        )
        cardDao.updateCard(updatedCard)
        getCardsForDeck(existingCard.deckId)
      }
    }
  }

  fun deleteCard(cardId: Long) {
    viewModelScope.launch {
      val card = cardDao.getCardById(cardId)
      if (card != null) {
        cardDao.deleteCard(card)
        getCardsForDeck(card.deckId)
      }
    }
  }
}
