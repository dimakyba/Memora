package com.example.memora

class ImageCard(title: String, val imageUrl: String) : Card(title) {
  override fun displayCard() {
    println("Image Card - ID: $id")
    println("Title: $title")
    println("Image URL: $imageUrl")
  }
}