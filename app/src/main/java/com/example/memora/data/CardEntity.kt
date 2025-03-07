package com.example.memora.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
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
  val content: String,
  val lastReviewDate: Long,
  val nextReviewDate: Long,
  val reviewCount: Int = 0,
  val algorithm: AlgorithmType
)