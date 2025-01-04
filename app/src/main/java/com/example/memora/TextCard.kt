package com.example.memora

class TextCard(title: String, val textContent: String) : Card(title) {
  override fun displayCard() {
    println("Text Card - ID: $id")
    println("Title: $title")
    println("Content: $textContent")
  }
}


