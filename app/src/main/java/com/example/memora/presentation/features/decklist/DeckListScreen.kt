package com.example.memora.presentation.features.decklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.memora.presentation.Screen
import com.example.memora.presentation.components.AddButton
import com.example.memora.presentation.components.CreateDeckDialog
import com.example.memora.presentation.components.DeckItem
import com.example.memora.presentation.components.TopAppBar
import com.example.memora.presentation.features.deck.DeckViewModel

class DeckListScreen(
  private val viewModel: DeckViewModel,
  private val navController: NavController
) {
  @Composable
  fun Show() {
    var showDialog by remember { mutableStateOf(false) }

    val decks by viewModel.decks.collectAsStateWithLifecycle(
      initialValue = emptyList(),
      lifecycleOwner = LocalLifecycleOwner.current
    )

    Scaffold(
      topBar = {
        TopAppBar(title = "Memora").Display()// Добавляем CustomTopAppBar в Scaffold
      },
      floatingActionButton = {
        AddButton(
          onClick = { showDialog = true },
          contentDescription = "Создать деку"
        ).Display()
      }
    ) { paddingValues ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues) // Учитываем отступы от Scaffold
      ) {
        if (decks.isEmpty()) {
          // Если дек нет, показываем текст по центру экрана
          Box(
            modifier = Modifier
              .fillMaxSize()
              .padding(16.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "Cтворіть вашу першу деку",
              style = MaterialTheme.typography.bodyLarge,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
        } else {
          // Если есть деки, показываем сетку
          LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
              .fillMaxSize()
              .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(decks) { deck ->
              val cardCount by produceState(initialValue = 0, key1 = deck.id) {
                value = viewModel.getCardCountForDeck(deck.id)
              }
              DeckItem(
                deck = deck,
                cardCount = cardCount,
                onClick = {
                  // Переход на DeckScreen с передачей deckId
                  navController.navigate("${Screen.Deck.route}/${deck.id}")
                }
              ).Display()
            }
          }
        }
      }
    }

    // Диалог создания деки
    if (showDialog) {
      CreateDeckDialog(
        onDismiss = { showDialog = false },
        onConfirm = { name ->
          viewModel.addDeck(name)
          showDialog = false
        }
      ).Display()
    }
  }
}