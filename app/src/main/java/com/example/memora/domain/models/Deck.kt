package com.example.memora.domain.models

data class Deck(
  val id: Long = 0,
  val name: String,
  val description: String = "",
  val createdAt: Long = System.currentTimeMillis(),
  val updatedAt: Long = System.currentTimeMillis(),
  var progress: Int = 0
)

