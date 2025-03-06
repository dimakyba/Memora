package com.example.memora.domain.models

import com.example.memora.algorithms.AlgorithmType

data class Card(
  val id: Long = 0,
  val deckId: Long,
  val content: Content,
  val lastReviewDate: Long,
  val nextReviewDate: Long,
  val reviewCount: Int = 0,
  val algorithm: AlgorithmType
)
