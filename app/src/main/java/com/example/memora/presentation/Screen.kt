package com.example.memora.presentation

sealed class Screen(val route: String) {
  data object DeckList : Screen("deck_list")
  data object Deck : Screen("deck")
  data object Learning : Screen("learning")
}
