package com.example.memora.domain.models

import com.example.memora.domain.algorithms.IRepetitionAlgorithm

data class Card(
  val id: Long = 0,
  val deckId: Long,
  val content: Content,
  val lastReviewDate: Long,
  val nextReviewDate: Long,
  val easeFactor: Float = 2.5f,
  val reviewCount: Int = 0,
  val algorithm: IRepetitionAlgorithm // Полиморфный алгоритм прямо в карточке!
)
