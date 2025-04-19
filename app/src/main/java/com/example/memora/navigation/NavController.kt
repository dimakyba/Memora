package com.example.memora.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.memora.presentation.Screen
import com.example.memora.presentation.features.cards.CardViewModel
import com.example.memora.presentation.features.deck.DeckScreen
import com.example.memora.presentation.features.deck.DeckViewModel
import com.example.memora.presentation.features.decklist.DeckListScreen


class NavController(
  private val navController: NavHostController,
  private val deckViewModel: DeckViewModel,
  private val cardViewModel: CardViewModel
) {
  @Composable
  fun AppNavigation() {
    NavHost(
      navController = navController,
      startDestination = Screen.DeckList.route
    ) {
      composable(Screen.DeckList.route) {
        DeckListScreen(viewModel = deckViewModel, navController = navController).Show()
      }
      composable(
        route = "${Screen.Deck.route}/{deckId}",
        arguments = listOf(navArgument("deckId") { type = NavType.LongType })
      ) { backStackEntry ->
        val deckId = backStackEntry.arguments?.getLong("deckId") ?: 0L
        DeckScreen(deckId = deckId, cardViewModel = cardViewModel).Show()
      }
    }
  }
}