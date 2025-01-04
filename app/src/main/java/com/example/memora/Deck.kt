package com.example.memora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.memora.ui.theme.MemoraTheme

class Deck(val name: String, val description: String) {
  companion object {
    private var idCounter = 0
  }

  val id: Int = ++idCounter
  private val cards = mutableListOf<Card>()

  fun addCard(card: Card) {
    cards.add(card)
  }

  fun displayDeck() {
    println("Deck ID: $id")
    println("Name: $name")
    println("Description: $description")
    println("Cards in Deck: ${cards.size}")
    println("Cards:")
    for (card in cards) {
      card.displayCard()
    }
  }
}

