package com.example.memora.core.algorithms

import com.example.memora.core.data.CardEntity

interface LearningFeedback {
  fun processReview(card: CardEntity): CardEntity
}


