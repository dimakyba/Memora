package com.example.memora.data

import androidx.room.*
import com.example.memora.algorithms.AlgorithmType

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
  val content: String, // JSON-строка (конвертируем через TypeConverter)
  val lastReviewDate: Long,
  val nextReviewDate: Long,
  val reviewCount: Int = 0,
  val algorithm: AlgorithmType
)