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
import com.example.memora.core.data.CardEntity
import com.example.memora.presentation.components.AddButton
import com.example.memora.presentation.components.CardItem
import com.example.memora.presentation.components.CreateCardDialog
import com.example.memora.presentation.components.PreviewCardDialog
import com.example.memora.presentation.components.TopAppBar
import com.example.memora.presentation.features.cards.CardViewModel


class DeckScreen(
  private val deckId: Long,
  private val cardViewModel: CardViewModel
) {
  @Composable
  fun Show() {
    var showDialog by remember { mutableStateOf(false) }
    var showPreviewDialog by remember { mutableStateOf(false) }
    var selectedCard by remember { mutableStateOf<CardEntity?>(null) }
    val cards by cardViewModel.cards.collectAsStateWithLifecycle()
    val deckName by cardViewModel.deckName.collectAsStateWithLifecycle()

    LaunchedEffect(deckId) {
      cardViewModel.getCardsForDeck(deckId)
      cardViewModel.getDeckName(deckId)
    }

    Scaffold(
      topBar = {
        TopAppBar(title = "Дека: ${deckName ?: "Дека"}").Display()
      },
      floatingActionButton = {
        AddButton(
          onClick = { showDialog = true },
          contentDescription = "Добавить карточку"
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
                  cardViewModel.learnCard(card.id)
                },
                onDeleteClick = {
                  cardViewModel.deleteCard(card.id)
                },
                onEditClick = {
                  // Handle edit click
                },
                onPreviewClick = {
                  selectedCard = card
                  showPreviewDialog = true
                }
              ).Display()
            }
          }

          if (showDialog) {
            CreateCardDialog(
              onDismiss = { showDialog = false },
              onConfirm = { name, content, algorithm ->
                cardViewModel.addCard(deckId, name, content, algorithm)
                showDialog = false
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
        }
      }
    )
  }
}