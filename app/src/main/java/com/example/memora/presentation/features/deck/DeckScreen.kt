package com.example.memora.presentation.features.deck

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.memora.core.data.CardEntity
import com.example.memora.presentation.Screen
import com.example.memora.presentation.components.AddButton
import com.example.memora.presentation.components.CardItem
import com.example.memora.presentation.components.ConfirmationDialog
import com.example.memora.presentation.components.CreateCardDialog
import com.example.memora.presentation.components.EditCardDialog
import com.example.memora.presentation.components.PreviewCardDialog
import com.example.memora.presentation.components.TopAppBar
import com.example.memora.presentation.features.cards.CardViewModel

class DeckScreen(
  private val deckId: Long,
  private val cardViewModel: CardViewModel,
  private val navController: NavController
) {
  @Composable
  fun Show() {
    var showCreateDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showPreviewDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedCard by remember { mutableStateOf<CardEntity?>(null) }
    val cards by cardViewModel.cards.collectAsStateWithLifecycle()
    val deckName by cardViewModel.deckName.collectAsStateWithLifecycle()

    LaunchedEffect(deckId) {
      cardViewModel.getCardsForDeck(deckId)
      cardViewModel.getDeckName(deckId)
    }

    Scaffold(
      topBar = {
        TopAppBar(title = "Дека: ${deckName ?: "Deck"}").Display()
      },
      floatingActionButton = {
        AddButton(
          onClick = { showCreateDialog = true },
          contentDescription = "Додати картку"
        ).Display()
      },
      content = { paddingValues ->
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        ) {
          LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(cards) { card ->
              CardItem(
                card = card,
                onReviewClick = {
                  navController.navigate("${Screen.Learning.route}/${card.id}")
                },
                onDeleteClick = {
                  selectedCard = card
                  showDeleteDialog = true
                },
                onEditClick = {
                  selectedCard = card
                  showEditDialog = true
                },
                onPreviewClick = {
                  selectedCard = card
                  showPreviewDialog = true
                }
              ).Display()
            }
          }
        }
      }
    )

    if (showCreateDialog) {
      CreateCardDialog(
        onDismiss = { showCreateDialog = false },
        onConfirm = { name, content, algorithm ->
          cardViewModel.addCard(deckId, name, content, algorithm)
          showCreateDialog = false
        }
      ).Display()
    }

    if (showEditDialog && selectedCard != null) {
      EditCardDialog(
        card = selectedCard!!,
        onDismiss = {
          showEditDialog = false
          selectedCard = null
        },
        onConfirm = { name, content, algorithm ->
          cardViewModel.updateCard(selectedCard!!.id, name, content, algorithm)
          showEditDialog = false
          selectedCard = null
        }
      ).Display()
    }

    if (showPreviewDialog && selectedCard != null) {
      PreviewCardDialog(
        card = selectedCard!!,
        onDismiss = {
          showPreviewDialog = false
          selectedCard = null
        }
      ).Display()
    }

    if (showDeleteDialog && selectedCard != null) {
      ConfirmationDialog(
        title = "Видалити картку",
        message = "Ви впевнені, що хочете видалити картку \"${selectedCard!!.name}\"?",
        confirmButtonText = "Видалити",
        onConfirm = {
          cardViewModel.deleteCard(selectedCard!!.id)
          showDeleteDialog = false
          selectedCard = null
        },
        onDismiss = {
          showDeleteDialog = false
          selectedCard = null
        }
      ).Display()
    }
  }
}
