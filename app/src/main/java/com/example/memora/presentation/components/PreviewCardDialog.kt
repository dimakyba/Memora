package com.example.memora.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.memora.core.Utils
import com.example.memora.core.data.CardEntity
import com.example.memora.core.models.ContentItem


class PreviewCardDialog(
  private val card: CardEntity,
  private val onDismiss: () -> Unit
) :Component {

  @Composable
  override fun Display() {
    AlertDialog(
      onDismissRequest = onDismiss,
      title = { ContentItem.Text("Предпросмотр карточки") },
      text = {
        Column {
          Text(text = "Назва: ${card.name}")
          Text(text = "Контент: ${card.content}")
          Text(text = "Час останнього повторення: ${Utils.formatTimeMillis(card.lastReviewDate)}")
          Text(text = "Час наступного повторення: ${Utils.formatTimeMillis(card.nextReviewDate)}")
          ProgressBar(progress = card.progress)
        }
      },
      confirmButton = {
        Button(onClick = onDismiss) {
          Text(text ="Закрити")
        }
      }
    )
  }
}