package com.example.memora.core.algorithms

interface RepetitionAlgorithm {
  fun getNextReviewDate(reviewCount: Int): Long
  fun calculateProgress(reviewCount: Int): Int
}
