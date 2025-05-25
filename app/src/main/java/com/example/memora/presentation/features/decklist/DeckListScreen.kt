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
import com.example.memora.core.data.DeckEntity
import com.example.memora.presentation.Screen
import com.example.memora.presentation.components.AddButton
import com.example.memora.presentation.components.ConfirmationDialog
import com.example.memora.presentation.components.DeckItem
import com.example.memora.presentation.components.DeckNameDialog
import com.example.memora.presentation.components.TopAppBar
import com.example.memora.presentation.features.deck.DeckViewModel

class DeckListScreen(
  private val viewModel: DeckViewModel,
  private val navController: NavController
) {
  @Composable
  fun Show() {
    var showCreateDialog by remember { mutableStateOf(false) }
    var showRenameDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedDeck by remember { mutableStateOf<DeckEntity?>(null) }

    val decks by viewModel.decks.collectAsStateWithLifecycle(
      initialValue = emptyList(),
      lifecycleOwner = LocalLifecycleOwner.current
    )

    Scaffold(
      topBar = {
        TopAppBar(title = "Memora").Display()
      },
      floatingActionButton = {
        AddButton(
          onClick = { showCreateDialog = true },
          contentDescription = "Створити деку"
        ).Display()
      }
    ) { paddingValues ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues)
      ) {
        if (decks.isEmpty()) {
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
                  navController.navigate("${Screen.Deck.route}/${deck.id}")
                },
                onRenameClick = {
                  selectedDeck = deck
                  showRenameDialog = true
                },
                onDeleteClick = {
                  selectedDeck = deck
                  showDeleteDialog = true
                },
                onExportClick = {
                  // TODO: Implement export functionality
                }
              ).Display()
            }
          }
        }
      }
    }

    if (showCreateDialog) {
      DeckNameDialog(
        title = "Введіть назву деки:",
        confirmButtonText = "Створити",
        onDismiss = { showCreateDialog = false },
        onConfirm = { name ->
          viewModel.addDeck(name)
          showCreateDialog = false
        }
      ).Display()
    }

    if (showRenameDialog && selectedDeck != null) {
      DeckNameDialog(
        title = "Введіть нову назву деки:",
        confirmButtonText = "Перейменувати",
        initialValue = selectedDeck!!.name,
        onDismiss = {
          showRenameDialog = false
          selectedDeck = null
        },
        onConfirm = { newName ->
          viewModel.renameDeck(selectedDeck!!.id, newName)
          showRenameDialog = false
          selectedDeck = null
        }
      ).Display()
    }

    if (showDeleteDialog && selectedDeck != null) {
      ConfirmationDialog(
        title = "Видалити деку",
        message = "Ви впевнені, що хочете видалити деку \"${selectedDeck!!.name}\"? Усі картки в цій деці також будуть видалені.",
        confirmButtonText = "Видалити",
        onConfirm = {
          viewModel.deleteDeck(selectedDeck!!.id)
          showDeleteDialog = false
          selectedDeck = null
        },
        onDismiss = {
          showDeleteDialog = false
          selectedDeck = null
        }
      ).Display()
    }
  }
}
