package com.example.memora.core.algorithms

import com.example.memora.core.data.CardEntity
interface LearningFeedback {
    fun processReview(card: CardEntity): CardEntity
}

class SimpleLearningFeedback : LearningFeedback {
    override fun processReview(card: CardEntity): CardEntity {
        return card.copy(
            reviewCount = card.reviewCount + 1,
            lastReviewDate = System.currentTimeMillis(),
            nextReviewDate = card.getRepetitionAlgorithm().getNextReviewDate(card.reviewCount + 1)
        )
    }
}

class AdaptiveLearningFeedback(private val quality: Int) : LearningFeedback {
    override fun processReview(card: CardEntity):CardEntity {
        val algorithm = card.getRepetitionAlgorithm()
        if (algorithm is AdaptiveAlgorithm) {
            algorithm.processReview(quality)
        }
        return card.copy(
            reviewCount = card.reviewCount + 1,
            lastReviewDate = System.currentTimeMillis(),
            nextReviewDate = algorithm.getNextReviewDate(card.reviewCount + 1)
        )
    }
}
