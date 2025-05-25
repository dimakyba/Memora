package com.example.memora.core.algorithms

import java.util.concurrent.TimeUnit

class FixedIntervalAlgorithm : RepetitionAlgorithm {
  override fun getNextReviewDate(reviewCount: Int): Long {
    return when (reviewCount) {
      0 -> System.currentTimeMillis()
      1 -> System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)    // Через 5 минут
      2 -> System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)      // Через 1 час
      3 -> System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)       // Через 1 день
      4 -> System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3)       // Через 3 дня
      5 -> System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)       // Через 7 дней
      6 -> System.currentTimeMillis() + TimeUnit.DAYS.toMillis(14)      // Через 14 дней
      7 -> System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30)      // Через 30 дней
      else -> System.currentTimeMillis() + TimeUnit.DAYS.toMillis(90)   // Через 90 дней (после 100%)
    }
  }

  override fun calculateProgress(reviewCount: Int): Int {
    return when (reviewCount) {
      0 -> 0    // До первого повторения
      1 -> 20   // Через 5 минут
      2 -> 35   // Через 1 час
      3 -> 50   // Через 1 день
      4 -> 65   // Через 3 дня
      5 -> 80   // Через 7 дней
      6 -> 90   // Через 14 дней
      7 -> 95  // Через 30 дней
      else -> 100 // После 7 повторений (и далее каждые 90 дней)
    }
  }
}
