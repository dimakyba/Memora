package com.example.memora.core.algorithms

import com.example.memora.core.data.CardEntity

class SimpleLearningFeedback : LearningFeedback {
  override fun processReview(card: CardEntity): CardEntity {
    return card.copy(
      reviewCount = card.reviewCount + 1,
      lastReviewDate = System.currentTimeMillis(),
      nextReviewDate = card.getRepetitionAlgorithm().getNextReviewDate(card.reviewCount + 1)
    )
  }
}