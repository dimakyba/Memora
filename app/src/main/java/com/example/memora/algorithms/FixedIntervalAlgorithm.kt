package com.example.memora.algorithms

import android.content.Context

class FixedIntervalAlgorithm(context: Context) : IRepetitionAlgorithm {

  private val intervals: List<Int> = loadIntervals(context)

  override fun getNextReviewDate(reviewCount: Int): Long {
    val interval = intervals.getOrElse(reviewCount) { intervals.lastOrNull() ?: 1 }
    return System.currentTimeMillis() + interval * 60 * 1000L
  }

  private fun loadIntervals(context: Context): List<Int> {
    val sharedPreferences = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
    val intervalsSet = sharedPreferences.getStringSet("static_intervals", emptySet())
    return intervalsSet?.mapNotNull { it.toIntOrNull() } ?: listOf(1, 5, 10) // Дефолтные интервалы

  }
}