package com.example.memora.core.models

import com.example.memora.core.algorithms.AlgorithmType

data class Card(
  val id: Long = 0,
  val deckId: Long,
  val name: String,
  val content: Content,
  val lastReviewDate: Long,
  val nextReviewDate: Long,
  val reviewCount: Int = 0,
  val algorithm: AlgorithmType,
  val progress: Int
)
