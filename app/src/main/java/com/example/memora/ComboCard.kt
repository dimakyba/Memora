package com.example.memora

class ComboCard(title: String, val textContent: String, val imageUrl: String) : Card(title) {
  override fun displayCard() {
    println("Combo Card - ID: $id")
    println("Title: $title")
    println("Content: $textContent")
    println("Image URL: $imageUrl")
  }
}