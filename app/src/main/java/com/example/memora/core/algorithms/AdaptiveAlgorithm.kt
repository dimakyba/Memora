package com.example.memora.core.algorithms

import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min

class AdaptiveAlgorithm : RepetitionAlgorithm {
  companion object {
    private const val INITIAL_EASE = 2.5
    private const val MIN_EASE = 1.3
    private const val MAX_EASE = 3.0

    // Initial intervals in hours for first review based on quality
    private val INITIAL_INTERVALS = mapOf(
      1 to 4L,     // Hard: 4 hours
      2 to 8L,     // Hard-Medium: 8 hours
      3 to 12L,    // Medium: 12 hours
      4 to 24L,    // Medium-Easy: 1 day
      5 to 48L     // Easy: 2 days
    )
  }

  private var easinessFactor: Double = INITIAL_EASE
  private var lastInterval: Long = TimeUnit.HOURS.toMillis(4)
  private var lastQuality: Int = 0

  override fun getNextReviewDate(reviewCount: Int): Long {
    val now = System.currentTimeMillis()

    if (reviewCount == 0) {
      val initialHours = INITIAL_INTERVALS[lastQuality] ?: 4L
      return now + TimeUnit.HOURS.toMillis(initialHours)
    }

    // Calculate next interval based on SM-2 algorithm
    val nextInterval = when (reviewCount) {
      1 -> when (lastQuality) {
        1 -> TimeUnit.HOURS.toMillis(8)     // Hard: 8 hours
        2 -> TimeUnit.HOURS.toMillis(12)    // Hard-Medium: 12 hours
        3 -> TimeUnit.DAYS.toMillis(1)      // Medium: 1 day
        4 -> TimeUnit.DAYS.toMillis(3)      // Medium-Easy: 3 days
        else -> TimeUnit.DAYS.toMillis(5)   // Easy: 5 days
      }
      2 -> when (lastQuality) {
        1 -> TimeUnit.DAYS.toMillis(1)      // Hard: 1 day
        2 -> TimeUnit.DAYS.toMillis(3)      // Hard-Medium: 3 days
        3 -> TimeUnit.DAYS.toMillis(7)      // Medium: 1 week
        4 -> TimeUnit.DAYS.toMillis(14)     // Medium-Easy: 2 weeks
        else -> TimeUnit.DAYS.toMillis(21)  // Easy: 3 weeks
      }
      else -> {
        val baseInterval = if (lastQuality < 3) {
          // For hard ratings, decrease interval
          lastInterval / 2
        } else {
          // For good ratings, increase interval based on ease factor
          (lastInterval * easinessFactor).toLong()
        }

        when (lastQuality) {
          1 -> baseInterval / 2     // Hard: half the interval
          2 -> baseInterval * 2/3   // Hard-Medium: 2/3 of interval
          3 -> baseInterval         // Medium: keep interval
          4 -> baseInterval * 3/2   // Medium-Easy: 1.5x interval
          else -> baseInterval * 2  // Easy: double interval
        }
      }
    }

    lastInterval = nextInterval
    return now + nextInterval
  }

  override fun calculateProgress(reviewCount: Int): Int {
    if (reviewCount == 0) return 0

    val baseProgress = min(60, reviewCount * 10)

    val qualityBonus = when (lastQuality) {
      1 -> 0    // Hard
      2 -> 5    // Hard-Medium
      3 -> 10   // Medium
      4 -> 15   // Medium-Easy
      5 -> 20   // Easy
      else -> 0
    }

    val easeBonus = ((easinessFactor - MIN_EASE) / (MAX_EASE - MIN_EASE) * 20.0).toInt()

    return min(100, baseProgress + qualityBonus + easeBonus)
  }

  fun processReview(quality: Int) {
    require(quality in 1..5) { "Quality must be between 1 and 5" }
    lastQuality = quality

    // Update easiness factor using SM-2 formula
    easinessFactor = (easinessFactor + (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02)))
      .coerceIn(MIN_EASE, MAX_EASE)
  }
}
