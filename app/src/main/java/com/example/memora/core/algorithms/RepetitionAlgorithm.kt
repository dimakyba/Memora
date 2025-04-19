package com.example.memora.algorithms

interface RepetitionAlgorithm {
  fun getNextReviewDate(reviewCount: Int): Long
  fun calculateProgress(reviewCount: Int): Int
}
