package com.example.memora.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.memora.core.Utils
import com.example.memora.core.data.CardEntity

class CardItem(
  private val card: CardEntity,
  private val onReviewClick: () -> Unit,
  private val onDeleteClick: () -> Unit,
  private val onEditClick: () -> Unit,
  private val onPreviewClick: () -> Unit
) : Component {
  @Composable
  override fun Display() {
    val scrollState = rememberScrollState()
    val isLearnEnabled = System.currentTimeMillis() >= card.nextReviewDate
    var expanded by remember { mutableStateOf(false) }

    Card(
      modifier = Modifier
        .fillMaxWidth()
        .clickable { onPreviewClick() },
      elevation = CardDefaults.cardElevation(4.dp)
    ) {
      Box(
        modifier = Modifier
          .padding(16.dp)
          .fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(end = 32.dp)
        ) {
          Text(
            text = card.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )

          Text(
            text = card.content,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 8.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(
              modifier = Modifier.weight(1f)
            ) {
              Text(
                text = "Наступне повторення:",
                style = MaterialTheme.typography.labelMedium
              )
              Text(
                text = Utils.formatTimeMillis(card.nextReviewDate),
                style = MaterialTheme.typography.bodySmall
              )
            }

            Button(
              onClick = onReviewClick,
              enabled = isLearnEnabled,
              modifier = Modifier.padding(start = 8.dp)
            ) {
              Text("Вчити")
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          ProgressBar(card.progress).Display()
        }

        IconButton(
          onClick = { expanded = true },
          modifier = Modifier.align(Alignment.TopEnd)
        ) {
          Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }

        DropdownMenu(
          expanded = expanded,
          onDismissRequest = { expanded = false }
        ) {
          DropdownMenuItem(
            text = { Text("Редагувати") },
            onClick = {
              onEditClick()
              expanded = false
            }
          )
          DropdownMenuItem(
            text = { Text("Видалити") },
            onClick = {
              onDeleteClick()
              expanded = false
            }
          )
        }
      }
    }
  }
}
