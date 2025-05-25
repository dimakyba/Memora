package com.example.memora.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.memora.core.algorithms.AdaptiveAlgorithm
import com.example.memora.core.algorithms.AlgorithmType
import com.example.memora.core.algorithms.FixedIntervalAlgorithm
import com.example.memora.core.algorithms.RepetitionAlgorithm

@Entity(
  tableName = "cards",
  foreignKeys = [ForeignKey(
    entity = DeckEntity::class,
    parentColumns = ["id"],
    childColumns = ["deckId"],
    onDelete = ForeignKey.CASCADE
  )]
)
data class CardEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  @ColumnInfo(index = true) val deckId: Long,
  val name: String,
  val content: String, // add TypeConverter
  val lastReviewDate: Long,
  val nextReviewDate: Long,
  val reviewCount: Int = 0,
  val algorithm: AlgorithmType,
) {



   fun getRepetitionAlgorithm(): RepetitionAlgorithm {
    return when (algorithm) {
      AlgorithmType.FIXED_INTERVAL -> FixedIntervalAlgorithm()
      AlgorithmType.ADAPTIVE -> AdaptiveAlgorithm()
    }
  }

  val progress: Int
    get() = getRepetitionAlgorithm().calculateProgress(reviewCount)
}
