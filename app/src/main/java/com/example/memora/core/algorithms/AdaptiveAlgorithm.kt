package com.example.memora.core.algorithms

import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class AdaptiveAlgorithm : RepetitionAlgorithm {
  companion object {
    private const val MIN_INTERVAL_MINUTES = 5L
    private const val MAX_INTERVAL_DAYS = 365L
    private const val INITIAL_EASE = 2.5
    private const val MIN_EASE = 1.3
    private const val MAX_EASE = 3.0
    private const val EASE_BONUS = 0.15
    private const val EASE_PENALTY = 0.2
  }

  private var ease: Double = INITIAL_EASE
  private var interval: Long = TimeUnit.MINUTES.toMillis(MIN_INTERVAL_MINUTES)

  override fun getNextReviewDate(reviewCount: Int): Long {
    val now = System.currentTimeMillis()

    if (reviewCount == 0) {
      return now
    }

    val nextIntervalDays = when (reviewCount) {
      1 -> 1.0
      2 -> 3.0
      else -> {
        val currentIntervalDays = TimeUnit.MILLISECONDS.toDays(interval)
        currentIntervalDays * ease
      }
    }

    val boundedIntervalDays = min(max(nextIntervalDays, MIN_INTERVAL_MINUTES / (24.0 * 60)), MAX_INTERVAL_DAYS.toDouble())
    interval = TimeUnit.DAYS.toMillis(boundedIntervalDays.toLong())

    return now + interval
  }

  override fun calculateProgress(reviewCount: Int): Int {
    return when {
      reviewCount == 0 -> 0
      reviewCount == 1 -> 15
      reviewCount == 2 -> 30
      else -> {
        val baseProgress = min(85, 30 + (reviewCount - 2) * 15)
        val easeBonus = ((ease - MIN_EASE) / (MAX_EASE - MIN_EASE) * 15).roundToInt()
        min(100, baseProgress + easeBonus)
      }
    }
  }

  fun processReview(quality: Int) {
    require(quality in 0..5) { "Quality must be between 0 and 5" }

    // Update ease factor based on review quality
    if (quality < 3) {
      // Failed review - decrease ease
      ease = max(MIN_EASE, ease - EASE_PENALTY)
      // Reset interval for failed reviews
      interval = TimeUnit.DAYS.toMillis(1)
    } else {
      // Successful review - increase ease based on quality
      val easeModifier = (quality - 3) * EASE_BONUS
      ease = min(MAX_EASE, ease + easeModifier)
    }
  }
}
